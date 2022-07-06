/*
 * Copyright ECORP SAS 2022
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
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.android.settings.accounts;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class MailAccountSyncHelper {

    private static final String MAIL_PACKAGE = "foundation.e.mail";
    private static final String MAIL_RECEIVER_CLASS = "com.fsck.k9.account.AccountSyncReceiver";
    private static final String ACTION_PREFIX = "foundation.e.accountmanager.account.";

    public static void accountLoggedIn(Context applicationContext) {
        if (applicationContext == null) {
            return;
        }
        Intent intent = getIntent();
        intent.setAction(ACTION_PREFIX + "create");
        applicationContext.sendBroadcast(intent);
    }

    public static void accountLoggedOut(Context applicationContext, String email) {
        if (applicationContext == null || email == null || !email.contains("@")) {
            return;
        }
        Intent intent = getIntent();
        intent.setAction(ACTION_PREFIX + "remove");
        intent.putExtra("account", email);
        applicationContext.sendBroadcast(intent);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private static Intent getIntent() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setComponent(new ComponentName(MAIL_PACKAGE, MAIL_RECEIVER_CLASS));
        return intent;
    }


    private MailAccountSyncHelper() {
    }
}
