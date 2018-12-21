package com.android.settings;

import android.content.Intent;
import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceGroup;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

public class Contributors extends SettingsPreferenceFragment {

    private static final String LOG_TAG = "Contributors";
    private static final String URL_E_CONTRIBUTORS = "https://e.foundation/about-e/#the-e-team";
    private static final String KEY_E_CONTRIBUTORS = "e_contributors";
    private static final String URL_E_SUPPORTERS = "https://e.foundation/support-us/#halloffame";
    private static final String KEY_E_SUPPORTERS = "e_supporters";

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.contributors);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.CONTRIBUTORS;
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals(KEY_E_CONTRIBUTORS)
          || preference.getKey().equals(KEY_E_SUPPORTERS) ) {

            final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            if (preference.getKey().equals(KEY_E_CONTRIBUTORS)) {
              intent.setData(Uri.parse(URL_E_CONTRIBUTORS));
            } else if (preference.getKey().equals(KEY_E_SUPPORTERS)) {
              intent.setData(Uri.parse(URL_E_SUPPORTERS));
            }

            try {
                startActivity(intent);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Unable to start activity " + intent.toString());
            }
        }
        return super.onPreferenceTreeClick(preference);
    }
}
