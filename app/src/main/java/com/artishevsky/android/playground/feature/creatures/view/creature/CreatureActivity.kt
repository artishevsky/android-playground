package com.artishevsky.android.playground.feature.creatures.view.creature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.artishevsky.android.playground.PlaygroundApplication
import com.artishevsky.android.playground.R
import com.artishevsky.android.playground.databinding.ActivityCreatureBinding
import com.artishevsky.android.playground.feature.creatures.model.AttributeStore.ENDURANCE
import com.artishevsky.android.playground.feature.creatures.model.AttributeStore.INTELLIGENCE
import com.artishevsky.android.playground.feature.creatures.model.AttributeStore.STRENGTH
import com.artishevsky.android.playground.feature.creatures.model.AttributeType
import com.artishevsky.android.playground.feature.creatures.model.AttributeType.*
import com.artishevsky.android.playground.feature.creatures.model.Avatar
import com.artishevsky.android.playground.feature.creatures.model.room.CreatureDatabase
import com.artishevsky.android.playground.feature.creatures.view.avatars.AvatarAdapter
import com.artishevsky.android.playground.feature.creatures.view.avatars.AvatarBottomDialogFragment
import com.artishevsky.android.playground.feature.creatures.viewmodel.CreatureViewModel

class CreatureActivity : AppCompatActivity(), AvatarAdapter.AvatarListener {

    private lateinit var binding: ActivityCreatureBinding

    private lateinit var viewModel: CreatureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CreatureViewModel::class.java]

        configureUI()
        configureSpinnerAdapters()
        configureSpinnerListeners()
        configureEditText()
        configureClickListeners()
        configureLiveDataObservers()
    }

    private fun configureClickListeners() {
        binding.avatarImageView.setOnClickListener {
            val bottomDialogFragment = AvatarBottomDialogFragment.newInstance()
            bottomDialogFragment.show(supportFragmentManager, "AvatarBottomDialogFragment")
        }

        binding.saveButton.setOnClickListener {
            if (viewModel.saveCreature()) {
                Toast.makeText(this, getString(R.string.creature_saved), Toast.LENGTH_SHORT).show()
//                finish()
            } else {
                Toast.makeText(this, getString(R.string.error_saving_creature), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configureEditText() {
        binding.nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                /* DO NOTHING */
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.name = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                /* DO NOTHING */
            }
        })
    }

    private fun configureSpinnerListeners() {
        binding.intelligence.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.attributeSelected(AttributeType.INTELLIGENCE, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.strength.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.attributeSelected(AttributeType.STRENGTH, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.endurance.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.attributeSelected(AttributeType.ENDURANCE, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun configureSpinnerAdapters() {
        val layoutRes = android.R.layout.simple_spinner_dropdown_item
        binding.intelligence.adapter = ArrayAdapter(this, layoutRes, INTELLIGENCE)
        binding.strength.adapter = ArrayAdapter(this, layoutRes, STRENGTH)
        binding.endurance.adapter = ArrayAdapter(this, layoutRes, ENDURANCE)
    }

    private fun configureUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.add_creature)
        if (viewModel.drawable != 0) hideTapLabel()
    }

    private fun hideTapLabel() {
        binding.tapLabel.visibility = View.INVISIBLE
    }

    override fun avatarClicked(avatar: Avatar) {
        viewModel.drawableSelected(avatar.drawable)
        hideTapLabel()
    }

    private fun configureLiveDataObservers() {
        viewModel.getCreatureLiveData().observe(this) { creature ->
            creature?.let {
                binding.hitPoints.text = creature.hitPoints.toString()
                binding.avatarImageView.setImageResource(creature.drawable)
                binding.nameEditText.setText(creature.name)
            }
        }
    }
}