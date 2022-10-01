package com.artishevsky.android.playground.feature.creatures.view.avatars

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.artishevsky.android.playground.R
import com.artishevsky.android.playground.feature.creatures.model.Avatar

class AvatarAdapter(
    private val avatars: List<Avatar>,
    private val listener: AvatarListener
) : RecyclerView.Adapter<AvatarAdapter.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_avatar, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(avatars[position])
    }

    override fun getItemCount(): Int {
        return avatars.size
    }

    interface AvatarListener {
        fun avatarClicked(avatar: Avatar)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private lateinit var avatar: Avatar

        private val imageView = itemView.findViewById<ImageView>(R.id.avatar)

        init {
            itemView.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(avatar: Avatar) {
            this.avatar = avatar
            val bitmap = BitmapFactory.decodeResource(imageView.context.resources, avatar.drawable)
            imageView.setImageDrawable(BitmapDrawable(imageView.context.resources, bitmap))
        }

        override fun onClick(view: View) {
            listener.avatarClicked(this.avatar)
        }
    }
}