package com.example.renteasyandroid.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.renteasyandroid.BR

open class BaseViewHolder(open val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    open fun bind(item: Any) {
        binding.setVariable(BR._all, item)
        binding.executePendingBindings()
    }
}