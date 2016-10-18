<<<<<<< 03d9741868cc86890c11542ccc597a86b3470ad1
/*
 * @(#)ConSettingsViewTest.java
 */
package edu.kit.ibds.mowidi.mobile.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import edu.kit.ibds.mowidi.mobile.R;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import edu.kit.ibds.mowidi.mobile.ui.ConSettingsView;

/**
 * Tests for the ConSettingsView.
 * @author Andre Wengert
 */
public class ConSettingsViewTest extends
		ActivityInstrumentationTestCase2<ConSettingsView> {
	private Intent outIntent;
	private ConSettingsView testActivity;
    private SpinnerAdapter accessRights;
	
	/** Views */
    private TextView actualName;
    private EditText editPCName;
    private Button okButton;
    private Spinner spinConnect;
    private CheckBox autoCheck;
	
    /** Constructor */
	public ConSettingsViewTest() {
		super("edu.kit.ibds.mowidi.mobile", ConSettingsView.class);
		
	}
	
	@Override
	protected void setUp() throws Exception {
		// Setup starting conditions for the test cases!
	    super.setUp();
	    testActivity = this.getActivity();
	    outIntent = testActivity.getIntent();
	    // Disable interaction.
	    setActivityInitialTouchMode(false);
	    
	    // Get the views.
        actualName = (TextView) testActivity.findViewById(R.id.con_settings_name);
        editPCName = (EditText) testActivity.findViewById(R.id.con_settings_nameEdit);
        okButton = (Button) testActivity.findViewById(R.id.con_settings_okButton);
        spinConnect = (Spinner) testActivity.findViewById(R.id.con_settings_spinner);
        autoCheck = (CheckBox) testActivity.findViewById(R.id.con_settings_check);
        
        accessRights = spinConnect.getAdapter();
	    
	}
	
	// Test whether views are properly initiated.
	public static final int ADAPTER_COUNT = 3;
	public void testPreConditions() {
		assertNotNull("Actual name TextView is null", actualName);
		assertNotNull("Editbox is null", editPCName);
		assertNotNull("OkButton is null", okButton);
		assertNotNull("Spinner is null", spinConnect);
		assertNotNull("CheckBox is null", autoCheck);
		
		assertNotNull("EditBox KeyListener is null", editPCName.getKeyListener());
	    assertNotNull(spinConnect.getOnItemSelectedListener());
	    assertNotNull(accessRights);
	    assertEquals(accessRights.getCount(), ADAPTER_COUNT);
	    
	}
	
	private static final String RANDOM_NAME1 = "ernie";
	private static final String ENTRY_ENTER = "E R N I E ENTER";
	public void testPCNameGiving1() {
		// Test whether a random name can be given via "ENTER".
		sendKeys(ENTRY_ENTER);
		assertEquals("EditBox-1: ", RANDOM_NAME1, editPCName.getText().toString());
		assertEquals("Intent-1: ", RANDOM_NAME1, outIntent.getCharSequenceExtra("givenName"));
		
	}
	
	private static final String RANDOM_NAME2 = "bert";
	private static final String ENTRY_BUTTON = "B E R T DPAD_RIGHT DPAD_CENTER";
	public void testPCNameGiving2() {
		// Test whether a random name can be given via OkButton.
		sendKeys(ENTRY_BUTTON);
		assertEquals("EditBox-2: ", RANDOM_NAME2, editPCName.getText().toString());
		assertEquals("Intent-2: ", RANDOM_NAME2, outIntent.getCharSequenceExtra("givenName"));
		
	}
	
	// Test whether random rights are given!
	public static final int INITIAL_POSITION = 0;
	public static final String ENTRY_RW = "DPAD_CENTER DPAD_DOWN DPAD_DOWN DPAD_CENTER";
	public static final String ENTRY_R = "DPAD_CENTER DPAD_DOWN DPAD_CENTER";
	boolean r;
	boolean w;
	public void testRights() {
		resetSpinner();
		// Test 'PENDING'
		getRightsExtra();
		assertFalse("PENDING has wrong rights", r || w);
		// Test 'READ_ONLY'
		sendKeys(ENTRY_R);
		getRightsExtra();
		assertTrue("READ_ONLY has wrong rights: r=" + r + " w=" + w, r && !w);
		resetSpinner();
		// Test 'READ_AND_WRITE'
		sendKeys(ENTRY_RW);
		getRightsExtra();
		assertTrue("READ_AND_WRITE has wrong rights", r && w);
		resetSpinner();

	}
	
	// Test correctness of check box!
	public static final boolean CHECKED = true;
	public void testAutoCheck() {
		testActivity.runOnUiThread(new Runnable() {
			public void run() {
				autoCheck.requestFocus();
			}
		});
		sendKeys("DPAD_CENTER");
		assertEquals(CHECKED, outIntent.getBooleanExtra("auto", false));
		sendKeys("DPAD_CENTER");
		assertEquals(!CHECKED, outIntent.getBooleanExtra("auto", false));
		
	}
	
	/*
	 * Helper operation to reset the spinner to 'PENDING'
	 */
	private void resetSpinner() {
		testActivity.runOnUiThread(new Runnable() {
			public void run() {
				spinConnect.requestFocus();
				spinConnect.setSelection(INITIAL_POSITION);
				
			}
		});
	}
	
	/*
	 * Helper operation to get currents access rights
	 */
	private void getRightsExtra() {
		r = ((AuthorizationType) outIntent.getSerializableExtra("rights")).mayRead();
		w = ((AuthorizationType) outIntent.getSerializableExtra("rights")).mayWrite();
		
	}

}
=======
/*
 * @(#)ConSettingsViewTest.java
 */
