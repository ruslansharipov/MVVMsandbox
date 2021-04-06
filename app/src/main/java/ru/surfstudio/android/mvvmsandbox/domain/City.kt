package ru.surfstudio.android.mvvmsandbox.domain

import java.io.Serializable

data class City(
        val code: String = "",
        val cityName: String = "",
        val fiasId: String = "",
        val kladrId: String = ""
) : Serializable