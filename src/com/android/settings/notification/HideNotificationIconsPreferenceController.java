/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.notification;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;
import lineageos.providers.LineageSettings;
import android.util.Log;


import android.content.Context;
import android.provider.Settings;

import com.android.settings.core.TogglePreferenceController;

import androidx.annotation.VisibleForTesting;

//PinScramblePreferenceController

public class HideNotificationIconsPreferenceController extends TogglePreferenceController {

    private static final String TAG = "HideNotificationIconsPreferenceController";
    @VisibleForTesting
    static final int ON = 1;
    @VisibleForTesting
    static final int OFF = 0;

    public HideNotificationIconsPreferenceController(Context context, String preferenceKey) {

        super(context, preferenceKey);

    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public boolean isChecked() {
        return Settings.Secure.getInt(mContext.getContentResolver(),
                LineageSettings.System.HIDE_NOTIFICATIONICON_LEFT_SYSTEM_ICON, OFF) == ON;
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        Log.e("Setting....", "Setting ...setChecked.................. "+isChecked);
        Log.e("Setting....", "Setting ...value .................. "+LineageSettings.System.HIDE_NOTIFICATIONICON_LEFT_SYSTEM_ICON);
        boolean out=Settings.Secure.putInt(mContext.getContentResolver(),
                LineageSettings.System.HIDE_NOTIFICATIONICON_LEFT_SYSTEM_ICON, isChecked ? ON : OFF);

        Log.e("Setting....", "Setting ...setChecked.........out......... "+out);

        Log.e("Setting....", "Setting ...setChecked.........HIDE_NOTIFICATIONICON_LEFT_SYSTEM_ICON......... "+LineageSettings.System.HIDE_NOTIFICATIONICON_LEFT_SYSTEM_ICON);
        return out;
    }

}
