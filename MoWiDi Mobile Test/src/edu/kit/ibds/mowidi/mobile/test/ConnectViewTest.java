<<<<<<< 03d9741868cc86890c11542ccc597a86b3470ad1
/*
 * @(#)SettingsViewTest.java
 */
package edu.kit.ibds.mowidi.mobile.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import edu.kit.ibds.mowidi.mobile.R;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import edu.kit.ibds.mowidi.mobile.ui.ConnectView;

/**
 * Tests for the ConnectView.
 * @author Andre Wengert
 */
public class ConnectViewTest extends
		ActivityInstrumentationTestCase2<ConnectView> {
    private Intent outIntent;
    private ConnectView testActivity;
    private SpinnerAdapter accessRights;
    
	/** Views */
	private TextView hashcode;
    private Spinner spinConnect;
    private CheckBox autoCheck;
    private Button okButton;
	
    /** Constructor */
	public ConnectViewTest() {
		super("edu.kit.ibds.mowidi.mobile", ConnectView.class);
		
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
        hashcode = (TextView) testActivity.findViewById(R.id.connect_hashcode);
	    spinConnect = (Spinner) testActivity.findViewById(R.id.connect_spinner);
        autoCheck = (CheckBox) testActivity.findViewById(R.id.connect_autoCheck);
        okButton = (Button) testActivity.findViewById(R.id.connect_okButton);
        
        accessRights = spinConnect.getAdapter();
	    
	}

	// Test whether views are properly initiated.
	public static final int ADAPTER_COUNT = 3;
	public void testPreConditions() {
		assertNotNull("Hashcode TextView is null", hashcode);
		assertNotNull("Spinner is null", spinConnect);
		assertNotNull("CheckBox is null", autoCheck);
		assertNotNull("OkButton is null", okButton);
		
	    assertNotNull(spinConnect.getOnItemSelectedListener());
	    assertNotNull(accessRights);
	    assertEquals(accessRights.getCount(), ADAPTER_COUNT);
	    
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
 * @(#)SettingsViewTest.java
 */
package edu.kit.ibds.mowidi.mobile.test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import edu.kit.ibds.mowidi.mobile.R;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import edu.kit.ibds.mowidi.mobile.ui.ConnectView;

/**
 * Tests for the ConnectView.
 * @author Andre Wengert
 */
public class ConnectViewTest extends
		ActivityInstrumentationTestCase2<ConnectView> {
    private Intent outIntent;
    private ConnectView testActivity;
    private SpinnerAdapter accessRights;
    
	/** Views */
	private TextView hashcode;
    private Spinner spinConnect;
    private CheckBox autoCheck;
    private Button okButton;
	
    /** Constructor */
	public ConnectViewTest() {
		super("edu.kit.ibds.mowidi.mobile", ConnectView.class);
		
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
        hashcode = (TextView) testActivity.findViewById(R.id.connect_hashcode);
	    spinConnect = (Spinner) testActivity.findViewById(R.id.connect_spinner);
        autoCheck = (CheckBox) testActivity.findViewById(R.id.connect_autoCheck);
        okButton = (Button) testActivity.findViewById(R.id.connect_okButton);
        
        accessRights = spinConnect.getAdapter();
	    
	}

	// Test whether views are properly initiated.
	public static final int ADAPTER_COUNT = 3;
	public void testPreConditions() {
		assertNotNull("Hashcode TextView is null", hashcode);
		assertNotNull("Spinner is null", spinConnect);
		assertNotNull("CheckBox is null", autoCheck);
		assertNotNull("OkButton is null", okButton);
		
	    assertNotNull(spinConnect.getOnItemSelectedListener());
	    assertNotNull(accessRights);
	    assertEquals(accessRights.getCount(), ADAPTER_COUNT);
	    
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
