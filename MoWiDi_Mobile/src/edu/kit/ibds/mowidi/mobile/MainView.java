/*
 * @(#)MainView.java
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
 * 2010-06-23   getter for the context PK
 * 2010-07-03   Fix for "No PCs available" AW
 * 2010-07-04   Updated dialogs and "commented" mobileDevice object due to getUID() missing AW
 *              Updated onCreateDialog() and added onItemLongClick()
 * 2010-07-09   refactor etc etc etc PK
 * 2010-07-12   Delete unused stuff AW
 * 2010-07-13   'clickedPC' introduced because intending index is senseless and dialog incompatibilities AW
 *                  Changed various things according to attribute 'clickedPC' AW
 *                  Added some helper procedures AW
 */
package edu.kit.ibds.mowidi.mobile;

import java.util.UUID;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import edu.kit.ibds.mowidi.mobile.data.AuthorizationType;
import edu.kit.ibds.mowidi.mobile.data.Model;
import edu.kit.ibds.mowidi.mobile.data.PCDevice;
import edu.kit.ibds.mowidi.mobile.network.ConnectionOnMobile;
import edu.kit.ibds.mowidi.mobile.network.MobileDevice;
import edu.kit.ibds.mowidi.mobile.ui.ConSettingsView;
import edu.kit.ibds.mowidi.mobile.ui.ConnectView;
import edu.kit.ibds.mowidi.mobile.ui.SettingsView;

/**
 * This class represents the main view and controller of the mobile software.<br/>
 * It handles all user interaction and communication with the mobile model.
 * @author Andre Wengert
 */
