package ru.surfstudio.android.mvvmsandbox.domain

import java.io.Serializable

data class Category(
    val code: String = "",
    val children: List<Category> = emptyList(),
    val name: String = "",
    val rigidCode: Long = 0
) : Serializable {

    fun isCodeSale(): Boolean {
        return code == "Sale"
    }

    companion object {
        const val GIFT_CARDS_CODE_LOCAL = "Gifts_Custom_Code"
        const val GIFT_CARDS_CODE_SERVER = "Gifts"
    }
}