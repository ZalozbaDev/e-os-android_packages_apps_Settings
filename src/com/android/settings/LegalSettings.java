/*
 * Copyright (C) 2015 The Android Open Source Project
 * Copyright (C) 2019-2021 E FOUNDATION
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

package com.android.settings;

import android.app.settings.SettingsEnums;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemProperties;
import android.util.Log;
import androidx.preference.Preference;

import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

@SearchIndexable
public class LegalSettings extends DashboardFragment {

    private static final String TAG = "LegalSettings";

    private static final String PROPERTY_LINEAGELICENSE_URL = "ro.lineagelegal.url";
    private static final String KEY_LINEAGE_LICENSE = "lineagelicense";
    private static final String PROPERTY_ELICENSE_URL = "ro.elegal.url";
    private static final String KEY_E_LICENSE = "elicense";

    @Override
    public int getMetricsCategory() {
        return SettingsEnums.ABOUT_LEGAL_SETTINGS;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
		String userLicenseUrl = "";
		if (preference.getKey().equals(KEY_E_LICENSE)) {
			userLicenseUrl = SystemProperties.get(PROPERTY_ELICENSE_URL);
		} else if (preference.getKey().equals(KEY_LINEAGE_LICENSE)) {
			userLicenseUrl = SystemProperties.get(PROPERTY_LINEAGELICENSE_URL);
		}

		if (userLicenseUrl != null && userLicenseUrl.length() > 0) {
			final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse(userLicenseUrl));
            try {
                startActivity(intent);
            } catch (Exception e) {
                Log.e(TAG, "Unable to start activity " + intent.toString());
            }
		}
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.about_legal;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.about_legal);
}