package edu.kit.ibds.mowidi.mobile.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import edu.kit.ibds.mowidi.mobile.R;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import edu.kit.ibds.mowidi.mobile.ui.ConSettingsView;

/**
 * Tests for the ConSettingsView.
 * @author Andre Wengert
 */
public class ConSettingsViewTest extends
		ActivityInstrumentationTestCase2<ConSettingsView> {
	private Intent outIntent;
	private ConSettingsView testActivity;
    private SpinnerAdapter accessRights;
	
	/** Views */
    private TextView actualName;
    private EditText editPCName;
    private Button okButton;
    private Spinner spinConnect;
    private CheckBox autoCheck;
	
    /** Constructor */
	public ConSettingsViewTest() {
		super("edu.kit.ibds.mowidi.mobile", ConSettingsView.class);
		
	}
	
	@Override
	protected void setUp() throws Exception {
		// Setup starting conditions for the test cases!
	    super.setUp();
	    testActivity = this.getActivity();
	    outIntent = testActivity.getIntent();
	    // Disable interaction.
	    setActivityInitialTouchMode(false);
	    
	    // Get the views.
        actualName = (TextView) testActivity.findViewById(R.id.con_settings_name);
        editPCName = (EditText) testActivity.findViewById(R.id.con_settings_nameEdit);
        okButton = (Button) testActivity.findViewById(R.id.con_settings_okButton);
        spinConnect = (Spinner) testActivity.findViewById(R.id.con_settings_spinner);
        autoCheck = (CheckBox) testActivity.findViewById(R.id.con_settings_check);
        
        accessRights = spinConnect.getAdapter();
	    
	}
	
	// Test whether views are properly initiated.
	public static final int ADAPTER_COUNT = 3;
	public void testPreConditions() {
		assertNotNull("Actual name TextView is null", actualName);
		assertNotNull("Editbox is null", editPCName);
		assertNotNull("OkButton is null", okButton);
		assertNotNull("Spinner is null", spinConnect);
		assertNotNull("CheckBox is null", autoCheck);
		
		assertNotNull("EditBox KeyListener is null", editPCName.getKeyListener());
	    assertNotNull(spinConnect.getOnItemSelectedListener());
	    assertNotNull(accessRights);
	    assertEquals(accessRights.getCount(), ADAPTER_COUNT);
	    
	}
	
	private static final String RANDOM_NAME1 = "ernie";
	private static final String ENTRY_ENTER = "E R N I E ENTER";
	public void testPCNameGiving1() {
		// Test whether a random name can be given via "ENTER".
		sendKeys(ENTRY_ENTER);
		assertEquals("EditBox-1: ", RANDOM_NAME1, editPCName.getText().toString());
		assertEquals("Intent-1: ", RANDOM_NAME1, outIntent.getCharSequenceExtra("givenName"));
		
	}
	
	private static final String RANDOM_NAME2 = "bert";
	private static final String ENTRY_BUTTON = "B E R T DPAD_RIGHT DPAD_CENTER";
	public void testPCNameGiving2() {
		// Test whether a random name can be given via OkButton.
		sendKeys(ENTRY_BUTTON);
		assertEquals("EditBox-2: ", RANDOM_NAME2, editPCName.getText().toString());
		assertEquals("Intent-2: ", RANDOM_NAME2, outIntent.getCharSequenceExtra("givenName"));
		
	}
	
	// Test whether random rights are given!
	public static final int INITIAL_POSITION = 0;
	public static final String ENTRY_RW = "DPAD_CENTER DPAD_DOWN DPAD_DOWN DPAD_CENTER";
	public static final String ENTRY_R = "DPAD_CENTER DPAD_DOWN DPAD_CENTER";
	boolean r;
	boolean w;
	public void testRights() {
		resetSpinner();
		// Test 'PENDING'
		getRightsExtra();
		assertFalse("PENDING has wrong rights", r || w);
		// Test 'READ_ONLY'
		sendKeys(ENTRY_R);
		getRightsExtra();
		assertTrue("READ_ONLY has wrong rights: r=" + r + " w=" + w, r && !w);
		resetSpinner();
		// Test 'READ_AND_WRITE'
		sendKeys(ENTRY_RW);
		getRightsExtra();
		assertTrue("READ_AND_WRITE has wrong rights", r && w);
		resetSpinner();

	}
	
	// Test correctness of check box!
	public static final boolean CHECKED = true;
	public void testAutoCheck() {
		testActivity.runOnUiThread(new Runnable() {
			public void run() {
				autoCheck.requestFocus();
			}
		});
		sendKeys("DPAD_CENTER");
		assertEquals(CHECKED, outIntent.getBooleanExtra("auto", false));
		sendKeys("DPAD_CENTER");
		assertEquals(!CHECKED, outIntent.getBooleanExtra("auto", false));
		
	}
	
	/*
	 * Helper operation to reset the spinner to 'PENDING'
	 */
	private void resetSpinner() {
		testActivity.runOnUiThread(new Runnable() {
			public void run() {
				spinConnect.requestFocus();
				spinConnect.setSelection(INITIAL_POSITION);
				
			}
		});
	}
	
	/*
	 * Helper operation to get currents access rights
	 */
	private void getRightsExtra() {
		r = ((AuthorizationType) outIntent.getSerializableExtra("rights")).mayRead();
		w = ((AuthorizationType) outIntent.getSerializableExtra("rights")).mayWrite();
		
	}

}
>>>>>>> no message