public final class MainView extends ListActivity implements
        OnItemClickListener, OnItemLongClickListener {
// <editor-fold defaultstate="collapsed" desc="IDs for the dialogs.">

    /** ID for disconnect dialog. */
    private static final int DIALOG_DISCONNECT_ID = 0;
    /** ID for info dialog. */
    private static final int DIALOG_INFO_ID = 1;
    /** ID for help dialog. */
    private static final int DIALOG_HELP_ID = 2;
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="IDs for the interaction activities.">
    /** ID for give mobile name activity. */
    private static final int GIVE_MOBILE_NAME = 0;
    /** ID for connect to PC activity. */
    private static final int CONNECT_TO_PC = 1;
    /** ID for connection settings activity. */
    private static final int CON_SETTINGS = 2;
    /** Time interval to refresh the pc list. */
    protected static final int LIST_REFRESH_INTERVAL = 3;
// </editor-fold>
    /** Model of the mobile. */
    private Model model;
    /** MobileDevice for UPNP and network stuff. */
    private MobileDevice mobileDevice;
    /** A custom ListAdapter. */
    private PCAdapter pcAdapter;
    /** Addressed PC in the list. */
    private PCDevice clickedPC;
    /** Context of our application. */
    private static volatile Context context = null;
    /** DiscoveryNotificator intent. */
    Intent servIntent;
    /** List refresh thread. */
    Thread upThread;
    /** Handler for callbacks to the UI thread. */
    final Handler updateHandler = new Handler();
    /** Runnable for posting. */
    final Runnable updateTask = new Runnable() {

        public void run() {
            refreshUI();
            Log.v(MainView.class.getName(), "List Updater casts Lvl.6 REFRESH");
            // Repeat          
            updateHandler.postDelayed(this, LIST_REFRESH_INTERVAL * 1000);

        }
    };

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (context == null) {
            context = (Context) this;
        }
        setContentView(R.layout.main);

        // Setup mobile.
        model = new Model();
        model.loadAll();
        String friendlyName = model.getSettings().getGivenName();
        UUID uid = model.getSettings().getUID();
        mobileDevice = new MobileDevice(this.getResources(), friendlyName, uid);

//        // Prepare Intent for discovery service.
//        servIntent = new Intent(MainView.this, DiscoveryNotificator.class);
//        Log.i("MainView", "Service intent created");
//        servIntent.putExtra("pcs", mobileDevice.getConnectees());
//        Log.i("MainView", "Intent with extras");
//        // Start notification Service
//        startService(servIntent);
//        Log.i("MainView", "Service started");
        // Start listening for PCs willing to connect.
        Thread md = new Thread(mobileDevice);
        md.start();

        // Initiate list updater
        upThread = new Thread() {

            @Override
            public void run() {
                // Do nothing serious (actually).
                updateHandler.removeCallbacks(updateTask);
                updateHandler.postDelayed(updateTask, 100);
            }
        };
        upThread.start();

        // Create list view and listeners.
        pcAdapter = new PCAdapter((Context) this, R.layout.main_item,
                mobileDevice.getConnectees());
        ListView lv = getListView();
        lv.setAdapter(pcAdapter);
        lv.setOnItemClickListener((OnItemClickListener) this);
        lv.setOnItemLongClickListener((OnItemLongClickListener) this);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Update list manually.
        pcAdapter.notifyDataSetChanged();
        ((TextView) findViewById(R.id.main_empty)).setText(R.string.no_items);

    }

    @Override
    protected void onPause() {
        super.onPause();
        model.saveAll();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(MainView.class.getName(), "onDestroy called");
        // Stop all background processes.
        model.saveAll();
        mobileDevice.release();
        mobileDevice.stop();
        mobileDevice = null;
        upThread.stop();
//        stopService(servIntent);

    }

    /**
     * Called when a launched activity exits.
     * @param reqCode
     *            Request code to identify who this result came from.
     * @param resCode
     *            Result Code returned by the child activity.
     * @param intent
     *            Intent returning eventual result data.
     */
    @Override
    protected void onActivityResult(final int reqCode, final int resCode,
            final Intent intent) {
        super.onActivityResult(reqCode, resCode, intent);
        AuthorizationType chosenRights;
        if (resCode == RESULT_CANCELED) {
            // Do nothing (bad style but better than extreme intending).
            return;

        }
        switch (reqCode) {
            case GIVE_MOBILE_NAME:
                String newName = intent.getStringExtra("newName");
                model.getSettings().setGivenName(newName);
                mobileDevice.setUPC(newName);
                break;

            case CONNECT_TO_PC:
                chosenRights = (AuthorizationType) intent.getSerializableExtra("rights");
                try {
                    // Set access rights.
                    clickedPC.setAuthType(chosenRights);
                    // Set auto connect.
                    clickedPC.setPermanent(intent.getBooleanExtra("auto", false));
                    // Establish connection.
                    connectTo(clickedPC);
                } catch (Exception e) {
                    // FIXME: Error handling when PC is out of reach at that point.
                    throw new RuntimeException(e);

                }
                break;

            case CON_SETTINGS:
                chosenRights = (AuthorizationType) intent.getSerializableExtra("rights");
                try {
                    // Set given Name.
                    clickedPC.setGivenName(intent.getStringExtra("givenName"));
                    // Set access rights.
                    clickedPC.setAuthType(chosenRights);
                    // Set auto connect.
                    clickedPC.setPermanent(intent.getBooleanExtra("auto", false));
                } catch (IndexOutOfBoundsException e) {
                    // FIXME: Error handling when PC is out of reach at that point.
                    throw new RuntimeException(e);

                }
                break;

            default:
                break;

        }
    }

    /**
     * Get the Context of the MainView.
     * @return context, <tt>null</tt> if not available in this context
     */
    public static Context getContext() {
        return context;

    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int pos, final long id) {
        // This represents a simple click on a PC list entry!
        PCDevice connected = mobileDevice.getConnectedPC();
        setClickedPC(id);
        if (connected != null && connected.isConnected()) {
            // A connection is already established.
            showDialog(DIALOG_DISCONNECT_ID);
        } else {
            // No current connection established.
            startConSetup();

        }
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        // This represents a long held click on a PC list entry!
        setClickedPC(id);
        Intent intent = new Intent(this, ConSettingsView.class);
        intent.putExtra("actualName", clickedPC.getuID().toString());
        intent.putExtra("givenName", clickedPC.getGivenName());
        intent.putExtra("rights", clickedPC.getAuthType());
        intent.putExtra("auto", clickedPC.isPermanent());
        // Change to connection settings view.
        startActivityForResult(intent, CON_SETTINGS);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Called when the options menu is first created!
        updateMenu(menu);
        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        // Called every time before the options menu is shown!
        updateMenu(menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem mItem) {
        // Called when the options menu item is selected!
        switch (mItem.getItemId()) {
            case R.id.opt_settings:
                Intent intent = new Intent(this, SettingsView.class);
                intent.putExtra("newName", model.getSettings().getGivenName());
                // Change to mobile settings view.
                startActivityForResult(intent, GIVE_MOBILE_NAME);
                break;

            case R.id.opt_info:
                showDialog(DIALOG_INFO_ID);
                break;

            case R.id.opt_help:
                showDialog(DIALOG_HELP_ID);
                break;

            case R.id.opt_quit:
                // Destroy the application.
                this.finish();
                break;

            default:
                return false;

        }
        return true;

    }

    @Override
    public Dialog onCreateDialog(final int id) {
        // Called when a dialog is first created!
        switch (id) {
            case DIALOG_DISCONNECT_ID:
                return new AlertDialog.Builder(MainView.this).setIcon(R.drawable.alert_dialog_icon).setTitle(R.string.warning).setMessage(R.string.warning_disconnect).setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(final DialogInterface dialog, final int button) {
                                // On 'Yes' terminate connection, setup new connection if not current.
                                PCDevice current = mobileDevice.getConnectedPC();
                                terminateConnection();
                                if (current != clickedPC) {
                                    startConSetup();
                                }
                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, final int button) {
                        // On 'No' close dialog.
                        dialog.dismiss();

                    }
                }).create();

            case DIALOG_INFO_ID:
                Dialog diaInfo = new Dialog((Context) this);
                diaInfo.setContentView(R.layout.info_dialog);
                diaInfo.setTitle(R.string.info);
                return diaInfo;

            case DIALOG_HELP_ID:
                Dialog diaHelp = new Dialog((Context) this);
                diaHelp.setContentView(R.layout.help_dialog);
                diaHelp.setTitle(R.string.help);
                return diaHelp;

            default:
                return null;

        }
    }

    @Override
    public void onPrepareDialog(final int id, final Dialog dialog) {
        // Called every time before a dialog is shown!
        switch (id) {
            case DIALOG_DISCONNECT_ID:
                // Can't really update AlertDialog text...
                break;

            case DIALOG_INFO_ID:
                dialog.setTitle(R.string.info);
                upText(dialog, R.id.about_version_label, R.string.version_label);
                upText(dialog, R.id.about_developed_label, R.string.developed_label);
                break;

            case DIALOG_HELP_ID:
                dialog.setTitle(R.string.help);
                upText(dialog, R.id.help_settings_label, R.string.settings_label);
                upText(dialog, R.id.help_settings_text1, R.string.settings_text1);
                upText(dialog, R.id.help_settings_text2, R.string.settings_text2);
                upText(dialog, R.id.help_connect_label, R.string.connect_label);
                upText(dialog, R.id.help_connect_text1, R.string.connect_text1);
                upText(dialog, R.id.help_connect_text2, R.string.connect_text2);
                upText(dialog, R.id.help_conSettings_label, R.string.conSettings_label);
                upText(dialog, R.id.help_conSettings_text, R.string.conSettings_text);
                upText(dialog, R.id.help_disconnect_label, R.string.disconnect_label);
                upText(dialog, R.id.help_disconnect_text, R.string.disconnect_text);
                upText(dialog, R.id.hint_text, R.string.hint);
                upText(dialog, R.id.thanks_text, R.string.thanks);
                break;

            default:
                assert false;
        }
    }

    /**
     * Resets text from resource (after language change).
     * @param dialog Dialog holding the views.
     * @param viewID ID of the view.
     * @param stringID ID of the string.
     */
    private void upText(final Dialog dialog, final int viewID, final int stringID) {
        ((TextView) dialog.findViewById(viewID)).setText(stringID);

    }

    /**
     * Resets menu text from resource.
     */
    private void updateMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

    }

    /**
     * Sets currently addressed PC in list.
     * @param id Row of the list
     */
    private void setClickedPC(final long id) {
        int index = (int) id;
        clickedPC = mobileDevice.getConnectees().get(index);

    }

    /**
     * Starts connection setup to waiting PC.
     */
    private void startConSetup() {
        Intent intent = new Intent(this, ConnectView.class);
//        FIXME: Reactivate when implemented
//        intent.putExtra("hashcode", clickedPC.getCertificate().hashCode());
//        FIXEND
        intent.putExtra("rights", clickedPC.getAuthType());
        Log.v(MainView.class.getName(), "Intent: AuthType added");
        intent.putExtra("auto", clickedPC.isPermanent());
        Log.v(MainView.class.getName(), "Intent: check added");
        startActivityForResult(intent, CONNECT_TO_PC);

    }

    /**
     * Establishes connection to waiting PC.
     * @return the upgraded connection
     */
    private ConnectionOnMobile connectTo(final PCDevice toConnect) {
        return mobileDevice.upgradeConnection(toConnect);

    }

    /**
     * Terminates current connection.
     */
    public void terminateConnection() {
        mobileDevice.release();

    }

    /**
     * Refreshes the pc list.
     */
    private void refreshUI() {
        pcAdapter.notifyDataSetChanged();

    }
}
