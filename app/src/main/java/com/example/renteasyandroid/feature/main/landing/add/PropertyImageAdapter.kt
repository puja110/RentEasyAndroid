package com.example.renteasyandroid.feature.main.landing.add

import android.net.Uri
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseAdapter
import com.example.renteasyandroid.base.BaseViewHolder
import com.example.renteasyandroid.databinding.ItemPropertyImageBinding

class PropertyImageAdapter(
    private var dataList: MutableList<Uri>,
    private val onItemSelectedListener: (Uri) -> Unit,
) : BaseAdapter<Uri, PropertyImageAdapter.PropertyImageViewHolder>() {

    override fun getViewHolder(binding: ViewDataBinding, viewType: Int): PropertyImageViewHolder {
        return PropertyImageViewHolder(binding as ItemPropertyImageBinding)
    }

    override fun onBindCustomViewHolder(holder: PropertyImageViewHolder, position: Int) {
        return holder.bindView(dataList[position])
    }

    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.item_property_image
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(list: MutableList<Uri>) {
        this.dataList = list
    }

    inner class PropertyImageViewHolder(private var binding: ItemPropertyImageBinding) :
        BaseViewHolder<Uri>(binding) {
        override fun bindView(obj: Uri) {
            super.bindView(obj)
            Glide.with(binding.root.context)
                .load(obj)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo).into(binding.ivPropertyImage)


            binding.root.setOnClickListener {
                onItemSelectedListener(obj)
            }
        }
    }
}