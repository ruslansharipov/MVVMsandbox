package ru.surfstudio.standard.ui.animation

import ru.surfstudio.android.navigation.animation.resource.BaseResourceAnimations
import ru.surfstudio.android.template.base_feature.R

/**
 * Анимации переключения экранов.
 * Открытие экрана происходит с анимацией fade-in, закрытие - с модальной анимацией.
 */
object FadeInModalOutAnimations : BaseResourceAnimations(
        enterAnimation = R.anim.fade_in_fast,
        exitAnimation = R.anim.fade_out_fast,
        popEnterAnimation = R.anim.fade_in_fast,
        popExitAnimation = R.anim.slide_out_to_bottom
)
