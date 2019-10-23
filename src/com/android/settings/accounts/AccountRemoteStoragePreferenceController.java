package com.android.settings.accounts;

import android.os.Bundle;
import android.os.Handler;
import android.net.Uri;
import android.view.View;

import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settings.accounts.FollowNSULinkRemoteOperation;

import com.owncloud.android.lib.common.OwnCloudClient;
import com.owncloud.android.lib.common.OwnCloudClientFactory;
import com.owncloud.android.lib.common.OwnCloudCredentialsFactory;

public class AccountRemoteStoragePreferenceController extends AbstractPreferenceController {
    private OwnCloudClient mClient;
    private Handler mhandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_account_button);

        Uri serverUri = Uri.parse(getString(R.string.e_ecloud_server_uri));

        mHandler = new Handler();
        mClient = OwnCloudClientFactory.createOwnCloudClient(serverUri, this, true);
        mClient.setCredentials(
      			OwnCloudCredentialsFactory.newBasicCredentials(
      					"",
      					""
  				  )
  		  );
    }

    public void onClickHandler(View button) {
        if (button.getId() == R.id.increase_storage_button)
            followNSULink();
    }

    private void followNSULink() {
      FollowNSULinkRemoteOperation followNSULinkOp = new FollowNSULinkRemoteOperation();
      followNSULinkOp.execute(mClient, this, mHandler);
    }
}
