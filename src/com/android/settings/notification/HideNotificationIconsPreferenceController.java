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
//PinScramblePreferenceController

public class HideNotificationIconsPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {

    static final String HIDE_NOTIFICATIONICON_LEFT_SYSTEM_ICON = "hide_notificationIcon_left_system_icon";

    public HideNotificationIconsPreferenceController(Context context) {
        super(context);
    }


    @Override
    public boolean isAvailable() {
        Log.e("Setting....", "Setting ...isAvailable ");
       // LineageSettings.System.HIDE_NOTIFICATIONICON_LEFT_SYSTEM_ICON;
        return true;
    }

    @Override
    public String getPreferenceKey() {
        Log.e("Setting....", "Setting ...getPreferenceKey ");
        return HIDE_NOTIFICATIONICON_LEFT_SYSTEM_ICON;
    }

    @Override
    public void updateState(Preference preference) {
        Log.e("Setting....", "Setting ...updateState ");
        ((TwoStatePreference) preference).setChecked(LineageSettings.System.getInt(
                mContext.getContentResolver(),
                LineageSettings.System.HIDE_NOTIFICATIONICON_LEFT_SYSTEM_ICON,
                0) == 1);


    }



    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        Log.e("Setting....", "Setting ...onPreferenceChange ");
        LineageSettings.System.putInt(
                mContext.getContentResolver(),
                LineageSettings.System.HIDE_NOTIFICATIONICON_LEFT_SYSTEM_ICON,
                (Boolean) newValue ? 1 : 0);
        return true;
    }



}
