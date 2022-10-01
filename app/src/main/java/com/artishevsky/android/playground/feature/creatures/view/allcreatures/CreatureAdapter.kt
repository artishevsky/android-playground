package com.artishevsky.android.playground.feature.creatures.view.allcreatures

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.createBitmap
import androidx.recyclerview.widget.RecyclerView
import com.artishevsky.android.playground.R
import com.artishevsky.android.playground.databinding.ListItemCreatureBinding
import com.artishevsky.android.playground.feature.creatures.model.Creature

class CreatureAdapter(
    private val creatures: MutableList<Creature>
) : RecyclerView.Adapter<CreatureAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemCreatureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatures[position])
    }

    override fun getItemCount(): Int {
        return creatures.size
    }

    fun updateCreatures(creatures: List<Creature>) {
        this.creatures.clear()
        this.creatures.addAll(creatures)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ListItemCreatureBinding) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var creature: Creature


        fun bind(creature: Creature) {
            this.creature = creature

            binding.avatarListItem.setImageDrawable(AppCompatResources.getDrawable(itemView.context, creature.drawable))
            binding.name.text = creature.name
            binding.hitPoints.text = creature.hitPoints.toString()
        }
    }
}