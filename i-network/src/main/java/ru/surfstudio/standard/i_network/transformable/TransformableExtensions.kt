package ru.surfstudio.standard.i_network.transformable

/**
 * Трансформирует коллекцию [Transformable] элементов
 */
fun <R, T : Transformable<R>> List<T>?.transformCollection(): List<R> {
    return this?.map { it.transform() } ?: emptyList()
}
