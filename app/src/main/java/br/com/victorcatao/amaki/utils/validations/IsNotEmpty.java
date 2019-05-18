package br.com.victorcatao.amaki.utils.validations;


public class IsNotEmpty {

    public static boolean isValid(String text) {
        return !text.isEmpty();
    }
}
