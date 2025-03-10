/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.horologist.compose.material

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.VolumeDown
import androidx.compose.ui.unit.LayoutDirection
import com.google.accompanist.testharness.TestHarness
import com.google.android.horologist.screenshots.ScreenshotBaseTest
import org.junit.Test

class IconTest : ScreenshotBaseTest() {

    @Test
    fun default() {
        screenshotTestRule.setContent(isComponent = true, takeScreenshot = true) {
            Icon(
                imageVector = Icons.Outlined.VolumeDown,
                contentDescription = "contentDescription"
            )
        }
    }

    @Test
    fun defaultRtl() {
        screenshotTestRule.setContent(isComponent = true, takeScreenshot = true) {
            TestHarness(layoutDirection = LayoutDirection.Rtl) {
                Icon(
                    imageVector = Icons.Outlined.VolumeDown,
                    contentDescription = "contentDescription"
                )
            }
        }
    }

    @Test
    fun mirrored() {
        screenshotTestRule.setContent(isComponent = true, takeScreenshot = true) {
            Icon(
                imageVector = Icons.Outlined.VolumeDown,
                contentDescription = "contentDescription",
                rtlMode = IconRtlMode.Mirrored
            )
        }
    }

    @Test
    fun mirroredRtl() {
        screenshotTestRule.setContent(isComponent = true, takeScreenshot = true) {
            TestHarness(layoutDirection = LayoutDirection.Rtl) {
                Icon(
                    imageVector = Icons.Outlined.VolumeDown,
                    contentDescription = "contentDescription",
                    rtlMode = IconRtlMode.Mirrored
                )
            }
        }
    }

    @Test
    fun usingDrawableResAsIcon() {
        screenshotTestRule.setContent(isComponent = true, takeScreenshot = true) {
            Icon(
                id = android.R.drawable.ic_media_play,
                contentDescription = "contentDescription"
            )
        }
    }

    @Test
    fun usingDrawableResAsIconRtl() {
        screenshotTestRule.setContent(isComponent = true, takeScreenshot = true) {
            TestHarness(layoutDirection = LayoutDirection.Rtl) {
                Icon(
                    id = android.R.drawable.ic_media_play,
                    contentDescription = "contentDescription"
                )
            }
        }
    }

    @Test
    fun usingDrawableResAsIconMirrored() {
        screenshotTestRule.setContent(isComponent = true, takeScreenshot = true) {
            Icon(
                id = android.R.drawable.ic_media_play,
                contentDescription = "contentDescription",
                rtlMode = IconRtlMode.Mirrored
            )
        }
    }

    @Test
    fun usingDrawableResAsIconMirroredRtl() {
        screenshotTestRule.setContent(isComponent = true, takeScreenshot = true) {
            TestHarness(layoutDirection = LayoutDirection.Rtl) {
                Icon(
                    id = android.R.drawable.ic_media_play,
                    contentDescription = "contentDescription",
                    rtlMode = IconRtlMode.Mirrored
                )
            }
        }
    }
}
