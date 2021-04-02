/*
 * Copyright (C) 2021 ECORP SAS
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.android.settings.development;

import android.content.Context;
import android.provider.Settings;
import android.support.annotation.VisibleForTesting;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.content.Context;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

public class ChangeSourcePreferenceController extends DeveloperOptionsPreferenceController implements
        Preference.OnPreferenceChangeListener, PreferenceControllerMixin {

    SharedPreferences preferences;
    private static final String CHANGE_URL_KEY = "change_update_source";
    private final String UPDATE_URI ="content://custom.setting.Provider.OTA_SERVER/cte";
    private String status;

    @VisibleForTesting
    static final int SETTING_VALUE_ON = 1;
    @VisibleForTesting
    static final int SETTING_VALUE_OFF = 0;

    public ChangeSourcePreferenceController(Context context) {
        super(context);
    }

    @Override
    public String getPreferenceKey() {
        return CHANGE_URL_KEY;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final boolean isEnabled = (Boolean) newValue;
        ((SwitchPreference) mPreference).setChecked(isEnabled);
        if (count(OTAProvider.CONTENT_URI)) {
            ContentValues values = new ContentValues();

            if (retrieveStatus().equals("true")) {
                values.put(OTAProvider.Status, "false");
                mContext.getContentResolver().update(OTAProvider.CONTENT_URI, values, OTAProvider.id + "=?", new String[]{"1"});
            }else{
                values.put(OTAProvider.Status, "true");
                mContext.getContentResolver().update(OTAProvider.CONTENT_URI, values, OTAProvider.id + "=?", new String[]{"1"});
            }
        } else {
            ContentValues values = new ContentValues();
            values.put(OTAProvider.Status,"true");
            mContext.getContentResolver().insert(OTAProvider.CONTENT_URI, values);
        }
        return true;
    }

    @Override
    public void updateState(Preference preference) {
        if (retrieveStatus().equals("true")){
            ((SwitchPreference) mPreference).setChecked(true);
        }else{
            ((SwitchPreference) mPreference).setChecked(false);
        }
    }

    @Override
    protected void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        ((SwitchPreference) mPreference).setChecked(false);
    }

    @Override
    protected void onDeveloperOptionsSwitchEnabled() {
        super.onDeveloperOptionsSwitchEnabled();
        if (!count(OTAProvider.CONTENT_URI)){
            ContentValues values = new ContentValues();
            values.put(OTAProvider.Status,"false");
            mContext.getContentResolver().insert(OTAProvider.CONTENT_URI, values);
        }

    }

    public boolean count(Uri uri) {
        Cursor cursor = mContext.getContentResolver().query(uri, new String[]{"id"},
                null, null, null);
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    private String retrieveStatus(){
        Cursor cursor = mContext.getContentResolver().query(Uri.parse(UPDATE_URI), null, OTAProvider.id+"=?", new String[]{"1"}, OTAProvider.Status);
        if (cursor.moveToFirst()) {
            do {
                status = cursor.getString(cursor.getColumnIndex(OTAProvider.Status));
            } while (cursor.moveToNext());
        }
        return status;
    }
}
