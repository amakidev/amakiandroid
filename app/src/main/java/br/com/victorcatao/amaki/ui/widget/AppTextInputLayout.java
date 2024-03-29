package br.com.victorcatao.amaki.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import br.com.victorcatao.amaki.R;
import br.com.victorcatao.amaki.utils.TextMask;
import br.com.victorcatao.amaki.utils.validations.IsCnpj;
import br.com.victorcatao.amaki.utils.validations.IsCpf;
import br.com.victorcatao.amaki.utils.validations.IsEmail;

public class AppTextInputLayout extends TextInputLayout {
    private final int TYPE_CUSTOM = 0;
    private final int TYPE_NAME = 1;
    private final int TYPE_MAIL = 2;
    private final int TYPE_DATE = 3;
    private final int TYPE_PHONE = 4;
    private final int TYPE_CEP = 5;
    private final int TYPE_CREDIT_CARD = 6;
    private final int TYPE_PASSWORD = 7;
    private final int TYPE_MATCHING = 8;
    private final int TYPE_CPF = 9;
    private final int TYPE_CNPJ = 10;
    private final int TYPE_CREDIT_CARD_DATE = 11;

    private int mInputType;
    private boolean mFieldNeedsValidation;
    private boolean mEmptinessIsValid;
    private String mEmptyErrorText;
    private String mInvalidErrorText;
    private int mMinLength;
    private EditText mMatchingReference;
    private String mMask = "";
    private TypedArray mValues;
    private String mFilterCharSequence;

    private String mRegex;

    private EditText mEditText;

    public AppTextInputLayout(Context context) {
        super(context);
    }

