package example.framgia.com.demo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import example.framgia.com.demo.data.model.User
import kotlinx.android.synthetic.main.item_adapter.view.*

class ItemAdapter(private var itemClick: ItemClick) : RecyclerView.Adapter<ItemAdapter.Companion.ItemHolder>() {

    private var listUser: ArrayList<User> = ArrayList()

    fun updateListUser(list: List<User>) {
        listUser.clear()
        listUser.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_adapter, viewGroup, false), itemClick
        )
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(itemHolder: ItemHolder, pos: Int) {
        itemHolder.bindData(listUser[pos])
    }

    companion object {

        class ItemHolder(itemView: View, private var itemClick: ItemClick) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

            private lateinit var user: User

            init {
                itemView.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                itemClick.onItemClick(user, adapterPosition)
            }

            fun bindData(user: User) {
                this.user = user
                itemView.text_userFirstName.text = user.firstName
                itemView.text_userLastName.text = user.lastName
            }
        }

    }

    interface ItemClick {
        fun onItemClick(user: User, pos: Int)
    }
}
