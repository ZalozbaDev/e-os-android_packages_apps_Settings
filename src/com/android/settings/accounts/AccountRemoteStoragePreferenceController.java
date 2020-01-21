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
import com.android.settings.R;
import com.android.settings.accounts.FollowNSULinkRemoteOperation;

import com.owncloud.android.lib.common.OwnCloudClient;
import com.owncloud.android.lib.common.OwnCloudClientFactory;
import com.owncloud.android.lib.common.OwnCloudCredentialsFactory;

public class AccountRemoteStoragePreferenceController 
        extends AbstractPreferenceController
        implements OnClickListener {
    private static final String KEY_ACCOUNT_REMOTE_STORAGE = "account_remote_storage";

    private OwnCloudClient mClient;
    private Handler mhandler;
    private Account mAccount;
    private UserHandle mUserHandle;

    public AccountRemoteStoragePreferenceController(Context context) {
        super(context);
    }


    @Override
    public boolean isAvailable() {
        return this.mAccount.type.equals("e.foundation.webdav.eelo");
    }

    @Override
    public String getPreferenceKey() {
        return KEY_ACCOUNT_REMOTE_STORAGE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void init(Account account, UserHandle userHandle) {
        this.mAccount = account;
        this.mUserHandle = userHandle;

        setContentView(R.layout.remove_account_button);

        Uri serverUri = Uri.parse(getString(R.string.e_ecloud_server_uri));

        this.mHandler = new Handler();
        this.mClient = OwnCloudClientFactory.createOwnCloudClient(serverUri, this, true);

        Credentials cred = this.mAccount.getState().getCredentials(AuthScope.ANY);

        this.mClient.setCredentials(
      			OwnCloudCredentialsFactory.newBasicCredentials(
      					cred.getUserName(),
      					cred.getPassword()
  				  )
  		  );
    }

    @Override
    public void onClick(View button) {
        if (button.getId() == R.id.increase_storage_button)
            followNSULink();
    }

    private void followNSULink() {
        FollowNSULinkRemoteOperation followNSULinkOp = new FollowNSULinkRemoteOperation();
        followNSULinkOp.execute(mClient, this, mHandler);
    }
}
