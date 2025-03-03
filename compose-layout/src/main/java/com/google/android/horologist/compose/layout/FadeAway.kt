/*
 * Copyright 2022 The Android Open Source Project
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

package com.google.android.horologist.compose.layout

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyListState

/**
 * Scroll Away the item based on a regular scrolling item, like a Column.
 * Does not include fading or scaling.
 */
@Deprecated(
    "Replaced by scrollAway",
    replaceWith = ReplaceWith(
        "this.scrollAway(scrollStateFn())",
        "com.google.android.horologist.compose.layout.scrollAway"
    )
)
public fun Modifier.fadeAway(scrollStateFn: () -> ScrollState): Modifier = composed {
    val scrollState = scrollStateFn()
    val y = scrollState.value / LocalDensity.current.density

    fadeEffect(y, fade = false)
}

/**
 * Scroll Away the item based on a lazy list, like a LazyColumn. Does not include fading or scaling.
 *
 * The logic assumes the first item is large enough to fully fade away the item, if this is not the
 * case then a custom implementation should be used.
 */
@Deprecated(
    "Replaced by scrollAway",
    replaceWith = ReplaceWith(
        "this.scrollAway(scrollStateFn())",
        "com.google.android.horologist.compose.layout.scrollAway"
    )
)
public fun Modifier.fadeAwayLazyList(scrollStateFn: () -> LazyListState): Modifier = composed {
    val scrollState = remember { scrollStateFn() }
    val isFirst by remember(scrollState) { derivedStateOf { scrollState.firstVisibleItemIndex == 0 } }
    if (isFirst) {
        val density = LocalDensity.current.density
        val y by remember(scrollState) { derivedStateOf { scrollState.firstVisibleItemScrollOffset / density } }

        fadeEffect(y, fade = false)
    } else {
        alpha(0.0f)
    }
}

/**
 * Scroll Away the item based on a ScalingLazyColumn. The item is scaled
 * and faded roughly in line with the default scaling params of ScalingLazyColumn,
 * if this is not the case, a custom implementation should be used.
 *
 * The logic assumes the first item is large enough to fully fade away the item, if this is not the
 * case then a custom implementation should be used.
 *
 * @param initialIndex The initial index must match that provided to [ScalingLazyListState].
 * @param initialOffset The initial offset must match that provided to [ScalingLazyListState].
 */
@Deprecated(
    "Replaced by scrollAway",
    replaceWith = ReplaceWith(
        "this.scrollAway(scrollStateFn(), initialIndex, initialOffset.dp)",
        "com.google.android.horologist.compose.layout.scrollAway",
        "androidx.compose.ui.unit.dp"
    )
)
public fun Modifier.fadeAwayScalingLazyList(
    initialIndex: Int = 1,
    initialOffset: Int = 0,
    scrollStateFn: () -> ScalingLazyListState
): Modifier =
    composed {
        val scrollState = remember { scrollStateFn() }

        // General improvement, but specifically a workaround
        // for https://github.com/google/horologist/issues/243 in Compose rc01
        val isInitial by remember(scrollState) {
            derivedStateOf { scrollState.centerItemIndex == initialIndex }
        }
        val centerItemScrollOffset by remember(scrollState) {
            derivedStateOf { scrollState.centerItemScrollOffset }
        }

        if (isInitial && centerItemScrollOffset > initialOffset) {
            val y = (centerItemScrollOffset - initialOffset) / LocalDensity.current.density

            fadeEffect(y, fade = true)
        } else if (scrollState.centerItemIndex > initialIndex) {
            alpha(0.0f)
        } else {
            this
        }
    }

private fun Modifier.fadeEffect(y: Float, fade: Boolean) = composed {
    if (fade) {
        val fadePercent: Float = (y / maxFadeOutScroll).coerceIn(0f, 1f)
        val fadeOut: Float = lerp(minFadeOut, maxFadeOut, fadePercent)

        val height = LocalConfiguration.current.screenHeightDp
        val translationY = (-y).coerceAtMost(0f) - ((height - height * fadeOut) / 2)

        this
            .offset(y = translationY.dp)
            .scale(fadeOut)
            .alpha(fadeOut)
    } else {
        this
            .offset(y = -y.dp)
    }
}

internal fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}

internal const val maxFadeOutScroll = 40f
internal const val minFadeOut = 1f
internal const val maxFadeOut = 0.5f
