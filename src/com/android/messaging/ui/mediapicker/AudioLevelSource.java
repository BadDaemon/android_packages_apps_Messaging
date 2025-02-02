/*
 * Copyright (C) 2015 The Android Open Source Project
 * Copyright (C) 2024 The LineageOS Project
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
 */
package com.android.messaging.ui.mediapicker;

import com.google.common.base.Preconditions;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Keeps track of the speech level as last observed by the recognition
 * engine as microphone data flows through it. Can be polled by the UI to
 * animate its views.
 */
@ThreadSafe
public class AudioLevelSource {
    private volatile int mSpeechLevel;

    public static final int LEVEL_UNKNOWN = -1;

    public void setSpeechLevel(int speechLevel) {
        Preconditions.checkArgument(speechLevel >= 0 && speechLevel <= 100 ||
                speechLevel == LEVEL_UNKNOWN);
        mSpeechLevel = speechLevel;
    }

    public int getSpeechLevel() {
        return mSpeechLevel;
    }
}
