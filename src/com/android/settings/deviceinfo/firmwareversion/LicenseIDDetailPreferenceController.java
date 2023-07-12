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
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;


public class LicenseIDDetailPreferenceController extends BasePreferenceController {

    private static final String TAG = "LicenseIDDetailPreferenceController";

    public LicenseIDDetailPreferenceController(Context context, String key) {
        super(context, key);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
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
        String eLicenseID = Settings.Secure.getStringForUser(
                mContext.getContentResolver(), Settings.Secure.E_LICENSE_ID,
                UserHandle.USER_CURRENT);
        if (eLicenseID == null) {
            return mContext.getString(R.string.unknown);
        }
        return eLicenseID;
    }
}
