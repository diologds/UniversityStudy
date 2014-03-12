package lv.rtu.recognition.audio;


import com.google.common.primitives.Ints;
import lv.rtu.db.UserTableImplementationDAO;
import lv.rtu.domain.User;
import marf.MARF;
import marf.Storage.ModuleParams;
import marf.Storage.TrainingSet;
import marf.util.Debug;
import marf.util.MARFException;
import marf.util.OptionProcessor;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class AudioRecognitionEngine {

    private UserTableImplementationDAO table = new UserTableImplementationDAO();

    protected OptionProcessor soGetOpt = new OptionProcessor();

    public synchronized void configure() {
        try {
            validateVersions();
            setDefaultConfig();
        } catch (MARFException e) {
            System.out.println("Configuration Installation Error");
        }
    }

    public synchronized void trainFolder(String fileName) {
        try {

            File[] aoFiles = new File(fileName).listFiles();
            Debug.debug("Files array: " + aoFiles);

            if (Debug.isDebugOn()) {
                System.getProperties().list(System.err);
            }

            String strFileName = "";

            for (int i = 0; i < aoFiles.length; i++) {
                strFileName = aoFiles[i].getPath();

                if (aoFiles[i].isFile() && strFileName.toLowerCase().endsWith(".wav")) {
                    try {
                        String[] files = strFileName.toString().split(Pattern.quote(File.separator));
                        String name = files[files.length - 1];

                        if(table.findUserByAudioFileName(name)!= null){
                            TimeUnit.NANOSECONDS.sleep(100);
                            train(strFileName);
                            System.out.println("Done training with " + strFileName);
                        }
                        else {

                        }

                    } catch (MARFException e) {
                        System.out.println("Error in training mode");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (NullPointerException e) {
            System.err.println("Folder \"" + fileName + "\" not found.");
            e.printStackTrace(System.err);
            System.exit(-1);
        }

        System.out.println("Done training on folder \"" + fileName + "\".");

    }

    public synchronized String[] ident(String pstrFilename)
            throws MARFException {
        /*
         * If no expected speaker present on the command line,
		 * attempt to fetch it from the database by filename.
		 */
        String expected = null;
        String pstrConfig = MARF.getConfig();
        int piExpectedID = -1;

        if (piExpectedID > 0) {
            String[] files = pstrFilename.split("/");
            String name = files[files.length - 1];
            System.out.println("Converted name : " + name);
            piExpectedID = Ints.checkedCast(((User) table.findUserByAudioFileName(name)).getId());
            expected = ((User) table.select((long) piExpectedID)).getUserName();
        }

        MARF.setSampleFile(pstrFilename);
        MARF.recognize();

        String [] results = new String[2];
        // First guess
        int iIdentifiedID = MARF.queryResultID();
        results[0] = ((User)table.select((long) iIdentifiedID)).getUserName();
        // Second best
        int iSecondClosestID = MARF.getResultSet().getSecondClosestID();
        results[1] = ((User) table.select((long) iSecondClosestID)).getUserName();

        System.out.println("----------------------------8<------------------------------");
        System.out.println("                 File: " + pstrFilename);
        System.out.println("               Config: " + pstrConfig);
        System.out.println("         Speaker's ID: " + iIdentifiedID);
        System.out.println("   Speaker identified: " + results[0]);

		/*
         * Only collect stats if we have expected speaker
		 */

        if (piExpectedID > 0) {
            System.out.println("Expected Speaker's ID: " + piExpectedID);
            System.out.println("     Expected Speaker: " + expected);
        }

        System.out.println("       Second Best ID: " + iSecondClosestID);
        System.out.println("     Second Best Name: " + results[1]);
        System.out.println("            Date/time: " + new Date());
        System.out.println("----------------------------8<------------------------------");

        return results;
    }

    public synchronized void folderIdent(String filePath) {
        // Store config and error/successes for that config
        String strConfig = MARF.getConfig();

        // Dir contents
        //File[] aoWaveFiles = new File(soGetOpt.getOption(OPT_DIR_OR_FILE)).listFiles();
        File[] aoWaveFiles = new File(filePath).listFiles();
        for (int i = 0; i < aoWaveFiles.length; i++) {
            String strFileName = aoWaveFiles[i].getPath();

            if (aoWaveFiles[i].isFile() && strFileName.toLowerCase().endsWith(".wav")) {
                try {
                    ident(strFileName);
                } catch (MARFException e) {
                    System.out.println("Error in Identification");
                }
            }
        }
    }

    /**
     * Updates training set with a new sample from a given file.
     *
     * @param pstrFilename name of the wave file with voice sample train the system on
     * @throws MARFException in case of any error while processing is in MARF
     * @since 0.3.0.5
     */
    public synchronized void train(String pstrFilename) throws MARFException {
        MARF.setSampleFile(pstrFilename);
        String[] files = pstrFilename.toString().split(Pattern.quote(File.separator));
        pstrFilename = files[files.length - 1];
        int iID = Ints.checkedCast(((User) table.findUserByAudioFileName(pstrFilename)).getId());

        if (iID == -1) {
            System.out.println("No speaker found for \"" + pstrFilename + "\" for training.");
        } else {
            MARF.setCurrentSubject(iID);
            MARF.train();
        }
    }

    /**
     * Makes sure the applications isn't run against older MARF version.
     * Exits with 1 if the MARF version is too old.
     */
    public synchronized void validateVersions() {
        if (MARF.getDoubleVersion() < (0 * 100 + 3 * 10 + 0 + .5)) {
            System.err.println
                    (
                            "Your MARF version (" + MARF.getVersion() +
                                    ") is too old. This application requires 0.3.0.5 or above."
                    );

            System.exit(1);
        }
    }

    /**
     * Composes the current configuration of in a string form.
     *
     * @param pstrArgv set of configuration options passed through the command line;
     *                 can be null or empty. If latter is the case, MARF itself is queried for its
     *                 numerical set up inside.
     * @return the current configuration setup
     */
    public synchronized String getConfigString(String[] pstrArgv) {
        // Store config and error/successes for that config
        String strConfig = "";

        if (pstrArgv != null && pstrArgv.length > 2) {
            // Get config from the command line
            for (int i = 2; i < pstrArgv.length; i++) {
                strConfig += pstrArgv[i] + " ";
            }
        } else {
            // Query MARF for it's current config
            strConfig = MARF.getConfig();
        }

        return strConfig;
    }

    /**
     * Sets default MARF configuration parameters as normalization
     * for preprocessing, FFT for feature extraction, Eucludean
     * distance for training and classification with no spectrogram
     * dumps and no debug information, assuming WAVE file format.
     *
     * @throws MARFException
     * @since 0.3.0.5
     */
    public synchronized void setDefaultConfig()
            throws MARFException {
        /*
		 * Default MARF setup
		 */
        MARF.setPreprocessingMethod(MARF.ENDPOINT);
        MARF.setFeatureExtractionMethod(MARF.LPC);
        MARF.setClassificationMethod(MARF.CHEBYSHEV_DISTANCE);
        MARF.setDumpSpectrogram(false);
        MARF.setSampleFormat(MARF.WAV);

        Debug.enableDebug(false);
    }

    /**
     * Customizes MARF's configuration based on the options.
     *
     * @throws MARFException if some options are out of range
     * @since 0.3.0.5
     */
    public synchronized void setCustomConfig()
            throws MARFException {
        ModuleParams oParams = new ModuleParams();

        for
                (
                int iPreprocessingMethod = MARF.MIN_PREPROCESSING_METHOD;
                iPreprocessingMethod <= MARF.MAX_PREPROCESSING_METHOD;
                iPreprocessingMethod++
                ) {
            if (soGetOpt.isActiveOption(iPreprocessingMethod)) {
                MARF.setPreprocessingMethod(iPreprocessingMethod);

                switch (iPreprocessingMethod) {
                    case MARF.DUMMY:
                    case MARF.HIGH_FREQUENCY_BOOST_FFT_FILTER:
                    case MARF.HIGH_PASS_FFT_FILTER:
                    case MARF.LOW_PASS_FFT_FILTER:
                    case MARF.BANDPASS_FFT_FILTER:
                    case MARF.HIGH_PASS_BOOST_FILTER:
                    case MARF.RAW:
                    case MARF.ENDPOINT:
                        // For now do nothing; customize when these methods
                        // become parametrizable.
                        break;

                    default:
                        assert false;
                } // switch

                break;
            }
        }

        for (int iFeatureExtractionMethod = MARF.MIN_FEATUREEXTRACTION_METHOD;
             iFeatureExtractionMethod <= MARF.MAX_FEATUREEXTRACTION_METHOD;
             iFeatureExtractionMethod++) {
            if (soGetOpt.isActiveOption(iFeatureExtractionMethod)) {
                MARF.setFeatureExtractionMethod(iFeatureExtractionMethod);

                switch (iFeatureExtractionMethod) {
                    case MARF.FFT:
                    case MARF.LPC:
                    case MARF.RANDOM_FEATURE_EXTRACTION:
                    case MARF.MIN_MAX_AMPLITUDES:
                        // For now do nothing; customize when these methods
                        // become parametrizable.
                        break;

                    case MARF.FEATURE_EXTRACTION_AGGREGATOR: {
                        // For now aggregate FFT followed by LPC until
                        // it becomes customizable
                        oParams.addFeatureExtractionParam(new Integer(MARF.FFT));
                        oParams.addFeatureExtractionParam(null);
                        oParams.addFeatureExtractionParam(new Integer(MARF.LPC));
                        oParams.addFeatureExtractionParam(null);
                        break;
                    }

                    default:
                        assert false;
                } // switch

                break;
            }
        }

        for (int iClassificationMethod = MARF.MIN_CLASSIFICATION_METHOD;
             iClassificationMethod <= MARF.MAX_CLASSIFICATION_METHOD;
             iClassificationMethod++) {
            if (soGetOpt.isActiveOption(iClassificationMethod)) {
                MARF.setClassificationMethod(iClassificationMethod);

                switch (iClassificationMethod) {
                    case MARF.NEURAL_NETWORK: {
                        // Dump/Restore Format of the TrainingSet
                        oParams.addClassificationParam(new Integer(TrainingSet.DUMP_GZIP_BINARY));

                        // Training Constant
                        oParams.addClassificationParam(new Double(0.5));

                        // Epoch number
                        oParams.addClassificationParam(new Integer(20));

                        // Min. error
                        oParams.addClassificationParam(new Double(0.1));

                        break;
                    }

                    case MARF.MINKOWSKI_DISTANCE: {
                        // Dump/Restore Format
                        oParams.addClassificationParam(new Integer(TrainingSet.DUMP_GZIP_BINARY));

                        // Minkowski Factor
                        oParams.addClassificationParam(new Double(6.0));

                        break;
                    }

                    case MARF.EUCLIDEAN_DISTANCE:
                    case MARF.CHEBYSHEV_DISTANCE:
                    case MARF.MAHALANOBIS_DISTANCE:
                    case MARF.RANDOM_CLASSIFICATION:
                    case MARF.DIFF_DISTANCE:
                        // For now do nothing; customize when these methods
                        // become parametrizable.
                        break;

                    default:
                        assert false;
                } // switch

                // Method is found, break out of the look up loop
                break;
            }
        }

        // Assign meaningful params only
        if (oParams.size() > 0) {
            MARF.setModuleParams(oParams);
        }
    }

}

