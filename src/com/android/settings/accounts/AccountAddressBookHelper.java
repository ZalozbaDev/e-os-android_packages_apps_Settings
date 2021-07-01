package com.android.settings.accounts;

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
}
