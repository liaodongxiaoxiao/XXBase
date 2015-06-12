package com.ldxx.xxbase.demo.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import com.ldxx.xxbase.R;
import com.ldxx.xxbase.activity.BaseActivity;

/**
 * Created by WangZhuo on 2015/6/6.
 */
public class DemoTextInputLayoutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_textinput);
        initView();
        initActionBar();
    }

    private void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.common_actionbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.a_d_t_textinputlayout);

    }

    private void initView() {
        final TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout);

        EditText editText = textInputLayout.getEditText();
        textInputLayout.setHint("Password");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() > 4) {
                    textInputLayout.setError("Password error");
                    textInputLayout.setErrorEnabled(true);
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}



