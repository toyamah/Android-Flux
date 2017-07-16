package example.duane0728.android_flux.ui.main.list

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import example.duane0728.android_flux.R
import example.duane0728.android_flux.model.User

class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  private val viewTypeUser = 1
  private val viewTypeProgress = 2

  private var itemList: List<ListItem> = emptyList()
  private var userList: List<User> = emptyList()
  private var isLoading: Boolean = false

  override fun getItemViewType(position: Int): Int {
    return when(itemList[position]) {
      is UserItem -> viewTypeUser
      is ProgressItem -> viewTypeProgress
      else -> throw IllegalArgumentException("unexpected position $position")
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val inflater = LayoutInflater.from(parent.context)

    return when(viewType) {
      viewTypeUser -> UserViewHolder.create(inflater, parent)
      viewTypeProgress -> ProgressViewHolder.create(inflater, parent)
      else -> throw IllegalArgumentException("unexpected viewType $viewType")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val item = itemList[position]
    when (item) {
      is UserItem -> (holder as UserViewHolder).bind(item.user)
    }
  }

  override fun getItemCount(): Int = itemList.size

  fun setData(userList: List<User>) {
    this.userList = userList
    updateData()
  }

  fun setLoading(isLoading: Boolean) {
    this.isLoading = isLoading
    updateData()
  }

  private fun updateData() {
    val oldList = itemList.toList()
    val newList: List<ListItem> = when(isLoading) {
      true -> userList.map { UserItem(it) }.plus(ProgressItem)
      false -> userList.map { UserItem(it) }
    }
    this.itemList = newList

    val diffResult = DiffUtil.calculateDiff(SimpleDiffUtilCallback(oldList, newList))
    diffResult.dispatchUpdatesTo(this)
  }

  class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameView: TextView = itemView.findViewById(R.id.user_name)
    val avatarImageView: ImageView = itemView.findViewById(R.id.user_avatar_image)

    fun bind(user: User) {
      nameView.text = user.name

      Glide.with(avatarImageView)
          .load(user.avatarUrl)
          .into(avatarImageView)
    }

    companion object {
      fun create(inflater: LayoutInflater, parent: ViewGroup): UserViewHolder {
        val itemView = inflater.inflate(R.layout.list_item_user, parent, false)
        return UserViewHolder(itemView)
      }
    }
  }

  class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
      fun create(inflater: LayoutInflater, parent: ViewGroup): ProgressViewHolder {
        val itemView = inflater.inflate(R.layout.centered_progress_bar, parent, false)
        return ProgressViewHolder(itemView)
      }
    }
  }
}
