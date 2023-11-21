package com.example.renteasyandroid.feature.main.landing.home

import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseAdapter
import com.example.renteasyandroid.base.BaseViewHolder
import com.example.renteasyandroid.databinding.ItemRecentlyUpdatedBinding
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse

class RecentlyUpdatedAdapter(
    private var dataList: MutableList<RecentlyUpdatedResponse>,
    private val onItemSelectedListener: (RecentlyUpdatedResponse) -> Unit
    ) : BaseAdapter<RecentlyUpdatedResponse, RecentlyUpdatedAdapter.RecentlyUpdatedViewHolder>() {

    override fun getViewHolder(binding: ViewDataBinding, viewType: Int): RecentlyUpdatedViewHolder {
        return RecentlyUpdatedViewHolder(binding as ItemRecentlyUpdatedBinding)
    }

    override fun onBindCustomViewHolder(holder: RecentlyUpdatedViewHolder, position: Int) {
        return holder.bindView(dataList[position])
    }

    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.item_recently_updated
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(list: MutableList<RecentlyUpdatedResponse>) {
        this.dataList = list
    }

    inner class RecentlyUpdatedViewHolder(private var binding: ItemRecentlyUpdatedBinding) :
        BaseViewHolder<RecentlyUpdatedResponse>(binding) {
        override fun bindView(obj: RecentlyUpdatedResponse) {
            super.bindView(obj)
            binding.tvTitle.text = obj.title
            Glide.with(binding.root.context)
                .load(obj.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_logo).into(binding.ivRecentlyUpdated)

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

            binding.cvRecentlyUpdated.setOnClickListener {
                onItemSelectedListener(obj)
            }
        }
    }
}