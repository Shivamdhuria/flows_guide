package com.elixer.paws.favorite

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elixer.paws.R
import com.elixer.paws.extensions.inflate
import com.elixer.paws.favorite.data.FavoriteDog
import kotlinx.android.extensions.LayoutContainer

class FavoriteAdapter : ListAdapter<FavoriteDog, FavoriteAdapter.UserDateViewHolder>(UserDataAdapterListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDateViewHolder =
            UserDateViewHolder(parent.inflate(R.layout.item_dog))

    override fun onBindViewHolder(holder: UserDateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class UserDataAdapterListDiff : DiffUtil.ItemCallback<FavoriteDog>() {

        override fun areItemsTheSame(oldItem: FavoriteDog, newItem: FavoriteDog): Boolean {
            return oldItem.breed == newItem.breed
        }

        override fun areContentsTheSame(oldItem: FavoriteDog, newItem: FavoriteDog): Boolean {
            return oldItem == newItem
        }
    }

    inner class UserDateViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(dog: FavoriteDog) {
//            containerView.breed_name.text = dog.breed?.capitalize()
//            dog.imageUrl?.let { it1 -> ImageLoader.loadImageWithCircularCrop(containerView.context, it1, containerView.image_dog) }
//            this.containerView.card_layout.setCardBackgroundColor(Color.YELLOW)
        }
    }
}