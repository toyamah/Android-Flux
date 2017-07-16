package example.duane0728.android_flux.data.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

  @GET("/repos/{owner}/{repo}/stargazers")
  fun repositoryStargazers(
      @Path("owner") owner: String,
      @Path("repo") repository: String,
      @Query("page") page: Int
  ): Single<Response<List<UserDto>>>
}