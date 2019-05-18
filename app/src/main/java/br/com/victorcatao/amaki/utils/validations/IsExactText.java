package br.com.victorcatao.amaki.utils.validations;

public class IsExactText {

    public static boolean isValid(String text, String otherText) {
        return text.equals(otherText);
    }

}
