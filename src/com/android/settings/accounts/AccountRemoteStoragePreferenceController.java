package com.android.settings.accounts;

import android.accounts.Account;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.net.Uri;
import android.view.View;

import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.Credentials;

import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settings.accounts.FollowNSULinkRemoteOperation;

import com.owncloud.android.lib.common.OwnCloudClient;
import com.owncloud.android.lib.common.OwnCloudClientFactory;
import com.owncloud.android.lib.common.OwnCloudCredentialsFactory;

public class AccountRemoteStoragePreferenceController extends AbstractPreferenceController {
    private OwnCloudClient mClient;
    private Handler mhandler;
    private Account mAccount;
    private UserHandle mUserHandle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_account_button);

        Uri serverUri = Uri.parse(getString(R.string.e_ecloud_server_uri));

        mHandler = new Handler();
        mClient = OwnCloudClientFactory.createOwnCloudClient(serverUri, this, true);

        Credentials cred = mAccount.getState().getCredentials(AuthScope.ANY);

        mClient.setCredentials(
      			OwnCloudCredentialsFactory.newBasicCredentials(
      					cred.getUserName(),
      					cred.getPassword()
  				  )
  		  );
    }

    public void init(Account account, UserHandle userHandle) {
        mAccount = account;
        mUserHandle = userHandle;
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
