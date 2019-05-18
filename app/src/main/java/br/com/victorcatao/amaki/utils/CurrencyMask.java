package br.com.victorcatao.amaki.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyMask {

    public static String unmask(String s) {
        String newStr = s.replaceAll("[R$�,.()]", "");
        return newStr;
    }

    public static float parseValue(String s) throws NumberFormatException {
        try {
            return Float.parseFloat(unmask(s).replaceAll("\\s+","")) / 100;
        } catch (Exception ex) {
            return 0f;
        }
    }

    public static TextWatcher insert(final Locale locale, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                if (!s.toString().equals(old)) {

                    isUpdating = true;

                    String cleanString = unmask(s.toString());

                    try {
                        double parsed = Double.parseDouble(cleanString.replaceAll("\\s+",""));
                        String formated = NumberFormat.getCurrencyInstance(locale)
                                .format((parsed / 100));

                        setFormatedValue(formated);
                    } catch (NumberFormatException e) {

                        String formated =  NumberFormat.getCurrencyInstance(locale)
                                .format(0);
                        setFormatedValue(formated);
                        e.printStackTrace();
                    }
                }

                // is erasing text
                if (old.length() > s.length() && s.length() > 0) {
                    old = s.toString();
                    return;
                }
            }

            private void setFormatedValue(String formated) {
                formated = formated.replace("R$", "R$ ");
                old = formated;
                ediTxt.setText(formated);
                ediTxt.setSelection(formated.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }
}