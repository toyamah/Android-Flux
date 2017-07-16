package example.duane0728.android_flux.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import example.duane0728.android_flux.*
import example.duane0728.android_flux.data.GithubRepository
import example.duane0728.android_flux.ui.main.list.ListAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

  @Inject lateinit var githubRepository: GithubRepository
  private val store: Store by lazy { ViewModelProviders.of(this).get(Store::class.java) }
  private val actions: Actions by lazy { Actions(githubRepository, store) }

  private val disposables: CompositeDisposable = CompositeDisposable()

  lateinit private var listAdapter: ListAdapter

  @BindView(R.id.toolbar)
  lateinit var toolbar: Toolbar

  @BindView(R.id.recycler_view)
  lateinit var recyclerView: RecyclerView

  @BindView(R.id.progress_bar)
  lateinit var progressBar: ProgressBar

  override fun onCreate(savedInstanceState: Bundle?) {
    DaggerAppComponent.builder().build().inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    ButterKnife.bind(this)

    progressBar.visibility = View.VISIBLE
    recyclerView.visibility = View.GONE
    initRecyclerView()

    if (store.isEmpty()) {
      // in case that this activity is created firstly or re-created after killing the process
      actions.fetchRetrofitStargazers(store.nextPage())
    }
  }

  private fun initRecyclerView() {
    listAdapter = ListAdapter()
    recyclerView.adapter = listAdapter
    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

    disposables += store.listData
        .subscribe {
          if (it.isEmpty()) {
            //TODO: show empty state
          } else {
            listAdapter.setData(it)
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
          }
        }

    disposables += store.isLoadingMore
        .subscribe {
          listAdapter.setLoading(it)
          // TODO: show progress bar at the bottom
        }

    recyclerView.loadMoreEvent(object : LoadMoreCallback {
      override fun isLoading(): Boolean = store.isLoadingMore.value
      override fun hasLoadedItems(): Boolean = store.hasLoadedAllItems()
    })
        .subscribe { actions.fetchRetrofitStargazers(store.nextPage()) }
  }

  override fun onDestroy() {
    disposables.clear()
    super.onDestroy()
  }
}

private operator fun CompositeDisposable.plusAssign(d: Disposable) {
  add(d)
}
