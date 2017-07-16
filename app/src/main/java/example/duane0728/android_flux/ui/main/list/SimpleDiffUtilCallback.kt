package example.duane0728.android_flux.ui.main.list

import android.support.v7.util.DiffUtil

class SimpleDiffUtilCallback(private val oldList: List<*>, private val newList: List<*>) : DiffUtil.Callback() {

  override fun getOldListSize(): Int = oldList.size

  override fun getNewListSize(): Int = newList.size

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
      = oldList[oldItemPosition] == newList[newItemPosition]

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
      = oldList[oldItemPosition] == newList[newItemPosition]
}