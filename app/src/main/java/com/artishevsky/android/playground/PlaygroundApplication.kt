package com.artishevsky.android.playground

import android.app.Application
import androidx.room.Room
import com.artishevsky.android.playground.feature.creatures.model.room.CreatureDatabase

class PlaygroundApplication : Application() {

    companion object {
        lateinit var database: CreatureDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, CreatureDatabase::class.java, "creature_database")
            .build()
    }
}