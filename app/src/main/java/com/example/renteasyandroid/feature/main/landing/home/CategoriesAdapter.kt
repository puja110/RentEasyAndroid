package com.example.renteasyandroid.feature.main.landing.home

import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseAdapter
import com.example.renteasyandroid.base.BaseViewHolder
import com.example.renteasyandroid.databinding.ItemCategoriesBinding
import com.example.renteasyandroid.feature.main.data.model.CategoryResponse

class CategoriesAdapter(
    private var dataList: MutableList<CategoryResponse>,
    private val onItemSelectedListener: (CategoryResponse) -> Unit
) : BaseAdapter<CategoryResponse, CategoriesAdapter.CategoriesViewHolder>() {

    override fun getViewHolder(binding: ViewDataBinding, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(binding as ItemCategoriesBinding)
    }

    override fun onBindCustomViewHolder(holder: CategoriesViewHolder, position: Int) {
        return holder.bindView(dataList[position])
    }

    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.item_categories
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(list: MutableList<CategoryResponse>) {
        this.dataList = list
    }

    inner class CategoriesViewHolder(private var binding: ItemCategoriesBinding) :
        BaseViewHolder<CategoryResponse>(binding) {
        override fun bindView(obj: CategoryResponse) {
            super.bindView(obj)
            binding.tvTitle.text = obj.title
            Glide.with(binding.root.context)
                .load(obj.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo).into(binding.ivCategory)

            binding.tvSearchCount.text = "${obj.searchCount} Searches"

            binding.cvCategory.setOnClickListener {
                onItemSelectedListener(obj)
            }
        }
    }
}