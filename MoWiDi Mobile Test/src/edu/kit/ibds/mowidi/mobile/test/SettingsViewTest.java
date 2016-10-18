<<<<<<< 03d9741868cc86890c11542ccc597a86b3470ad1
/*
 * @(#)SettingsViewTest.java
 */
package edu.kit.ibds.mowidi.mobile.test;

import java.util.Locale;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import edu.kit.ibds.mowidi.mobile.R;
import edu.kit.ibds.mowidi.mobile.ui.SettingsView;

/**
 * Tests for the SettingsView.
 * @author Andre Wengert
 */
public class SettingsViewTest extends
		ActivityInstrumentationTestCase2<SettingsView> {
    private Intent outIntent;
    private SettingsView testActivity;
	
	/** Views */
	private Button button_ok;
    private EditText editGivenName;
    private RadioGroup radio_lang;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    
    /** Constructor */
	public SettingsViewTest() {
		super("edu.kit.ibds.mowidi.mobile", SettingsView.class);
		
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
        button_ok = (Button) testActivity.findViewById(R.id.b_ok);
        radio_lang = (RadioGroup) testActivity.findViewById(R.id.settings_langGroup_label);
        radioButton1 = (RadioButton) testActivity.findViewById(R.id.settings_languageAuto);
        radioButton2 = (RadioButton) testActivity.findViewById(R.id.settings_languageGer);
        radioButton3 = (RadioButton) testActivity.findViewById(R.id.settings_languageEng);
        editGivenName = (EditText) testActivity.findViewById(R.id.settings_mobileName_edit);
	    
	}
	
	public static final int RADIO_COUNT = 3;
	public void testPreConditions() {
		// Test whether views are properly initiated.
		assertNotNull("OkButton is null", button_ok);
		assertNotNull("RadioGroup is null", radio_lang);
		assertNotNull("RadioButton (auto) is null", radioButton1);
		assertNotNull("RadioButton (ger) is null", radioButton2);
		assertNotNull("RadioButton (eng) is null", radioButton3);
		assertNotNull("Editbox is null", editGivenName);
		
	    assertNotNull("EditBox KeyListener is null", editGivenName.getKeyListener());
	    assertEquals("RadioGroup has " + radio_lang.getChildCount()
	    		+ " children instead of " + RADIO_COUNT
	    		+ ".", radio_lang.getChildCount(), RADIO_COUNT);
	    
	}

	private static final String RANDOM_NAME1 = "ernie";
	private static final String ENTRY_ENTER = "E R N I E ENTER";
	public void testNameGiving1() {
		// Test whether a random name can be given via "ENTER".
		sendKeys(ENTRY_ENTER);
		assertEquals("EditBox-1: ", RANDOM_NAME1, editGivenName.getText().toString());
		assertEquals("Intent-1: ", RANDOM_NAME1, outIntent.getCharSequenceExtra("newName"));
		
	}
	
	private static final String RANDOM_NAME2 = "bert";
	private static final String ENTRY_BUTTON = "B E R T DPAD_RIGHT DPAD_CENTER";
	public void testNameGiving2() {
		// Test whether a random name can be given via OkButton.
		sendKeys(ENTRY_BUTTON);
		assertEquals("EditBox-2: ", RANDOM_NAME2, editGivenName.getText().toString());
		assertEquals("Intent-2: ", RANDOM_NAME2, outIntent.getCharSequenceExtra("newName"));
		
	}
	
	private static final String ENTRY_GER = "DPAD_DOWN DPAD_DOWN DPAD_CENTER";
	public void testLanguageChange1() {
		// Test random language changes.
		sendKeys(ENTRY_GER);
		assertEquals("de", testActivity.getBaseContext().getResources()
        		.getConfiguration().locale.getLanguage());
		
	}
	
	private static final String ENTRY_AUTO = "DPAD_DOWN DPAD_CENTER";
	public void testLanguageChange2() {
		// Test random language changes.
		sendKeys(ENTRY_AUTO);
		assertEquals("en", Locale.getDefault().getLanguage());
		
	}
	
}
=======
/*
 * @(#)SettingsViewTest.java
 */
