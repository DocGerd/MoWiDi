/*
 * @(#)ConSettingsView.java
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
 * 2010-08-15	Polishing AW
 * 2010-08-16	Fixed onStart bugs AW
 */
package edu.kit.ibds.mowidi.mobile.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import edu.kit.ibds.mowidi.mobile.R;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;

/**
 * This class represents the connection settings view.<br/>
 * It handles various connection relevant settings.
 * @author Andre Wengert
 */
public final class ConSettingsView extends Activity {
    /** Views */
    private TextView actualName;
    private EditText editPCName;
    private Button okButton;
    private Spinner spinConnect;
    private CheckBox autoCheck;
    /** The intent going back to the main view */
    private Intent outIntent;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.con_settings);
        outIntent = getIntent();
        
        // Get the views.
        actualName = (TextView) findViewById(R.id.con_settings_name);
        editPCName = (EditText) findViewById(R.id.con_settings_nameEdit);
        okButton = (Button) findViewById(R.id.con_settings_okButton);
        spinConnect = (Spinner) findViewById(R.id.con_settings_spinner);
        autoCheck = (CheckBox) findViewById(R.id.con_settings_check);

        // Setup Editbox for user given PC name.
        editPCName.setOnKeyListener(new OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If "Enter" was pressed on the tabboard
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    changePCName();
                    return true;
                    
                }
                return false;
                
            }
        });
        // Setup Verify button for user given PC name.
        okButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                changePCName();
                
            }
        });
        // Setup spinner for the access rights.
        int arrayResId = R.array.access_rights;
        int viewResId = android.R.layout.simple_spinner_item;
        int resource = android.R.layout.simple_spinner_dropdown_item;
        ArrayAdapter<CharSequence> adaptSettings = ArrayAdapter.createFromResource(
                ConSettingsView.this, arrayResId, viewResId);
        adaptSettings.setDropDownViewResource(resource);
        spinConnect.setAdapter(adaptSettings);
        spinConnect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	int selected;
        	
            @Override
            public void onItemSelected(final AdapterView<?> parent,
                    final View view, final int pos, final long id) {
            	selected = spinConnect.getSelectedItemPosition();
            	finalizeSelection(selected);
                
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                selected = 0;
                finalizeSelection(selected);
                
            }
            
            /**
             * Saves the selection for the outgoing intent.
             * @param sel Selected spinner item.
             */
            private void finalizeSelection(int sel) {
            	outIntent.putExtra("rights", integerToAuthorizationType(sel));
                setResult(RESULT_OK, outIntent);
                
            }
            
            /**
             * Set authorization type from spinner selection.
             * @param i Selected spinner item.
             */
            private AuthorizationType integerToAuthorizationType(final int i) {
                switch (i) {
                    case 0:
                        return AuthorizationType.PENDING;
                        
                    case 1:
                        return AuthorizationType.READ_ONLY;
                        
                    case 2:
                        return AuthorizationType.READ_AND_WRITE;
                        
                    default:
                        return null; 
                        
                }
            }
        });
        // Setup check box for automatic connection establishment.
        autoCheck.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                outIntent.putExtra("auto", autoCheck.isChecked());
                setResult(RESULT_OK, outIntent);
                
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Display actual PC name.
        if (outIntent.hasExtra("actualName")) {
        	actualName.setText(outIntent.getStringExtra("actualName"));
        }
        // Display currently given PC name.
        if (outIntent.hasExtra("givenName")) {
        	editPCName.setText(outIntent.getStringExtra("givenName"));
        }
        // Display currently given access rights.
        if (outIntent.hasExtra("rights")) {
	        AuthorizationType rights = (AuthorizationType) outIntent.getSerializableExtra("rights");
	        if (rights.mayWrite()) {
	            spinConnect.setSelection(2);
	            
	        } else if (rights.mayRead()) {
	            spinConnect.setSelection(1);
	            
	        } else {
	            spinConnect.setSelection(0);
	            
	        }
        }
        // Display current auto connect setting.
        if (outIntent.hasExtra("auto")) {
	        boolean autoCon = outIntent.getBooleanExtra("auto", false);
	        if (autoCon) {
	            autoCheck.setChecked(true);
	            
	        } else {
	            autoCheck.setChecked(false);
	            
	        }
        }
    }

    /**
     * Changes the user given pc name.
     */
    private void changePCName() {
        String newName = editPCName.getText().toString();
        if (checkPCName(newName)) {
            outIntent.putExtra("givenName", newName);
            setResult(RESULT_OK, outIntent);
            
        } else {
        	String errMsg = getString(R.string.too_short);
            Toast.makeText(getBaseContext(), errMsg, Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Checks a name for various name giving rules.
     * @param input The name to check.
     * @return Result of the check.
     */
    private boolean checkPCName(final String input) {
        if (input.length() > 2) {
            return true;
            
        } else {
            return false;
            
        }
    }
}
