/*
 * Copyright (C) 2019 e.foundation
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

package com.android.settings.dns;

import android.content.Context;

import android.os.UserHandle;

//import android.content.pm.PackageManager;

import android.provider.Settings;

import com.android.settings.SettingsPreferenceFragment;
import android.view.View.OnClickListener;
import android.content.DialogInterface;
import com.android.settings.search.Indexable;
import com.android.internal.logging.MetricsProto.MetricsEvent;


import com.android.settings.widget.ToggleSwitch;

import com.android.settings.R;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;

import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.TwoStatePreference;

import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.EditTextPreference;

import android.util.Log;


import android.net.NetworkAgent;
import android.net.NetworkInfo;

import android.net.ConnectivityManager;


import android.net.NetworkUtils;

import java.net.Inet4Address;

import android.os.UserManager;

import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import java.lang.reflect.Method;

import com.android.settings.RestrictedSettingsFragment;

import static android.provider.Settings.System.VIBRATE_WHEN_RINGING;

public class DNSSettings extends RestrictedSettingsFragment
        implements OnPreferenceChangeListener /*Preference.OnPreferenceClickListener*/ {

    private static final String TAG = "DNSSettings";

    private static final String KEY_E_TOGGLE_DNS = "toggle_e_dns";
    private final static String KEY_E_DNS_VALUE = "e_dns_value";

    private SwitchPreference useNetworkDNS;
    private EditTextPreference	overrideDNSIPV4;

    private static final String USE_NETWORK_DNS = "USE_NETWORK_DNS";
    private static final String OVERRIDE_DNS_IP_V4 = "OVERRIDE_DNS_IP_V4"; // IPV4 DNS TO USE.

    private String dnsNotSet; // Default value

	public DNSSettings() {
		super(UserManager.DISALLOW_CONFIG_VPN);
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.VPN;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

		//pm = getActivity().getPackageManager();

		dnsNotSet = getResources().getString(R.string.e_dns_not_set);

		addPreferencesFromResource(R.xml.dns_settings);
		useNetworkDNS = (SwitchPreference) findPreference(KEY_E_TOGGLE_DNS);

		overrideDNSIPV4 = (EditTextPreference) findPreference(KEY_E_DNS_VALUE);

		for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
			getPreferenceScreen().getPreference(i).setOnPreferenceChangeListener(this);
		}

		int useNwDNS = Settings.System.getInt(getContext().getContentResolver(), USE_NETWORK_DNS, 1);
		((TwoStatePreference) useNetworkDNS).setChecked(useNwDNS == 1);
		
		String s = Settings.System.getString(getContext().getContentResolver(), OVERRIDE_DNS_IP_V4);
		overrideDNSIPV4.setText(checkNull(s));
    }

    private final String checkNull(String value) {
        if (value == null || value.length() == 0) {
            return dnsNotSet;
        } else {
            return value;
        }
    }

    private final Inet4Address getIPv4Address(String text) {
        try {
            return (Inet4Address) NetworkUtils.numericToInetAddress(text);
        } catch (IllegalArgumentException | ClassCastException e) {
            return null;
        }
    }

/*
	@Override
    public boolean onPreferenceClick(Preference preference) {
		return true;
	}
*/

	@Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

		final String key = preference.getKey();

		if (KEY_E_TOGGLE_DNS.equals(key)) {
			final boolean val = (Boolean) newValue;
		    boolean result = Settings.System.putInt(getContext().getContentResolver(), USE_NETWORK_DNS, val ? 1 : 0);
			resartNetworks();
			return result;

		}
		if (KEY_E_DNS_VALUE.equals(key)) {
			final String val = (String) newValue;
			final Inet4Address ipAddress = getIPv4Address(val);
			if (ipAddress != null) {
				overrideDNSIPV4.setText(checkNull(val));
			    boolean result = Settings.System.putString(getContext().getContentResolver(), OVERRIDE_DNS_IP_V4, val);
				resartNetworks();
				return result;
			}
			overrideDNSIPV4.setText(checkNull(dnsNotSet));
		}

		//new NetworkInfo(ConnectivityManager.TYPE_BLUETOOTH, 0, NETWORK_TYPE, "");
		return false;
    }

	private final void resartNetworks(){

		ConnectivityManager connectivitymanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
 
		NetworkInfo[] networkInfo = connectivitymanager.getAllNetworkInfo();
		 
		for (NetworkInfo netInfo : networkInfo) {
		 
			if (netInfo.getTypeName().equalsIgnoreCase("WIFI")) {
			 
				if (netInfo.isConnected()){
					WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
					//boolean wifiEnabled = wifiManager.isWifiEnabled();
					//Log.v(TAG, "Wi-Fi state>"+wifiEnabled);
					//if (wifiEnabled) {		
						wifiManager.setWifiEnabled(false);
						wifiManager.setWifiEnabled(true);
					//}
				} else {
				
				}
					

			}
			 
			if (netInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
			 
				if (netInfo.isConnected()){
					Intent intent = new Intent();
					intent.setComponent(new android.content.ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
					startActivity(intent);
				} else {

				}
				 
			
			}
		}

	}

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
