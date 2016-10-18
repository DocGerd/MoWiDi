/*
 * @(#)MainViewTest.java
 */
package edu.kit.ibds.mowidi.mobile.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import edu.kit.ibds.mowidi.mobile.MainView;
import edu.kit.ibds.mowidi.mobile.PCAdapter;

/**
 * Tests for the MainView.
 * @author Andre Wengert
 */
public class MainViewTest extends
	ActivityInstrumentationTestCase2<MainView> {
	private MainView testActivity;
	private PCAdapter pcAdapter;
	private ListView lv;
	
    /** Constructor */
	public MainViewTest() {
		super("edu.kit.ibds.mowidi.mobile", MainView.class);
		
	}
	
	@Override
	protected void setUp() throws Exception {
		// Setup starting conditions for the test cases!
	    super.setUp();
	    testActivity = this.getActivity();
	    // Disable interaction.
	    setActivityInitialTouchMode(false);
	    lv = testActivity.getListView();
	    pcAdapter = (PCAdapter) lv.getAdapter();
	    
	}
	
	// Test whether views are properly initiated.
	public void testPreConditions() {
		assertNotNull("ListView is null", lv);
		assertNotNull("ListAdapter is null", pcAdapter);
	}
}
