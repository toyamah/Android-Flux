package example.duane0728.android_flux.data.api

import example.duane0728.android_flux.model.PageableData
import example.duane0728.android_flux.model.User
import io.reactivex.Single
import okhttp3.Headers
import javax.inject.Inject

class GithubApi @Inject constructor(private val service: GithubService) {

  private val NEXT_PAGE: Regex = Regex("rel=\"next\".*")

  fun repositoryStargazers(owner: String, repository: String,
      page: Int): Single<PageableData<User>> {
    return service.repositoryStargazers(owner, repository, page)
        .flatMap {
          val body = it.body()
          if (it.isSuccessful && body != null) {
            Single.just(toPageableData(body, it.headers()))
          } else {
            Single.error(Exception("An error occurred"))
          }
        }
  }

  private fun toPageableData(list: List<UserDto>, header: Headers): PageableData<User> {
    val userList = list.map { it.toUser() }
    val links = header["Link"]
        ?: return PageableData(userList, false)

    val hasNextData = links.contains(NEXT_PAGE)
    return PageableData(userList, hasNextData)
  }
}