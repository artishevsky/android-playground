package com.artishevsky.android.playground.feature.creatures.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.artishevsky.android.playground.feature.creatures.model.Creature

@Dao
interface CreatureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(creature: Creature)

    @Delete
    fun clearCreatures(vararg creature: Creature)

    @Query("SELECT * FROM creature_table ORDER BY name ASC")
    fun getAllCreatures(): LiveData<List<Creature>>

}