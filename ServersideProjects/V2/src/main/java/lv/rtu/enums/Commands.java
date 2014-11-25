package lv.rtu.enums;

public enum Commands {
    LOGIN("login"), EXIT("exit"), STREAM("stream"), GENERAL("general"), IMAGE("image"), AUDIO("audio"), MOBILE("mobile");
    private String value;

    Commands(String value) {
        this.value = value;
    }

    public static Commands fromValue(String value) {
        if (value.equals(LOGIN.value))
            return LOGIN;
        else if (value.equals(EXIT.value))
            return EXIT;
        else if (value.equals(STREAM.value))
            return STREAM;
        else if (value.equals(IMAGE.value))
            return IMAGE;
        else if (value.equals(AUDIO.value))
            return AUDIO;
        else if (value.equals(GENERAL.value))
            return GENERAL;
        else if (value.equals(MOBILE.value))
            return MOBILE;
        else return null;
    }
}
