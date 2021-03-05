package com.swsbt.secret.helper.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding

open class SingleTypeAdapter<T>(context: Context, private val layoutRes: Int, list: ObservableArrayList<T>) : BindingViewAdapter<T>(context, list) {

    init {
        initSingleList()
    }

    protected fun initSingleList() {
        list.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
            override fun onChanged(sender: ObservableList<T>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
                notifyItemRangeChanged(positionStart, itemCount)
            }

            override fun onItemRangeInserted(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeMoved(sender: ObservableList<T>, fromPosition: Int, toPosition: Int, itemCount: Int) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
                notifyDataSetChanged()
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ViewDataBinding> =
        BindingViewHolder(DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutRes, parent, false))

}
