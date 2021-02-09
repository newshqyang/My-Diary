package com.swsbt.secret.helper.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

abstract class BindingViewAdapter<T>(context: Context, protected val list: ObservableList<T>): RecyclerView.Adapter<BindingViewHolder<ViewDataBinding>>() {

    protected val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)

    var itemPresenter: ItemClickPresenter<T>? = null

    var itemDecorator: ItemDecorator? = null

    override fun onBindViewHolder(holder: BindingViewHolder<ViewDataBinding>, position: Int) {
        val item = list[position]
        holder.let {
            it.binding.setVariable(BR.item, item)
            it.binding.setVariable(BR.presenter, itemPresenter)
            it.binding.executePendingBindings()
        }
        itemDecorator?.decorator(holder, position, getItemViewType(position))
    }

    override fun getItemCount(): Int = list.size

    fun getItem(position: Int): T? = list[position]

    interface ItemClickPresenter<in Any> {
        fun onItemClick(v: View, item: Any)
    }

    interface ItemLongClickPresenter<in Any> {
        fun onItemLongClick(v: View, item: Any)
    }

    interface ItemDecorator {
        fun decorator(holder: BindingViewHolder<ViewDataBinding>?, position: Int, viewType: Int)
    }

}