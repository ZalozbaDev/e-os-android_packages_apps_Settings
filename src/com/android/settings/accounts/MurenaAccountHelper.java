/*
 * Copyright MURENA SAS 2022
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

public class MurenaAccountHelper {

    public static final String MURENA_ACCOUNT_TYPE = "e.foundation.webdav.eelo";
    public static final String MURENA_ADDRESS_BOOK_ACCOUNT_TYPE = "foundation.e.accountmanager.eelo.address_book";

    public static boolean isMurenaAccount(String accountType) {
        return accountType.equals(MURENA_ACCOUNT_TYPE) || accountType.equals(MURENA_ADDRESS_BOOK_ACCOUNT_TYPE);
    }
}
