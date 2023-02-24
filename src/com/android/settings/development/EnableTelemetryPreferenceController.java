/*
 * Copyright (C) 2022 MURENA SAS
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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

public class EnableTelemetryPreferenceController extends DeveloperOptionsPreferenceController implements
        Preference.OnPreferenceChangeListener, PreferenceControllerMixin {

    private static final String PREFERENCES_ENABLE_TELEMETRY_KEY = "enable_telemetry";
    private static final String TELEMETRY_KEY = "e_telemetry";
    private static final int TELEMETRY_ON = 1;
    private static final int TELEMETRY_OFF = 0;
    private String status;

    public EnableTelemetryPreferenceController(Context context) {
        super(context);
    }

    @Override
    public String getPreferenceKey(){
        return PREFERENCES_ENABLE_TELEMETRY_KEY;
    }

    public void enableTelemetry(Boolean enabled) {
        Settings.System.putInt(mContext.getContentResolver(), TELEMETRY_KEY, enabled ? TELEMETRY_ON : TELEMETRY_OFF);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final boolean isEnabled = (Boolean) newValue;
        ((SwitchPreference) mPreference).setChecked(isEnabled);

        if (!isEnabled) {
	    enableTelemetry(false);
	    return true;
	}

        new AlertDialog.Builder(mContext)
                .setTitle(R.string.telemetry_dialog_title)
                .setMessage(mContext.getResources().getString(R.string.telemetry_dialog_message))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((SwitchPreference) mPreference).setChecked(true);
                        enableTelemetry(true);
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((SwitchPreference) mPreference).setChecked(false);
                        enableTelemetry(false);
                    }
                }).show();
        return true;
    }

    @Override
    public void updateState(Preference preference) {
	if (Settings.System.getInt(mContext.getContentResolver(), TELEMETRY_KEY, 0) == TELEMETRY_ON) {
            ((SwitchPreference) mPreference).setChecked(true);
	} else {
            ((SwitchPreference) mPreference).setChecked(false);
	}
    }

    @Override
    protected void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        ((SwitchPreference) mPreference).setChecked(false);
        enableTelemetry(false);
    }

    @Override
    protected void onDeveloperOptionsSwitchEnabled() {
        super.onDeveloperOptionsSwitchEnabled();
    }
}
