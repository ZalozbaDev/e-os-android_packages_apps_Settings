package com.android.settings.accounts;

import android.accounts.Account;

class AccountAddressBookHelper {

    /*
     By convention all address book account types has "address_book" postfix
     e.g. "foundation.e.accountmanager.address_book"
     */
    public static final String ADDRESS_BOOK_ACCOUNT_TYPE = "address_book";

    public static boolean isAccountAddressBookType(String account_type) {
        if (account_type != null) {
            return account_type.contains(ADDRESS_BOOK_ACCOUNT_TYPE);
        } else {
            return false;
        }
    }

    /**
     * Converting account name get from nextcloud accoring to our needs
     */
    public static String convertAccountName(Account account) {
        if (account != null && account.type != null) {
            if (!isAccountAddressBookType(account.type)) {
                // ecloud account
                // TODO - should be constructed out of resources
                return "Settings (" + account.name + ")";
            } else {
                // TODO change address_book account type name
                return account.name
            }
        } else {
            return "";
        }
    }
}
