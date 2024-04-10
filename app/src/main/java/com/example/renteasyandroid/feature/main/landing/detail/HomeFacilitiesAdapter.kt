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
import com.example.renteasyandroid.databinding.ItemHomeFacilitiesBinding
import com.example.renteasyandroid.feature.main.data.model.HomeFacilitiesResponse

/**
 * Adapter for displaying home facilities in the details screen.
 */
class HomeFacilitiesAdapter(
    private var dataList: MutableList<HomeFacilitiesResponse>
) : BaseAdapter<HomeFacilitiesResponse, HomeFacilitiesAdapter.HomeFacilitiesViewHolder>() {

    override fun getViewHolder(binding: ViewDataBinding, viewType: Int): HomeFacilitiesViewHolder {
        return HomeFacilitiesViewHolder(binding as ItemHomeFacilitiesBinding)
    }

    override fun onBindCustomViewHolder(holder: HomeFacilitiesViewHolder, position: Int) {
        // Bind data to the view holder
        holder.bindView(dataList[position])
    }

    override fun getLayoutResource(viewType: Int): Int {
        // Provide the layout resource for each item in the adapter
        return R.layout.item_home_facilities
    }

    override fun getItemCount(): Int {
        // Return the number of items in the data list
        return dataList.size
    }

    /**
     * Update the data list with new items.
     */
    fun updateData(list: MutableList<HomeFacilitiesResponse>) {
        this.dataList = list
    }

    /**
     * ViewHolder class for home facilities items.
     */
    inner class HomeFacilitiesViewHolder(private var binding: ItemHomeFacilitiesBinding) :
        BaseViewHolder<HomeFacilitiesResponse>(binding) {

        /**
         * Bind data to the view holder.
         */
        override fun bindView(obj: HomeFacilitiesResponse) {
            super.bindView(obj)
            binding.tvHomeFacilities.text = obj.title
            // Load image using Glide library
            Glide.with(binding.root.context)
                .load(getImage(binding.root.context, obj))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo) // Placeholder image while loading
                .into(binding.ivHomeFacilities)
        }
    }

    /**
     * Get the corresponding image drawable based on the facility ID.
     */
    private fun getImage(context: Context, obj: HomeFacilitiesResponse): Drawable? {
        return when (obj.id) {
            1 -> {
                ContextCompat.getDrawable(context, R.drawable.ic_heating)
            }
            2 -> {
                ContextCompat.getDrawable(context, R.drawable.ic_laundry)
            }
            3 -> {
                ContextCompat.getDrawable(context, R.drawable.ic_parking)
            }
            else -> {
                ContextCompat.getDrawable(context, R.drawable.ic_wifi)
            }
        }
    }
}
