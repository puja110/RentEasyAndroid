package com.example.renteasyandroid.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            getLayoutResource(viewType),
            parent,
            false
        )
        return getViewHolder(binding, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) =
        onBindCustomViewHolder(holder, position)

    abstract fun getViewHolder(binding: ViewDataBinding, viewType: Int): VH

    abstract fun onBindCustomViewHolder(holder: VH, position: Int)

    abstract fun getLayoutResource(viewType: Int): Int
}