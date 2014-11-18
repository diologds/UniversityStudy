package lv.rtu.enums;

import android.os.Environment;

public enum FileConstants {

    FILE_NAME("Voice.wav"), FILE_LOCATION(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/Voice.wav");

    private String value;

    private FileConstants(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
