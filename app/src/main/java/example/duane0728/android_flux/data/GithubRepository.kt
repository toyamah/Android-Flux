package example.duane0728.android_flux.data

import example.duane0728.android_flux.data.api.GithubApi
import example.duane0728.android_flux.model.PageableData
import example.duane0728.android_flux.model.User
import io.reactivex.Single
import javax.inject.Inject

class GithubRepository @Inject constructor(private val githubApi: GithubApi) {

  fun getRepositoryStargazers(owner: String, repository: String,
      page: Int): Single<PageableData<User>> = githubApi.repositoryStargazers(owner, repository, page)
}