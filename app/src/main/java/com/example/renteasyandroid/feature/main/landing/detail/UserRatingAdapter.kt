package com.example.renteasyandroid.feature.main.landing.detail
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.database.entity.UserRatingEntity

class UserRatingAdapter(
    val itemList: ArrayList<UserRatingEntity>,
    private val onItemSelectedListener: (UserRatingEntity) -> Unit
) :
    RecyclerView.Adapter<UserRatingAdapter.ModelViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserRatingAdapter.ModelViewHolder {

        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_rating, parent, false)
        return ModelViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserRatingAdapter.ModelViewHolder, position: Int) {
        Glide.with(holder.image.context)
            .load(
                itemList[position].userImage
            )
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo).into(holder.image)
        holder.name.text = itemList[position].username
        holder.description.text = itemList[position].description
        holder.ratingBar.rating = itemList[position].rating ?: 0.0F
        holder.constraintLayout.setOnClickListener {
            onItemSelectedListener(itemList[position])
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var constraintLayout: ConstraintLayout
        var image: ImageView
        var name: TextView
        var description: TextView
        var ratingBar: RatingBar

        init {
            constraintLayout = itemView.findViewById(
                R.id.constraintLayout
            ) as ConstraintLayout
            image = itemView.findViewById(R.id.ivImage) as ImageView
            name = itemView.findViewById(R.id.tvName) as TextView
            description = itemView.findViewById(R.id.tvDescription) as TextView
            ratingBar = itemView.findViewById(R.id.ratingBar) as RatingBar
        }

    }
}