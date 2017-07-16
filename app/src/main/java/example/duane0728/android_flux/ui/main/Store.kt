package example.duane0728.android_flux.ui.main

import android.arch.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import example.duane0728.android_flux.model.PageableData
import example.duane0728.android_flux.model.User

/**
 * A store which can hold view-related data as long as the scope is alive
 *
 * @see ViewModel
 */
class Store : ViewModel() {
  private var hasLoadedFirstItem: Boolean = false
  private var hasLoadedAllItems: Boolean = false
  private var nextPage: Int = 1

  // BehaviorRelay is used to hold each data.
  // guess LiveData can be used instead of RxRelay.
  val listData: BehaviorRelay<List<User>> = BehaviorRelay.create()
  val error: BehaviorRelay<String> = BehaviorRelay.create()
  val isLoadingMore: BehaviorRelay<Boolean> = BehaviorRelay.createDefault(false)

  fun isEmpty(): Boolean = !listData.hasValue()
  fun hasLoadedAllItems(): Boolean = hasLoadedAllItems
  fun nextPage(): Int = nextPage

  fun acceptListData(additional: PageableData<User>) {
    val stored = listData.value ?: emptyList()
    listData.accept(stored + additional.list)

    if (additional.hasNextData) {
      nextPage++
    } else {
      hasLoadedAllItems = true
    }
  }

  fun acceptError(error: Throwable) {
    //TODO: handle error appropriately
    this.error.accept(error.message ?: "")
  }

  fun acceptLoading(isLoading: Boolean) {
    if (hasLoadedFirstItem) {
      isLoadingMore.accept(isLoading)
      return
    }

    if (!isLoading) {
      hasLoadedFirstItem = true
    }
  }
}