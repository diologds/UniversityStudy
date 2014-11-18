package lv.rtu.audio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.util.Log;

import lv.rtu.connection.Sender;
import lv.rtu.domain.ObjectFile;
import lv.rtu.enums.Commands;
import lv.rtu.enums.FileConstants;

@SuppressWarnings("deprecation")
public class VoiceRecorder implements Runnable {
    private final static int[] sampleRates = {44100, 22050, 11025, 8000};

    public static VoiceRecorder getInstanse(Boolean recordingCompressed) {
        VoiceRecorder result = null;

        if (recordingCompressed) {
            result = new VoiceRecorder(false, AudioSource.MIC, sampleRates[3],
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_8BIT);
        } else {
            int i = 0;
            do {
                result = new VoiceRecorder(true, AudioSource.MIC,
                        sampleRates[i], AudioFormat.CHANNEL_CONFIGURATION_MONO,
                        AudioFormat.ENCODING_PCM_8BIT);

            } while ((++i < sampleRates.length)
                    & !(result.getState() == State.INITIALIZING));
        }
        return result;
    }

    public enum State {
        INITIALIZING, READY, RECORDING, ERROR, STOPPED
    }

    ;

    public static final boolean RECORDING_UNCOMPRESSED = true;
    public static final boolean RECORDING_COMPRESSED = false;

    private static final int TIMER_INTERVAL = 120;

    private boolean rUncompressed;

    private AudioRecord audioRecorder = null;

    private MediaRecorder mediaRecorder = null;

    private int cAmplitude = 0;

    private String filePath = null;

    private State state;

    private RandomAccessFile randomAccessWriter;

    private short nChannels;
    private int sRate;
    private short bSamples;
    private int bufferSize;
    private int aSource;
    private int aFormat;

    private int framePeriod;

    private byte[] buffer;

    private int payloadSize;

    private boolean stopedRecord;

    public State getState() {
        return state;
    }

    private AudioRecord.OnRecordPositionUpdateListener updateListener = new AudioRecord.OnRecordPositionUpdateListener() {
        public void onPeriodicNotification(AudioRecord recorder) {
            audioRecorder.read(buffer, 0, buffer.length);
            try {
                randomAccessWriter.write(buffer);
                payloadSize += buffer.length;
                if (bSamples == 16) {
                    for (int i = 0; i < buffer.length / 2; i++) {
                        short curSample = getShort(buffer[i * 2],
                                buffer[i * 2 + 1]);
                        if (curSample > cAmplitude) {
                            cAmplitude = curSample;
                        }
                    }
                } else {
                    for (int i = 0; i < buffer.length; i++) {
                        if (buffer[i] > cAmplitude) {
                            cAmplitude = buffer[i];
                        }
                    }
                }
            } catch (IOException e) {
                Log.e(VoiceRecorder.class.getName(),
                        "Error occured in updateListener, recording is aborted");
            }
        }

        public void onMarkerReached(AudioRecord recorder) {
        }
    };

    @SuppressWarnings("deprecation")
    public VoiceRecorder(boolean uncompressed, int audioSource, int sampleRate,
                         int channelConfig, int audioFormat) {
        try {
            rUncompressed = uncompressed;
            if (rUncompressed) {
                if (audioFormat == AudioFormat.ENCODING_PCM_16BIT) {
                    bSamples = 16;
                } else {
                    bSamples = 8;
                }

                if (channelConfig == AudioFormat.CHANNEL_CONFIGURATION_MONO) {
                    nChannels = 1;
                } else {
                    nChannels = 2;
                }

                aSource = audioSource;
                sRate = sampleRate;
                aFormat = audioFormat;

                framePeriod = sampleRate * TIMER_INTERVAL / 1000;
                bufferSize = framePeriod * 2 * bSamples * nChannels / 8;
                if (bufferSize < AudioRecord.getMinBufferSize(sampleRate,
                        channelConfig, audioFormat)) {
                    bufferSize = AudioRecord.getMinBufferSize(sampleRate,
                            channelConfig, audioFormat);
                    framePeriod = bufferSize / (2 * bSamples * nChannels / 8);
                    Log.w(VoiceRecorder.class.getName(),
                            "Increasing buffer size to "
                                    + Integer.toString(bufferSize));
                }

                audioRecorder = new AudioRecord(audioSource, sampleRate,
                        channelConfig, audioFormat, bufferSize);

                if (audioRecorder.getState() != AudioRecord.STATE_INITIALIZED)
                    throw new Exception("AudioRecord initialization failed");
                audioRecorder.setRecordPositionUpdateListener(updateListener);
                audioRecorder.setPositionNotificationPeriod(framePeriod);
            } else {
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(AudioSource.MIC);
                mediaRecorder
                        .setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder
                        .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            }
            cAmplitude = 0;
            filePath = null;
            state = State.INITIALIZING;
        } catch (Exception e) {
            if (e.getMessage() != null) {
                Log.e(VoiceRecorder.class.getName(), e.getMessage());
            } else {
                Log.e(VoiceRecorder.class.getName(),
                        "Unknown error occured while initializing recording");
            }
            state = State.ERROR;
        }
    }

    public void setOutputFile(String argPath) {
        try {
            if (state == State.INITIALIZING) {
                filePath = argPath;
                if (!rUncompressed) {
                    mediaRecorder.setOutputFile(filePath);
                }
            }
        } catch (Exception e) {
            if (e.getMessage() != null) {
                Log.e(VoiceRecorder.class.getName(), e.getMessage());
            } else {
                Log.e(VoiceRecorder.class.getName(),
                        "Unknown error occured while setting output path");
            }
            state = State.ERROR;
        }
    }

