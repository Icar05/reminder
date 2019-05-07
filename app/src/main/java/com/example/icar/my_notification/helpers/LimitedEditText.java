package com.example.icar.my_notification.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.icar.my_notification.R;

/**
 * Created by icar on 17.05.17.
 */

public class LimitedEditText extends EditText {

        /**
         * Max lines to be present in editable text field
         */
        private int maxLines = 5;

        /**
         * Max characters to be present in editable text field
         */
        private int maxCharacters = 50;

        /**
         * application context;
         */
        private Context context;

        public int getMaxCharacters() {
            return maxCharacters;
        }

        public void setMaxCharacters(int maxCharacters) {
            this.maxCharacters = maxCharacters;
        }

        @Override
        public int getMaxLines() {
            return maxLines;
        }

        @Override
        public void setMaxLines(int maxLines) {
            this.maxLines = maxLines;
        }

        public LimitedEditText(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            this.context = context;
            init();
        }

        public LimitedEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.context = context;
            init();
        }

        public LimitedEditText(Context context) {
            super(context);
            this.context = context;
            init();
        }

        @Override
        protected void onFinishInflate() {
            super.onFinishInflate();

            TextWatcher watcher = new TextWatcher() {

                private String text;
                private int beforeCursorPosition = 0;

                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                    //TODO sth

                    Log.d("textChanges", " lines "+LimitedEditText.this.getLineCount()+" char "+
                            s.toString().length());
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                    text = s.toString();
                    beforeCursorPosition = start;
                }

                @Override
                public void afterTextChanged(Editable s) {

            /* turning off listener */
                    removeTextChangedListener(this);

            /* handling lines limit exceed */
                    if (LimitedEditText.this.getLineCount() > maxLines) {
                        LimitedEditText.this.setText(text);
                        LimitedEditText.this.setSelection(beforeCursorPosition);


                        showText(context.getString(R.string.max_lines_limited));
                    }

            /* handling character limit exceed */
                    if (s.toString().length() > maxCharacters) {
                        LimitedEditText.this.setText(text);
                        LimitedEditText.this.setSelection(beforeCursorPosition);


                        showText(context.getString(R.string.max_character_limited));
                    }

            /* turning on listener */
                    addTextChangedListener(this);

                }
            };

            this.addTextChangedListener(watcher);
        }


        private void showText(String text){
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }

        private void init() {
            if (!isInEditMode()) {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Constants.CUSTOM_FONT);
                setTypeface(tf);
            }
        }

    }

