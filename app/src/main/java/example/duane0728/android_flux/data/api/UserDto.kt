package example.duane0728.android_flux.data.api

import example.duane0728.android_flux.model.User

data class UserDto(
    val id: Long,
    val login: String,
    val avatar_url: String
) {

  fun toUser(): User = User(id, login, avatar_url)
}
