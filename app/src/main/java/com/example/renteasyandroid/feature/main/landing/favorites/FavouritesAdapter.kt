package com.example.renteasyandroid.feature.main.landing.favorites

import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseAdapter
import com.example.renteasyandroid.base.BaseViewHolder
import com.example.renteasyandroid.databinding.ItemFavouritesBinding
import com.example.renteasyandroid.feature.main.data.model.FavouritesResponse

class FavouritesAdapter(
    private var dataList: MutableList<FavouritesResponse>,
    private val onItemSelectedListener: (FavouritesResponse) -> Unit
) : BaseAdapter<FavouritesResponse, FavouritesAdapter.RecentlyUpdatedViewHolder>() {

    override fun getViewHolder(binding: ViewDataBinding, viewType: Int): RecentlyUpdatedViewHolder {
        return RecentlyUpdatedViewHolder(binding as ItemFavouritesBinding)
    }

    override fun onBindCustomViewHolder(holder: RecentlyUpdatedViewHolder, position: Int) {
        return holder.bindView(dataList[position])
    }

    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.item_favourites
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(list: MutableList<FavouritesResponse>) {
        this.dataList = list
    }

    inner class RecentlyUpdatedViewHolder(private var binding: ItemFavouritesBinding) :
        BaseViewHolder<FavouritesResponse>(binding) {
        override fun bindView(obj: FavouritesResponse) {
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