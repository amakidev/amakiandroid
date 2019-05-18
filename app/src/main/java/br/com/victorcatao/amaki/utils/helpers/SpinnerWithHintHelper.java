package br.com.victorcatao.amaki.utils.helpers;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SpinnerWithHintHelper {
    public static final int SPINNER_HINT_POSITION = 0;

    private String[] mStrings;
    private Spinner mSpinner;
    private Context mContext;

    public SpinnerWithHintHelper(Context context, String[] strings, Spinner spinner) {
        mStrings = strings;
        mSpinner = spinner;
        mContext = context;
        setGenderSpinner();
    }

    public String[] getStrings() {
        return mStrings;
    }

    private void setGenderSpinner() {
        //Set adapter to first item be used as hint
        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(mContext, android.R.layout.simple_spinner_dropdown_item, mStrings) {
            @Override
            public boolean isEnabled(int position) {
                if (position == SPINNER_HINT_POSITION) {
                    // Disable the first item from Spinner
                    // First item will be used as hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == SPINNER_HINT_POSITION) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(i != SPINNER_HINT_POSITION) mSpinner.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_white_rounded_stroke_gray));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpinner.setAdapter(spinnerAdapter);
    }

    public boolean isSpinnerSelectionValid() {
        return mSpinner.getSelectedItemPosition() != SPINNER_HINT_POSITION;
    }

    public Spinner getSpinner() {
        return mSpinner;
    }

    public String getSelectedString() {
        return mStrings[mSpinner.getSelectedItemPosition()];
    }
}
