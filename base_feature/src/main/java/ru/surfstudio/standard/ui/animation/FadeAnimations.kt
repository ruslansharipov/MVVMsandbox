package ru.surfstudio.standard.ui.animation

import ru.surfstudio.android.navigation.animation.resource.BaseResourceAnimations
import ru.surfstudio.android.template.base_feature.R

/**
 * Набор анимаций Fade In/Out для переключения фрагментов.
 */
object FadeAnimations : BaseResourceAnimations(
        enterAnimation = R.anim.fade_in_fast,
        exitAnimation = R.anim.fade_out_fast,
        popEnterAnimation = R.anim.fade_in_fast,
        popExitAnimation = R.anim.fade_out_fast
)
