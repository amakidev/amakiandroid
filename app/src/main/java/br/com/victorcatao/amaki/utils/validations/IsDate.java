package br.com.victorcatao.amaki.utils.validations;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class IsDate {
    public static boolean isValid(String text, String dateFormat) {
        SimpleDateFormat sdf =  new SimpleDateFormat(dateFormat);
        try {
            sdf.parse(text);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
