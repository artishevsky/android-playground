package com.artishevsky.android.playground.feature.creatures.viewmodel

import androidx.lifecycle.ViewModel
import com.artishevsky.android.playground.feature.creatures.model.CreatureRepository
import com.artishevsky.android.playground.feature.creatures.model.room.RoomRepository

class AllCreaturesViewModel(
    private val repository: CreatureRepository = RoomRepository()
) : ViewModel() {

    private val allCreaturesLiveData =  repository.getAllCreatures()

    fun getAllCreaturesLiveData() = allCreaturesLiveData

    fun clearAllCreatures() = repository.clearAllCreatures()
}