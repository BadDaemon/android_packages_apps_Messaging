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

package android.support.v7.mms;

/**
 * APN exception
 */
public class ApnException extends Exception {

    public ApnException() {
        super();
    }

    public ApnException(String message) {
        super(message);
    }

    public ApnException(Throwable cause) {
        super(cause);
    }

    public ApnException(String message, Throwable cause) {
        super(message, cause);
    }
}
