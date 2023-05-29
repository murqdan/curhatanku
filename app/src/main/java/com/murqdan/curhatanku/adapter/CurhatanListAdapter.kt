package com.murqdan.curhatanku.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murqdan.curhatanku.databinding.ItemCurhatanBinding
import com.murqdan.curhatanku.response.ListCurhatanItem
import com.murqdan.curhatanku.view.detailcurhatan.DetailCurhatanActivity

class CurhatanListAdapter: PagingDataAdapter<ListCurhatanItem, CurhatanListAdapter.MyViewHolder>(
    DIFF_CALLBACK
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCurhatanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemCurhatanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data:ListCurhatanItem){
            binding.apply{
                Glide.with(itemView)
                    .load(data.photoUrl)
                    .into(binding.ivItemPhoto)
                binding.tvItemName.text = data.name
                binding.tvDescription.text = data.description
            }

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailCurhatanActivity::class.java)
                intent.putExtra("DATA", data)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivItemPhoto, "profile"),
                        Pair(binding.tvItemName, "name"),
                        Pair(binding.tvDescription, "description"),
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {
         val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListCurhatanItem>() {
            override fun areItemsTheSame(oldItem: ListCurhatanItem, newItem: ListCurhatanItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListCurhatanItem, newItem: ListCurhatanItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
