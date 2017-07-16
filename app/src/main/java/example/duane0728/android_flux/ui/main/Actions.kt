package example.duane0728.android_flux.ui.main

import example.duane0728.android_flux.data.GithubRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * A facade to access classes which belong to domain or data layer
 * (e.g Repositories, Application Services or UseCases)
 *
 * Instead of using Dispatcher,
 * An action depends on a specific store directly to simplify the design.
 */
class Actions(
    private val githubRepository: GithubRepository,
    private val store: Store
) {

  fun fetchRetrofitStargazers(page: Int) {
    githubRepository.getRepositoryStargazers("square", "retrofit", page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { store.acceptLoading(true) }
        .doOnEvent { _, _ -> store.acceptLoading(false) }
        .subscribe(
            { store.acceptListData(it) },
            { store.acceptError(it) }
        )
  }
}