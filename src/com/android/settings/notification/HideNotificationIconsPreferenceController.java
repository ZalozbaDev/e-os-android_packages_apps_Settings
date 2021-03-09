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

import android.content.Context;
import android.provider.Settings;

import androidx.annotation.VisibleForTesting;

import com.android.settings.core.TogglePreferenceController;

import static android.provider.Settings.Secure.SHOW_NOTIFICATION_SNOOZE;

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
                SHOW_NOTIFICATION_SNOOZE, OFF) == ON;
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        return Settings.Secure.putInt(mContext.getContentResolver(),
                SHOW_NOTIFICATION_SNOOZE, isChecked ? ON : OFF);
    }
}
