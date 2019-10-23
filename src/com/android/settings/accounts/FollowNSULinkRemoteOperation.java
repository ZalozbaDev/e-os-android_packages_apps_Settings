package com.android.settings.accounts;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.Cookie;

import android.net.Uri;

import com.owncloud.android.lib.common.OwnCloudClient;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;


public class FollowNSULinkRemoteOperation extends RemoteOperation {
    protected RemoteOperationResult run(OwnCloudClient client) {
        Uri serverUri = (client.getBaseUri() != null)? client.getBaseUri() : client.getWebdavUri();

        Cookie usernameCookie = new Cookie();
        usernameCookie.setName("username");
        usernameCookie.setValue("x@e.email");
        usernameCookie.setDomain(serverUri.getHost());
        usernameCookie.setPath(serverUri.getPath());

        Cookie tokenCookie = new Cookie();
        tokenCookie.setName("token");
        tokenCookie.setValue("x");
        tokenCookie.setDomain(serverUri.getHost());
        tokenCookie.setPath(serverUri.getPath());

        client.getState().addCookie(usernameCookie);
        client.getState().addCookie(tokenCookie);

        String url = client.getBaseUri() + "/apps/increasestoragebutton/get-redirect-link";
        GetMethod get = new GetMethod(url);
        int status = client.executeMethod(get);

        if(!status)
            return new RemoteOperationResult(false, get);

        String redirectLink = get.getResponseBodyAsString();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(redirectLink)));
    }
}
