/*
 * Copyright (C) 2016 The Android Open Source Project
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
 *
 */

package com.android.settings.search;

import android.content.Intent;
import android.os.Parcel;
import com.android.settings.testutils.SettingsRobolectricTestRunner;
import com.android.settings.TestConfig;
import com.android.settings.search2.IntentPayload;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

@RunWith(SettingsRobolectricTestRunner.class)
@Config(manifest = TestConfig.MANIFEST_PATH, sdk = TestConfig.SDK_VERSION)
public class IntentPayloadTest {
    private IntentPayload mPayload;

    private final String EXTRA_KEY = "key";
    private final String EXTRA_VALUE = "value";

    @Test
    public void testParcelOrdering_StaysValid() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY, EXTRA_VALUE);
        Parcel parcel = Parcel.obtain();

        mPayload = new IntentPayload(intent);
        mPayload.writeToParcel(parcel, 0);
        // Reset parcel for reading
        parcel.setDataPosition(0);
        IntentPayload newPayload = IntentPayload.CREATOR.createFromParcel(parcel);

        String originalIntentExtra = mPayload.intent.getStringExtra(EXTRA_KEY);
        String copiedIntentExtra = newPayload.intent.getStringExtra(EXTRA_KEY);
        assertThat(originalIntentExtra).isEqualTo(copiedIntentExtra);
    }
}
