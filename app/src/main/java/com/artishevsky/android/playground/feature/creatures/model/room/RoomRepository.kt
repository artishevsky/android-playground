package com.artishevsky.android.playground.feature.creatures.model.room

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.artishevsky.android.playground.PlaygroundApplication
import com.artishevsky.android.playground.feature.creatures.model.Creature
import com.artishevsky.android.playground.feature.creatures.model.CreatureRepository

class RoomRepository : CreatureRepository {

    private val creatureDao: CreatureDao = PlaygroundApplication.database.creatureDao()

    private val allCreatures: LiveData<List<Creature>> = creatureDao.getAllCreatures()

    private class InsertAsyncTask internal constructor(private val dao: CreatureDao) : AsyncTask<Creature, Void, Void>() {
        override fun doInBackground(vararg params: Creature): Void? {
            dao.insert(params[0])
            return null
        }
    }

    private class DeleteAsyncTask internal constructor(private val dao: CreatureDao) : AsyncTask<Creature, Void, Void>() {
        override fun doInBackground(vararg params: Creature): Void? {
            dao.clearCreatures(*params)
            return null
        }
    }

    override fun saveCreature(creature: Creature) {
        InsertAsyncTask(creatureDao).execute(creature)
    }

    override fun getAllCreatures(): LiveData<List<Creature>> {
        return allCreatures
    }

    override fun clearAllCreatures() {
        val creatureArray = allCreatures.value?.toTypedArray()
        if (creatureArray != null) {
            DeleteAsyncTask(creatureDao).execute(*creatureArray)
        }
    }
}