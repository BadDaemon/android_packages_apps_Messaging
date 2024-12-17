/*
 * Copyright (C) 2015 The Android Open Source Project
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
package com.android.messaging.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import com.android.messaging.Factory;
import com.android.messaging.R;
import com.android.messaging.util.ImageUtils;

/**
 * A singleton cache that holds tinted drawable resources for displaying messages, such as
 * message bubbles, audio attachments etc.
 */
public class ConversationDrawables {
    private static ConversationDrawables sInstance;

    // Cache the color filtered bubble drawables so that we don't need to create a
    // new one for each ConversationMessageView.
    private Drawable mIncomingBubbleNoArrowDrawable;
    private Drawable mOutgoingBubbleNoArrowDrawable;
    private Drawable mAudioPlayButtonDrawable;
    private Drawable mAudioPauseButtonDrawable;
    private Drawable mIncomingAudioProgressBackgroundDrawable;
    private Drawable mOutgoingAudioProgressBackgroundDrawable;
    private Drawable mAudioProgressForegroundDrawable;
    private Drawable mFastScrollThumbDrawable;
    private Drawable mFastScrollThumbPressedDrawable;
    private Drawable mFastScrollPreviewDrawableLeft;
    private Drawable mFastScrollPreviewDrawableRight;
    private final Context mContext;
    private int mOutgoingBubbleColor;
    private int mIncomingErrorBubbleColor;
    private int mIncomingAudioButtonColor;
    private int mSelectedBubbleColor;
    private int mThemeColor;
    private TypedArray mColors;

    public static ConversationDrawables get() {
        if (sInstance == null) {
            sInstance = new ConversationDrawables(Factory.get().getApplicationContext());
        }
        return sInstance;
    }

    private ConversationDrawables(final Context context) {
        mContext = context;
        // Pre-create all the drawables.
        updateDrawables();
    }

    public int getConversationThemeColor() {
        return mThemeColor;
    }

    public void updateDrawables() {
        final Resources resources = mContext.getResources();
        final Resources.Theme theme = mContext.getTheme();

        mIncomingBubbleNoArrowDrawable = ResourcesCompat.getDrawable(resources,
                R.drawable.message_bubble_incoming_no_arrow, theme);
        mOutgoingBubbleNoArrowDrawable = ResourcesCompat.getDrawable(resources,
                R.drawable.message_bubble_outgoing_no_arrow, theme);
        mAudioPlayButtonDrawable = ResourcesCompat.getDrawable(resources,
                R.drawable.ic_play_light, theme);
        mAudioPauseButtonDrawable = ResourcesCompat.getDrawable(resources,
                R.drawable.ic_pause_light, theme);
        mIncomingAudioProgressBackgroundDrawable = ResourcesCompat.getDrawable(resources,
                R.drawable.audio_progress_bar_background_incoming, theme);
        mOutgoingAudioProgressBackgroundDrawable = ResourcesCompat.getDrawable(resources,
                R.drawable.audio_progress_bar_background_outgoing, theme);
        mAudioProgressForegroundDrawable = ResourcesCompat.getDrawable(resources,
                R.drawable.audio_progress_bar_progress, theme);
        mFastScrollThumbDrawable = ResourcesCompat.getDrawable(resources,
                R.drawable.fastscroll_thumb, theme);
        mFastScrollThumbPressedDrawable = ResourcesCompat.getDrawable(resources,
                R.drawable.fastscroll_thumb_pressed, theme);
        mFastScrollPreviewDrawableLeft = ResourcesCompat.getDrawable(resources,
                R.drawable.fastscroll_preview_left, theme);
        mFastScrollPreviewDrawableRight = ResourcesCompat.getDrawable(resources,
                R.drawable.fastscroll_preview_right, theme);
        mOutgoingBubbleColor = resources.getColor(R.color.message_bubble_color_outgoing, theme);
        mIncomingErrorBubbleColor =
                resources.getColor(R.color.message_error_bubble_color_incoming, theme);
        mIncomingAudioButtonColor =
                resources.getColor(R.color.message_audio_button_color_incoming, theme);
        mSelectedBubbleColor = resources.getColor(R.color.message_bubble_color_selected, theme);
        mThemeColor = resources.getColor(R.color.primary_color, theme);
        mColors = resources.obtainTypedArray(R.array.letter_tile_colors);
    }

    public Drawable getBubbleDrawable(final boolean selected, final boolean incoming,
                                      final boolean isError, final String identifier) {
        final Drawable protoDrawable;
        if (incoming) {
            protoDrawable = mIncomingBubbleNoArrowDrawable;
        } else {
            protoDrawable = mOutgoingBubbleNoArrowDrawable;
        }

        int color;
        if (selected) {
            color = mSelectedBubbleColor;
        } else if (incoming) {
            if (isError) {
                color = mIncomingErrorBubbleColor;
            } else {
                if (identifier != null &&
                        mContext.getResources().getBoolean(R.bool.contact_colors)) {
                    int idcolor = Math.abs(identifier.hashCode()) % mColors.length();
                    color = mColors.getColor(idcolor, mThemeColor);
                } else {
                    color = mThemeColor;
                }
            }
        } else {
            color = mOutgoingBubbleColor;
        }

        return ImageUtils.getTintedDrawable(mContext, protoDrawable, color);
    }

    private int getAudioButtonColor(final boolean incoming) {
        return incoming ? mIncomingAudioButtonColor : mThemeColor;
    }

    public Drawable getPlayButtonDrawable(final boolean incoming) {
        return ImageUtils.getTintedDrawable(
                mContext, mAudioPlayButtonDrawable, getAudioButtonColor(incoming));
    }

    public Drawable getPauseButtonDrawable(final boolean incoming) {
        return ImageUtils.getTintedDrawable(
                mContext, mAudioPauseButtonDrawable, getAudioButtonColor(incoming));
    }

    public Drawable getAudioProgressDrawable(final boolean incoming) {
        return ImageUtils.getTintedDrawable(
                mContext, mAudioProgressForegroundDrawable, getAudioButtonColor(incoming));
    }

    public Drawable getAudioProgressBackgroundDrawable(final boolean incoming) {
        return incoming ? mIncomingAudioProgressBackgroundDrawable :
            mOutgoingAudioProgressBackgroundDrawable;
    }

    public Drawable getFastScrollThumbDrawable(final boolean pressed) {
        if (pressed) {
            return ImageUtils.getTintedDrawable(mContext, mFastScrollThumbPressedDrawable,
                    mThemeColor);
        } else {
            return mFastScrollThumbDrawable;
        }
    }

    public Drawable getFastScrollPreviewDrawable(boolean positionRight) {
        Drawable protoDrawable = positionRight ? mFastScrollPreviewDrawableRight :
            mFastScrollPreviewDrawableLeft;
        return ImageUtils.getTintedDrawable(mContext, protoDrawable, mThemeColor);
    }
}
