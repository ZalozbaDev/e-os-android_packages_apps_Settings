package com.android.settings;

import android.content.Intent;
import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceGroup;
import com.android.internal.logging.MetricsProto.MetricsEvent;

public class Contributors extends SettingsPreferenceFragment {

    private static final String LOG_TAG = "Contributors";
    private static final String URL_E_CONTRIBUTORS = "https://e.foundation/support-us/#halloffame";
    private static final String KEY_E_CONTRIBUTORS = "e_contributors";

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.contributors);
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.CONTRIBUTORS;
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals(KEY_E_CONTRIBUTORS)) {
            final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse(URL_E_CONTRIBUTORS));
            try {
                startActivity(intent);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Unable to start activity " + intent.toString());
            }
        }
        return super.onPreferenceTreeClick(preference);
    }
}