package edu.kit.ibds.mowidi.mobile.test;

import java.util.Locale;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import edu.kit.ibds.mowidi.mobile.R;
import edu.kit.ibds.mowidi.mobile.ui.SettingsView;

/**
 * Tests for the SettingsView.
 * @author Andre Wengert
 */
public class SettingsViewTest extends
		ActivityInstrumentationTestCase2<SettingsView> {
    private Intent outIntent;
    private SettingsView testActivity;
	
	/** Views */
	private Button button_ok;
    private EditText editGivenName;
    private RadioGroup radio_lang;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    
    /** Constructor */
	public SettingsViewTest() {
		super("edu.kit.ibds.mowidi.mobile", SettingsView.class);
		
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
        button_ok = (Button) testActivity.findViewById(R.id.b_ok);
        radio_lang = (RadioGroup) testActivity.findViewById(R.id.settings_langGroup_label);
        radioButton1 = (RadioButton) testActivity.findViewById(R.id.settings_languageAuto);
        radioButton2 = (RadioButton) testActivity.findViewById(R.id.settings_languageGer);
        radioButton3 = (RadioButton) testActivity.findViewById(R.id.settings_languageEng);
        editGivenName = (EditText) testActivity.findViewById(R.id.settings_mobileName_edit);
	    
	}
	
	public static final int RADIO_COUNT = 3;
	public void testPreConditions() {
		// Test whether views are properly initiated.
		assertNotNull("OkButton is null", button_ok);
		assertNotNull("RadioGroup is null", radio_lang);
		assertNotNull("RadioButton (auto) is null", radioButton1);
		assertNotNull("RadioButton (ger) is null", radioButton2);
		assertNotNull("RadioButton (eng) is null", radioButton3);
		assertNotNull("Editbox is null", editGivenName);
		
	    assertNotNull("EditBox KeyListener is null", editGivenName.getKeyListener());
	    assertEquals("RadioGroup has " + radio_lang.getChildCount()
	    		+ " children instead of " + RADIO_COUNT
	    		+ ".", radio_lang.getChildCount(), RADIO_COUNT);
	    
	}

	private static final String RANDOM_NAME1 = "ernie";
	private static final String ENTRY_ENTER = "E R N I E ENTER";
	public void testNameGiving1() {
		// Test whether a random name can be given via "ENTER".
		sendKeys(ENTRY_ENTER);
		assertEquals("EditBox-1: ", RANDOM_NAME1, editGivenName.getText().toString());
		assertEquals("Intent-1: ", RANDOM_NAME1, outIntent.getCharSequenceExtra("newName"));
		
	}
	
	private static final String RANDOM_NAME2 = "bert";
	private static final String ENTRY_BUTTON = "B E R T DPAD_RIGHT DPAD_CENTER";
	public void testNameGiving2() {
		// Test whether a random name can be given via OkButton.
		sendKeys(ENTRY_BUTTON);
		assertEquals("EditBox-2: ", RANDOM_NAME2, editGivenName.getText().toString());
		assertEquals("Intent-2: ", RANDOM_NAME2, outIntent.getCharSequenceExtra("newName"));
		
	}
	
	private static final String ENTRY_GER = "DPAD_DOWN DPAD_DOWN DPAD_CENTER";
	public void testLanguageChange1() {
		// Test random language changes.
		sendKeys(ENTRY_GER);
		assertEquals("de", testActivity.getBaseContext().getResources()
        		.getConfiguration().locale.getLanguage());
		
	}
	
	private static final String ENTRY_AUTO = "DPAD_DOWN DPAD_CENTER";
	public void testLanguageChange2() {
		// Test random language changes.
		sendKeys(ENTRY_AUTO);
		assertEquals("en", Locale.getDefault().getLanguage());
		
	}
	
}
>>>>>>> no message