    public AppTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mValues = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AppTextInputLayout, 0, 0);
        initialize(mValues);
    }

    public AppTextInputLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mValues = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AppTextInputLayout, 0, 0);
        initialize(mValues);
    }

    private void initialize(TypedArray values) {
        mFieldNeedsValidation = values.getBoolean(R.styleable.AppTextInputLayout_validation, true);
        mEmptyErrorText = values.getString(R.styleable.AppTextInputLayout_emptyErrorText) == null ? "" : values.getString(R.styleable.AppTextInputLayout_emptyErrorText);
        mInvalidErrorText = values.getString(R.styleable.AppTextInputLayout_invalidErrorText) == null ? "" : values.getString(R.styleable.AppTextInputLayout_invalidErrorText);
        mInputType = values.getInt(R.styleable.AppTextInputLayout_inputTextType, TYPE_CUSTOM);
        mMinLength = values.getInt(R.styleable.AppTextInputLayout_minLength, 0);
        mMask = values.getString(R.styleable.AppTextInputLayout_customMask) == null ? "" : values.getString(R.styleable.AppTextInputLayout_customMask);
        mEmptinessIsValid = values.getBoolean(R.styleable.AppTextInputLayout_emptinessIsValid, false);
        mRegex = values.getString(R.styleable.AppTextInputLayout_pattern) == null ? "" : values.getString(R.styleable.AppTextInputLayout_pattern);
        mFilterCharSequence = values.getString(R.styleable.AppTextInputLayout_filterCharacterSet);
        values.recycle();
    }

    private boolean mIsInitialized = false;

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (getEditText() != null && !mIsInitialized) {
            mIsInitialized = true;
            setInputType();
            setInputFilter(mFilterCharSequence);
        }
    }

    private void setInputType() {
        mEditText = getEditText();
        setEditTextListener(mEditText);
        switch (mInputType) {
            case TYPE_CUSTOM:
            case TYPE_NAME:
            case TYPE_MAIL:
            case TYPE_PASSWORD:
            case TYPE_MATCHING:
                break;
            case TYPE_DATE:
                mMask = TextMask.DATE_MASK;
                break;
            case TYPE_PHONE:
                mMask = TextMask.PHONE_MASK;
                break;
            case TYPE_CEP:
                mMask = TextMask.CEP_MASK;
                break;
            case TYPE_CREDIT_CARD:
                mMask = TextMask.CREDIT_CARD_MASK;
                break;
            case TYPE_CPF:
                mMask = TextMask.CPF_MASK;
                break;
            case TYPE_CNPJ:
                mMask = TextMask.CNPJ_MASK;
                break;
            case TYPE_CREDIT_CARD_DATE:
                mMask = TextMask.CREDIT_CARD_DATE_MASK;
                break;
        }
        if(!mMask.isEmpty()){
            addMask(mEditText, mMask);
        }
    }

    private void setInputFilter(final String blockCharacterSet) {
        if (blockCharacterSet != null) {
            InputFilter filter = new InputFilter() {
                @Override
                public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                    if(charSequence != null) {
                        String textFieldText = charSequence.toString();
                        for(int index = 0; index < blockCharacterSet.length(); index++) {
                            char blockChar = blockCharacterSet.charAt(index);
                            if(textFieldText.indexOf(blockChar) != -1) {
                                textFieldText = textFieldText.replace(String.valueOf(blockChar),  "");
                            }
                        }
                        return textFieldText;
                    }
                    return null;
                }
            };
            if(getEditText() != null) {
                getEditText().setFilters(new InputFilter[]{filter});
            }
        }

    }

    private void addMask(EditText editText, String mask) {
        editText.addTextChangedListener(TextMask.insert(mask, editText));
    }

    private void setEditTextListener(final EditText editTextListener) {
        editTextListener.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (mFieldNeedsValidation) {
                    if (b) {
                        setErrorEnabled(false);
                    } else {
                        validate();
                    }
                }
            }
        });
    }

    public void validate() {
        if(mFieldNeedsValidation) {
            //Type validations
            boolean isFieldValid = true;
            switch (mInputType) {
                case TYPE_CUSTOM:
                case TYPE_NAME:
                case TYPE_PHONE:
                case TYPE_CEP:
                case TYPE_CREDIT_CARD:
                case TYPE_PASSWORD:
                    break;
                case TYPE_DATE:
                    isFieldValid = isDateValid();
                    break;
                case TYPE_CREDIT_CARD_DATE:
                    isFieldValid = isCardDateValid();
                    break;
                case TYPE_CNPJ:
                    isFieldValid = isCnpjValid();
                    break;
                case TYPE_MAIL:
                    isFieldValid = isMailValid();
                    break;
                case TYPE_MATCHING:
                    isFieldValid = isMatchingValid();
                    break;
                case TYPE_CPF:
                    isFieldValid = isCpfValid();
                    break;
            }

            //Emptiness validation
            if (!isNotEmpty()) {
                setEmptyErrorText();
            } else if(!isFieldValid || !isPatternValid()) {
                setInvalidErrorText();
            } else if(!isLengthValid()) {
                if(mMask.isEmpty()) {
                    showMinLengthErrorText();
                } else {
                    setInvalidErrorText();
                }
            } else {
                //if got here it's because field is valid
                setErrorEnabled(false);
            }

        }
    }

    private boolean isDateValid() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            format.setLenient(false);
            format.parse(mEditText.getText().toString());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isCardDateValid() {
        SimpleDateFormat format = new SimpleDateFormat("MM/yy");
        try {
            format.setLenient(false);
            format.parse(mEditText.getText().toString());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isPatternValid() {
        //Returns true only if there' a regex and the Pattern matches
        return mRegex.isEmpty() || Pattern.matches(mRegex, mEditText.getText());
    }

    private boolean isLengthValid() {
        if(!mMask.isEmpty()){

            if(mMask.equals(TextMask.PHONE_MASK)) {
                return hasMinLengthOrMore(TextMask.PHONE_MASK.length() - 1) || hasMinLengthOrMore(TextMask.CEL_PHONE_MASK.length());
            }

            mMinLength = mMask.length();
        }

        return hasMinLengthOrMore(mMinLength);
    }

    private boolean hasMinLengthOrMore(int minLength) {
        return minLength == 0 || mEditText.getText().length() >= minLength;
    }

    private boolean isCpfValid() {
        return IsCpf.isValid(mEditText.getText().toString());
    }

    private boolean isCnpjValid() {
        return IsCnpj.isValid(mEditText.getText().toString());
    }

    private boolean isNotEmpty() {
        return mEmptinessIsValid || !mEditText.getText().toString().isEmpty();
    }

    public boolean isFieldValid() {
        return !isErrorEnabled();
    }

    private boolean isMailValid() {
        return IsEmail.isValid(mEditText.getText().toString());
    }

    private void setEmptyErrorText() {
        setError(mEmptyErrorText.isEmpty() ? getContext().getString(R.string.apptextinputlayout_empty_field, getHint().toString()) : mEmptyErrorText);
    }

    private void setInvalidErrorText() {
        setError(mInvalidErrorText.isEmpty() ? getContext().getString(R.string.apptextinputlayout_invalid_field, getHint().toString()) : mInvalidErrorText);
    }

    private void showMinLengthErrorText() {
        setError(mInvalidErrorText.isEmpty() ? getContext().getString(R.string.apptextinputlaoyut_password_field, getHint().toString(), mMinLength) : mInvalidErrorText);
    }

    private boolean isMatchingValid() {
        try {
            return mMatchingReference.getText().toString().equals(mEditText.getText().toString());
        } catch (NullPointerException e) {
            return true;
        }
    }

    public void setMatchingReference(EditText matchingReference) {
        mMatchingReference = matchingReference;
    }

    public void setText(String text) {
        if (getEditText() != null) {
            getEditText().setText(text);
        }
    }

    public String getText() {
        return getEditText().getText().toString();
    }

    public String getUnmaskedText() {
        return TextMask.unmask(getText());
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mEditText.setOnClickListener(l);
        mEditText.setFocusable(false);
        mEditText.setClickable(true);
        setClickable(true);
        setFocusable(true);
        mEditText.setEnabled(true);
        super.setOnClickListener(l);
    }
}
