package com.artishevsky.android.playground.feature.creatures.view.allcreatures

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.artishevsky.android.playground.R
import com.artishevsky.android.playground.databinding.ActivityAllCreaturesBinding
import com.artishevsky.android.playground.feature.creatures.view.creature.CreatureActivity
import com.artishevsky.android.playground.feature.creatures.viewmodel.AllCreaturesViewModel

class AllCreaturesActivity : AppCompatActivity() {

    private lateinit var viewModel: AllCreaturesViewModel

    private lateinit var binding: ActivityAllCreaturesBinding

    private val adapter = CreatureAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllCreaturesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AllCreaturesViewModel::class.java]
        viewModel.getAllCreaturesLiveData().observe(this) { creatures ->
            creatures?.let {
                adapter.updateCreatures(creatures)
            }
        }
        binding.content.creaturesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.content.creaturesRecyclerView.adapter = adapter

        binding.fab.setOnClickListener {
            startActivity(Intent(this, CreatureActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_all -> {
                viewModel.clearAllCreatures()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}