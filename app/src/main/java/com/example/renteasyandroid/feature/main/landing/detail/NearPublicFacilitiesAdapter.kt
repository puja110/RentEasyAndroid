package com.example.renteasyandroid.feature.main.landing.detail

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseAdapter
import com.example.renteasyandroid.base.BaseViewHolder
import com.example.renteasyandroid.databinding.ItemNearPublicFacilitiesBinding
import com.example.renteasyandroid.feature.main.data.model.NearPublicFacilitiesResponse

class NearPublicFacilitiesAdapter(
    private var dataList: MutableList<NearPublicFacilitiesResponse>
) : BaseAdapter<NearPublicFacilitiesResponse, NearPublicFacilitiesAdapter.NearPublicFacilitiesViewHolder>() {

    override fun getViewHolder(
        binding: ViewDataBinding,
        viewType: Int
    ): NearPublicFacilitiesViewHolder {
        return NearPublicFacilitiesViewHolder(binding as ItemNearPublicFacilitiesBinding)
    }

    override fun onBindCustomViewHolder(holder: NearPublicFacilitiesViewHolder, position: Int) {
        return holder.bindView(dataList[position])
    }

    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.item_near_public_facilities
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(list: MutableList<NearPublicFacilitiesResponse>) {
        this.dataList = list
    }

    inner class NearPublicFacilitiesViewHolder(private var binding: ItemNearPublicFacilitiesBinding) :
        BaseViewHolder<NearPublicFacilitiesResponse>(binding) {
        override fun bindView(obj: NearPublicFacilitiesResponse) {
            super.bindView(obj)
            binding.tvNearPublicFacilities.text = obj.title
            binding.tvDistance.text = obj.distance
            Glide.with(binding.root.context)
                .load(
                    getImage(
                        binding.root.context, obj
                    )
                )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo).into(binding.ivNearPublicFacilities)
        }
    }

    fun getImage(context: Context, obj: NearPublicFacilitiesResponse): Drawable? {
        return when (obj.id) {
            1 -> {
                ContextCompat.getDrawable(context, R.drawable.ic_mini_market)
            }

            2 -> {
                ContextCompat.getDrawable(context, R.drawable.ic_hospital)
            }

            3 -> {
                ContextCompat.getDrawable(context, R.drawable.ic_public_canteen)
            }

            else -> {
                ContextCompat.getDrawable(context, R.drawable.ic_train_station)
            }
        }
    }
}