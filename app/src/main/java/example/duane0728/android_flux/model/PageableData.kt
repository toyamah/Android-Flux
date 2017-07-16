package example.duane0728.android_flux.model

data class PageableData<out T>(
    val list: List<T>,
    val hasNextData: Boolean
)
