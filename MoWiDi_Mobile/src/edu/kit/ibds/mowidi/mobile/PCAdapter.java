/*
 * @(#)PCAdapter.java
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
 * 2010-07-09   outsourced from MainView where was nested PK
 */
package edu.kit.ibds.mowidi.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.kit.ibds.mowidi.mobile.data.PCDevice;
import java.util.List;

/**
 * A custom ListAdapter which manages the ListView.
 * @author André Wengert
 */
class PCAdapter extends ArrayAdapter<PCDevice> {

    /** List of PCs. */
    private final List<PCDevice> pcds;
    /** Context. */
    private final Context context;

    /**
     * Constructor.
     * @param context the context.
     * @param textViewResourceId the view resource ID
     * @param pcds the list
     */
    public PCAdapter(final Context context, final int textViewResourceId, final List<PCDevice> pcds) {
        super(context, textViewResourceId, pcds);
        this.context = context;
        this.pcds = pcds;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.main_item, null);

        }
        if (position >= 0 && position < pcds.size()) {
            PCDevice pcd = pcds.get(position);
            if (pcd != null) {
                TextView pcName = (TextView) v.findViewById(R.id.main_pcName);
                TextView pcState = (TextView) v.findViewById(R.id.main_pcState);
                if (pcName != null) {
                    String setName = pcd.getGivenName();
                    if (setName != null) {
                        pcName.setText(setName);

                    } else {
                        setName = pcd.getuID().toString();

                    }
                    pcName.setText(setName);

                }
                if (pcState != null) {
                    if (pcd.isConnected()) {
                        pcState.setText(R.string.connected);

                    } else {
                        pcState.setText(R.string.waiting);

                    }
                }
            }
        }
        return v;
    }
}
