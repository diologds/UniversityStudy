package lv.rtu.testapp;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import lv.rtu.audio.VoiceRecorder;
import lv.rtu.connection.Sender;
import lv.rtu.enums.FileConstants;


public class MainActivity extends Activity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void recordVoice(String location) {

        final VoiceRecorder recorder = new VoiceRecorder(false,
                MediaRecorder.AudioSource.MIC, 8000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        recorder.setOutputFile(location);

        Thread thread = new Thread(recorder);
        thread.start();
    }

    public void addListenerOnButton() {

        button = (Button) findViewById(R.id.startRecord);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(isNetworkAvailable()) {
                    recordVoice(FileConstants.FILE_LOCATION.getValue());
                }
            }
        });
    }
}
