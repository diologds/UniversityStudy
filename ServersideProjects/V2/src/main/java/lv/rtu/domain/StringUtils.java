package lv.rtu.domain;

public class StringUtils {

    public static String getWord(String text, Integer number){
        return text.split("\\s+")[--number];
    }

}
