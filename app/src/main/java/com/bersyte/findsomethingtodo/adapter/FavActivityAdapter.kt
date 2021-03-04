package com.bersyte.findsomethingtodo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bersyte.findsomethingtodo.R
import com.bersyte.findsomethingtodo.databinding.ItemFavActLayoutBinding
import com.bersyte.findsomethingtodo.fragments.FavoriteFragment
import com.bersyte.findsomethingtodo.fragments.FavoriteFragmentDirections
import com.bersyte.findsomethingtodo.models.RandomActivity

class FavActivityAdapter(
    private val fragment: Fragment
) : RecyclerView.Adapter<FavActivityAdapter.FavActivityViewHolder>() {

    inner class FavActivityViewHolder(val binding: ItemFavActLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<RandomActivity>() {
        override fun areItemsTheSame(oldItem: RandomActivity, newItem: RandomActivity): Boolean {
            return oldItem.activity == newItem.activity
        }

        override fun areContentsTheSame(oldItem: RandomActivity, newItem: RandomActivity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavActivityViewHolder {
        return FavActivityViewHolder(
            ItemFavActLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavActivityViewHolder, position: Int) {
        val currAct = differ.currentList[position]

        holder.binding.apply {

            tvActivity.text = currAct.activity
            tvAccessibility.text = currAct.accessibility.toString()
            tvParticipants.text = currAct.participants.toString()
            tvType.text = currAct.type
            tvPrice.text = currAct.price.toString()

        }

        holder.itemView.setOnClickListener { mView ->
            if (currAct.link!!.isNotBlank()) {
                val direction = FavoriteFragmentDirections
                    .actionFavoriteFragmentToActivityDetailsFragment(currAct)
                mView.findNavController().navigate(direction)
                (fragment as FavoriteFragment)
            }


        }

        holder.binding.ibMore.setOnClickListener {
            val popup = fragment.context?.let {
                PopupMenu(it,
                    holder.binding.ibMore
                )
            }

            popup?.menuInflater?.inflate(
                R.menu.menu_adapter_more,
                popup.menu
            )


            popup?.setOnMenuItemClickListener { item ->

                if (item.itemId == R.id.action_delete_fav_act) {
                    if (fragment is FavoriteFragment) {
                        fragment.deleteFavActivity(currAct)
                    }
                }
                true
            }
            popup?.show()
        }
    }

    override fun getItemCount() = differ.currentList.size
}