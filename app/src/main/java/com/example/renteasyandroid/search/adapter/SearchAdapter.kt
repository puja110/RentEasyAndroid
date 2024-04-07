package com.example.renteasyandroid.search.adapter

import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseAdapter
import com.example.renteasyandroid.base.BaseViewHolder
import com.example.renteasyandroid.databinding.ItemSearchBinding
import com.example.renteasyandroid.search.data.model.SearchResponse

class SearchAdapter(
    private var dataList: MutableList<SearchResponse>,
    private val onItemSelectedListener: (SearchResponse) -> Unit
) : BaseAdapter<SearchResponse, SearchAdapter.SearchViewHolder>() {

    override fun getViewHolder(binding: ViewDataBinding, viewType: Int): SearchViewHolder {
        return SearchViewHolder(binding as ItemSearchBinding)
    }

    override fun onBindCustomViewHolder(holder: SearchViewHolder, position: Int) {
        return holder.bindView(dataList[position])
    }

    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.item_search
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(list: MutableList<SearchResponse>) {
        this.dataList = list
    }

    inner class SearchViewHolder(private var binding: ItemSearchBinding) :
        BaseViewHolder<SearchResponse>(binding) {
        override fun bindView(obj: SearchResponse) {
            super.bindView(obj)
            binding.tvTitle.text = obj.title
            Glide.with(binding.root.context)
                .load(obj.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_logo).into(binding.ivFavourites)

            binding.tvPer.text = "/ ${obj.price_type}"
            binding.tvAddress.text = obj.address
            binding.tvRoomCount.text = "${obj.roomCount} room"
            binding.tvPrice.text = "${obj.currency_code}${obj.price}"
            binding.tvStatus.text = obj.status
            if (obj.status == "Available") {
                binding.tvStatus.setTextColor(
                    ContextCompat.getColor(
                        binding.tvStatus.context,
                        R.color.colorAccent
                    )
                )
                binding.tvStatus.background = ContextCompat.getDrawable(
                    binding.tvStatus.context,
                    R.drawable.bg_red
                )
            } else {
                binding.tvStatus.setTextColor(
                    ContextCompat.getColor(
                        binding.tvStatus.context,
                        R.color.colorMain
                    )
                )
                binding.tvStatus.background = ContextCompat.getDrawable(
                    binding.tvStatus.context,
                    R.drawable.bg_green
                )
            }

            binding.cvFavourites.setOnClickListener {
                onItemSelectedListener(obj)
            }
        }
    }
}