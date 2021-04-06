package ru.surfstudio.android.mvvmsandbox.network

/**
 * Коды ошибок
 */
object HttpCodes {

    const val CODE_200 = 200 //Успех
    const val CODE_304 = 304 //Нет обновленных данных
    const val CODE_400 = 400 //Bad request
    const val CODE_401 = 401 //Невалидный токен
    const val CODE_403 = 403 //Доступ запрещен
    const val CODE_404 = 404 //Не найдено
    const val CODE_500 = 500 //Внутренняя ошибка сервера, сервер не доступен
    const val UNSPECIFIED = 0 //не определен
}