package ru.surfstudio.standard.ui.animation

import ru.surfstudio.android.navigation.animation.resource.BaseResourceAnimations
import ru.surfstudio.android.template.base_feature.R

/**
 * Набор анимаций для модального появления/скрытия экрана
 */
object ModalAnimations : BaseResourceAnimations(
        enterAnimation = R.anim.slide_in_from_bottom,
        exitAnimation = R.anim.fade_out_fast,
        popEnterAnimation = R.anim.fade_in_fast,
        popExitAnimation = R.anim.slide_out_to_bottom
)
