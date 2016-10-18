/*
 * @(#)SettingsView.java
 *
 * This file is part of MoWiDi.
 *
 * MoWiDi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MoWiDi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MoWiDi. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2010 by PSE23-Team:
 *
 * Patrick Kuhn, Michael Auracher,
 * André Wengert, Kim Spieß, Christopher Schütze
 *
 * 2010-08-15	Fix minor bug and polished AW
 * 2010-08-16	Fixed onStart bugs AW
 */
package edu.kit.ibds.mowidi.mobile.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import edu.kit.ibds.mowidi.mobile.R;
import java.util.Locale;

/**
 * This class takes care of mobile settings manipulation.<br/>
 * It is used to change various settings concerning the mobile itself.
 * @author Andre Wengert
 */
public class SettingsView extends Activity implements OnClickListener {
    /* Views */
    private Button button_ok;
    private EditText editGivenName;
    private RadioGroup radio_lang;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    /** The intent going back to the main view */
    private Intent outIntent;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        outIntent = getIntent();
        
        // Get the views.
        button_ok = (Button) findViewById(R.id.b_ok);
        radio_lang = (RadioGroup) findViewById(R.id.settings_langGroup_label);
        radioButton1 = (RadioButton) findViewById(R.id.settings_languageAuto);
        radioButton2 = (RadioButton) findViewById(R.id.settings_languageGer);
        radioButton3 = (RadioButton) findViewById(R.id.settings_languageEng);
        editGivenName = (EditText) findViewById(R.id.settings_mobileName_edit);
        // Apply listeners.
        button_ok.setOnClickListener(this);
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
        editGivenName.setOnKeyListener(new OnKeyListener() {

        	@Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // "Enter" was pressed on the tabboard.
                    changeGivenName();
                    return true;

                }
                return false;

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Show user given name in the edit box.
        if (outIntent.hasExtra("newName")) {
        	editGivenName.setText(outIntent.getCharSequenceExtra("newName"));
        }
        // Check language respective radio button.
        String usedLanguage = getBaseContext().getResources()
        		.getConfiguration().locale.getLanguage();
        radio_lang.check(getLanguageId(usedLanguage));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_ok:
                changeGivenName();
                break;

            case R.id.settings_languageAuto:
                changeLanguage("auto");
                break;

            case R.id.settings_languageGer:
                changeLanguage("de");
                break;

            case R.id.settings_languageEng:
                changeLanguage("en");
                break;

            default:
                break;
                
        }
    }

    /**
     * Changes the user given mobile name.
     */
    private void changeGivenName() {
        String newName = editGivenName.getText().toString();
        if (checkGivenName(newName)) {
            outIntent.putExtra("newName", newName);
            setResult(RESULT_OK, outIntent);
            
        } else {
        	String errMsg = getString(R.string.too_short);
            Toast.makeText(getBaseContext(), errMsg, Toast.LENGTH_SHORT).show();
            
        }
    }

    /**
     * Checks a name for various name giving rules.
     * @param input	The name to check.
     * @return Result of the check.
     */
    private boolean checkGivenName(String input) {
        if (input.length() > 2) {
            return true;

        } else {
            return false;

        }
    }

    /**
     * Changes the language of the application at runtime.
     * @param lang The Language code.
     */
    private void changeLanguage(String lang) {
        Locale locale;
        if (lang.equals("auto")) {
            locale = Locale.getDefault();

        } else {
            locale = new Locale(lang);

        }
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        updateSettings();

    }

    /**
     * Updates the UI (after language change).
     */
    private void updateSettings() {
        button_ok.setText(R.string.ok);
        radioButton1.setText(R.string.automatic);
        radioButton2.setText(R.string.german);
        radioButton3.setText(R.string.english);
        ((TextView) findViewById(R.id.settings_mobileName_label)).setText(R.string.mobile_name);
        ((TextView) findViewById(R.id.settings_language_label)).setText(R.string.language);

    }

    /**
     * Callback for creating dialogs managed by the activity.
     * @param lang The Language code.
     * @return id The identifier.
     */
    private int getLanguageId(String lang) {
        int id = R.id.settings_languageAuto;
        String globalLang = Locale.getDefault().getLanguage();
        if (lang.equals(globalLang)) {
            // Do nothing
        } else if (lang.equals("de")) {
            id = R.id.settings_languageGer;

        } else if (lang.equals("en")) {
            id = R.id.settings_languageEng;

        }
        return id;

    }
}
