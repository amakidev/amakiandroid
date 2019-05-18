package br.com.victorcatao.amaki.utils.helpers;

import java.util.Locale;

import br.com.victorcatao.amaki.utils.base.BaseDateFormatHelper;

public class DateFormatHelper extends BaseDateFormatHelper {
    public DateFormatHelper(String dateFormat) {
        super(dateFormat);
    }

    @Override
    public Locale getLocale() {
        return Locale.getDefault();
    }
}
