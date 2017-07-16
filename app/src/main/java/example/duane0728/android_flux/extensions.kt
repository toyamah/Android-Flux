package example.duane0728.android_flux

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jakewharton.rxbinding2.support.v7.widget.scrollEvents
import io.reactivex.Observable

/**
 * Create an Observable which emits when subscribers should load more.
 */
fun RecyclerView.loadMoreEvent(callback: LoadMoreCallback): Observable<Irrelevant> {
  if (layoutManager !is LinearLayoutManager) {
    throw IllegalStateException(
        "layout manager must be LinearLayoutManager, but was ${layoutManager.javaClass.simpleName}")
  }

  return scrollEvents()
      .filter {
        if (callback.isLoading() || callback.hasLoadedItems()) {
          false
        } else {
          val manager = layoutManager as LinearLayoutManager
          val visibleCount = childCount
          val totalCount = manager.itemCount
          val firstPos = manager.findFirstVisibleItemPosition()
          totalCount - visibleCount <= firstPos
        }
      }
      .map { Irrelevant }
}

interface LoadMoreCallback {

  /** Whether it is loading now or not */
  fun isLoading(): Boolean

  /** Whether it has loaded all items or not */
  fun hasLoadedItems(): Boolean
}
