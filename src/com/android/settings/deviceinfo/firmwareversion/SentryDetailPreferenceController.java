/*
 * Copyright (C) 2023 MURENA SAS
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.android.settings.deviceinfo.firmwareversion;

import android.content.Context;
import android.os.UserHandle;
import android.text.TextUtils;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;


public class SentryDetailPreferenceController extends BasePreferenceController {

    private static final String TAG = "SentryDetailPreferenceController";

    private static final String TELEMETRY_KEY = "e_telemetry";

    public SentryDetailPreferenceController(Context context, String key) {
        super(context, key);
    }

    @Override
    public int getAvailabilityStatus() {
        boolean enable = Settings.System.getInt(mContext.getContentResolver(), TELEMETRY_KEY, 0) == 1;
        return enable ? AVAILABLE : UNSUPPORTED_ON_DEVICE;
    }

    @Override
    public boolean useDynamicSliceSummary() {
        return true;
    }

    @Override
    public boolean isSliceable() {
        return true;
    }

    @Override
    public CharSequence getSummary() {
        String sentryId = Settings.Secure.getStringForUser(
                mContext.getContentResolver(), Settings.Secure.SENTRY_USERID,
                UserHandle.USER_CURRENT);
        if (sentryId == null) {
            return mContext.getString(R.string.unknown);
        }
        return sentryId;
    }
}
