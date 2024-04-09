package com.example.renteasyandroid.feature.main.landing.home

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseAdapter
import com.example.renteasyandroid.base.BaseViewHolder
import com.example.renteasyandroid.databinding.ItemRecentlyUpdatedBinding
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse
import java.util.EventListener

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
            binding.tvTitle.text = obj.propertyName

            if (!obj.imageUrls.isNullOrEmpty() && obj.imageUrls.isNotEmpty()) {
                Glide.with(binding.root.context)
                    .load(obj.imageUrls[0]) // Since we've already checked for null or empty, directly access the first item
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_logo)
                    .into(binding.ivRecentlyUpdated)
            } else {
                // Optionally, handle the case where there are no URLs, e.g., by setting a default image
                Glide.with(binding.root.context)
                    .load("\"https://firebasestorage.googleapis.com:443/v0/b/renteasy-7a973.appspot.com/o/propertyImages%2FE499D4D7-2441-4048-8424-F59D51118D46.jpg?alt=media&token=d591f92b-ea82-4007-a584-47f11c9dfc44\"") // Assuming ic_default_placeholder is a default image in your drawables
                    .into(binding.ivRecentlyUpdated)
            }

            binding.tvPer.text = obj.propertyCategory
            binding.tvAddress.text = obj.propertyAddress
            binding.tvRoomCount.text = "${obj.propertySize} room"
            binding.tvPrice.text = "$ ${obj.propertyAmount}"
            binding.tvStatus.text = if (obj.isBooked == true) "Booked" else "Available"

            val status = if (obj.isBooked == true) "Booked" else "Available"
            if (status == "Available") {
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

            if(obj.isFavourite == true){
                binding.ivFavourite.setImageResource(R.drawable.ic_heart_fill)
            }else {
                binding.ivFavourite.setImageResource(R.drawable.ic_heart)

            }

            binding.cvRecentlyUpdated.setOnClickListener {
//                onItemSelectedListener(obj)
            }

            binding.ivFavourite.setOnClickListener {
                Log.d(Companion.TAG, "bindView: ")
                onItemSelectedListener(obj)
            }
        }
    }

    companion object {
        private const val TAG = "RecentlyUpdatedAdapter"
    }
}