    public int getMaxAmplitude() {
        if (state == State.RECORDING) {
            if (rUncompressed) {
                int result = cAmplitude;
                cAmplitude = 0;
                return result;
            } else {
                try {
                    return mediaRecorder.getMaxAmplitude();
                } catch (IllegalStateException e) {
                    return 0;
                }
            }
        } else {
            return 0;
        }
    }

    public void prepare() {
        try {
            if (state == State.INITIALIZING) {
                if (rUncompressed) {
                    if ((audioRecorder.getState() == AudioRecord.STATE_INITIALIZED)
                            & (filePath != null)) {
                        randomAccessWriter = new RandomAccessFile(filePath,
                                "rw");
                        randomAccessWriter.setLength(0);
                        randomAccessWriter.writeBytes("RIFF");
                        randomAccessWriter.writeInt(0);
                        randomAccessWriter.writeBytes("WAVE");
                        randomAccessWriter.writeBytes("fmt ");
                        randomAccessWriter.writeInt(Integer.reverseBytes(16));
                        randomAccessWriter.writeShort(Short
                                .reverseBytes((short) 1));
                        randomAccessWriter.writeShort(Short
                                .reverseBytes(nChannels));
                        randomAccessWriter
                                .writeInt(Integer.reverseBytes(sRate));
                        randomAccessWriter.writeInt(Integer.reverseBytes(sRate
                                * bSamples * nChannels / 8));
                        randomAccessWriter
                                .writeShort(Short
                                        .reverseBytes((short) (nChannels
                                                * bSamples / 8)));
                        randomAccessWriter.writeShort(Short
                                .reverseBytes(bSamples));
                        randomAccessWriter.writeBytes("data");
                        randomAccessWriter.writeInt(0);
                        buffer = new byte[framePeriod * bSamples / 8
                                * nChannels];
                        state = State.READY;
                    } else {
                        Log.e(VoiceRecorder.class.getName(),
                                "prepare() method called on uninitialized recorder");
                        state = State.ERROR;
                    }
                } else {
                    mediaRecorder.prepare();
                    state = State.READY;
                }
            } else {
                Log.e(VoiceRecorder.class.getName(),
                        "prepare() method called on illegal state");
                release();
                state = State.ERROR;
            }
        } catch (Exception e) {
            if (e.getMessage() != null) {
                Log.e(VoiceRecorder.class.getName(), e.getMessage());
            } else {
                Log.e(VoiceRecorder.class.getName(),
                        "Unknown error occured in prepare()");
            }
            state = State.ERROR;
        }
    }

    public void release() {
        if (state == State.RECORDING) {
            stop();
        } else {
            if ((state == State.READY) & (rUncompressed)) {
                try {
                    randomAccessWriter.close();
                } catch (IOException e) {
                    Log.e(VoiceRecorder.class.getName(),
                            "I/O exception occured while closing output file");
                }
                (new File(filePath)).delete();
            }
        }

        if (rUncompressed) {
            if (audioRecorder != null) {
                audioRecorder.release();
            }
        } else {
            if (mediaRecorder != null) {
                mediaRecorder.release();
            }
        }
    }

    public void reset() {
        try {
            if (state != State.ERROR) {
                release();
                filePath = null;
                cAmplitude = 0;
                if (rUncompressed) {
                    audioRecorder = new AudioRecord(aSource, sRate,
                            nChannels + 1, aFormat, bufferSize);
                } else {
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(AudioSource.MIC);
                    mediaRecorder
                            .setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder
                            .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                }
                state = State.INITIALIZING;
            }
        } catch (Exception e) {
            Log.e(VoiceRecorder.class.getName(), e.getMessage());
            state = State.ERROR;
        }
    }

    public void start() {
        if (state == State.READY) {
            if (rUncompressed) {
                payloadSize = 0;
                audioRecorder.startRecording();
                audioRecorder.read(buffer, 0, buffer.length);
            } else {
                mediaRecorder.start();
            }
            state = State.RECORDING;
        } else {
            Log.e(VoiceRecorder.class.getName(),
                    "start() called on illegal state");
            state = State.ERROR;
        }
    }

    public void stop() {
        if (state == State.RECORDING) {
            if (rUncompressed) {
                audioRecorder.stop();

                try {
                    randomAccessWriter.seek(4);
                    randomAccessWriter.writeInt(Integer
                            .reverseBytes(36 + payloadSize));

                    randomAccessWriter.seek(40);
                    randomAccessWriter.writeInt(Integer
                            .reverseBytes(payloadSize));

                    randomAccessWriter.close();
                } catch (IOException e) {
                    Log.e(VoiceRecorder.class.getName(),
                            "I/O exception occured while closing output file");
                    state = State.ERROR;
                }
            } else {
                mediaRecorder.stop();
            }
            state = State.STOPPED;
        } else {
            Log.e(VoiceRecorder.class.getName(),
                    "stop() called on illegal state");
            state = State.ERROR;
        }
    }

    private short getShort(byte argB1, byte argB2) {
        return (short) (argB1 | (argB2 << 8));
    }

    @Override
    public void run() {
        Log.e(VoiceRecorder.class.getName(), "Record started");
        prepare();
        long start = System.currentTimeMillis();
        start();
        long end = start + 10 * 1000;
        while (System.currentTimeMillis() < end) {
        }
        ;
        stop();
        release();
        Log.e(VoiceRecorder.class.getName(), "Record stopped");

        Sender sender = new Sender();
        sender.setToSend(new ObjectFile(Commands.MOBILE.getValue(), "Recognize Audio", null, null,
                Converter.getSoundBytes(FileConstants.FILE_LOCATION.getValue()), null, null));

        new Thread(sender).start();
    }
}