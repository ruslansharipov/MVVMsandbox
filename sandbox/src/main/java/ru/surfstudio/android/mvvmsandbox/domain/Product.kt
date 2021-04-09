package ru.surfstudio.android.mvvmsandbox.domain

import java.io.Serializable

data class Product(
    val code: String = "",
    val name: String = "",
    val description: String = "",
    val descriptionShort: String = "",
    val manufacturer: String = "",
    val price: Price = Price(),
    val addedToWishlist: Boolean = false,
    val image: String = "",
) : Serializable
