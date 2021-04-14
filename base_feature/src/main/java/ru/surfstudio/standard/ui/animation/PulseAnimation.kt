package ru.surfstudio.standard.ui.animation

import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

/**
 * Анимацию пульсирующего изменения прозрачности
 */
class PulseAnimation : AlphaAnimation(ALPHA_TRANSPARENT, ALPHA_OPAQUE) {
    init {
        interpolator = AccelerateDecelerateInterpolator()
        duration = ANIMATION_DURATION
        repeatCount = Animation.INFINITE
        repeatMode = Animation.REVERSE
    }

    companion object {
        const val ANIMATION_DURATION = 250L
        const val ALPHA_OPAQUE = 1f
        const val ALPHA_TRANSPARENT = 0f
    }
}
