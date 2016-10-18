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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import edu.kit.ibds.mowidi.mobile.R;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;

/**
 * This class represents the connection establishment view.<br/>
 * It handles last minute changes of connection settings and presents
 * the hashcode of the connection partner.
 * @author Andre Wengert
 */
public class ConnectView extends Activity {
    /* Views */
	private TextView hashcode;
    private Spinner spinConnect;
    private CheckBox autoCheck;
    private Button okButton;
    /** The intent going back to the main view */
    private Intent outIntent;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect);
        outIntent = this.getIntent();
       

        // Get the views.
        hashcode = (TextView) findViewById(R.id.connect_hashcode);
        spinConnect = (Spinner) findViewById(R.id.connect_spinner);
        autoCheck = (CheckBox) findViewById(R.id.connect_autoCheck);
        okButton = (Button) findViewById(R.id.connect_okButton);
        // Setup spinner for the access rights.
        int arrayResId = R.array.access_rights;
        int viewResId = android.R.layout.simple_spinner_item;
        int resource = android.R.layout.simple_spinner_dropdown_item;
        ArrayAdapter<CharSequence> adaptConnect = ArrayAdapter.createFromResource(
        		ConnectView.this, arrayResId, viewResId);
        adaptConnect.setDropDownViewResource(resource);
        spinConnect.setAdapter(adaptConnect);
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
        okButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                // Return id of selected access right.
                outIntent.putExtra("rights", integerToAuthorizationType(spinConnect.getSelectedItemPosition()));
                // Return checked state.
                outIntent.putExtra("auto", autoCheck.isChecked());
                setResult(RESULT_OK, outIntent);
                finish();
            }

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

    }

    @Override
    public void onStart() {
        super.onStart();
        // Display partner hashcode.
//        FIXME: Decomment after implementation.
//        if (outIntent.hasExtra("hashcode")) {
//        	hashcode.setText(outIntent.getIntExtra("hashcode", 0));
//        	
//        }
//        FIXEND
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
}
