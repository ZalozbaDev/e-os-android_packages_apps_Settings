package com.android.settings.accounts;

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
    }

    private void getRedirectLink() {

    }
}
