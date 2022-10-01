package com.artishevsky.android.playground.feature.creatures.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.artishevsky.android.playground.feature.creatures.model.Creature

@Database(entities = [(Creature::class)], version = 1)
@TypeConverters(CreatureAttributesConverter::class)
abstract class CreatureDatabase: RoomDatabase() {
    abstract fun creatureDao(): CreatureDao
}