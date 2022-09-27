package com.artishevsky.android.playground.feature.creatures.model

data class Creature(
    val attributes: CreatureAttributes = CreatureAttributes(),
    val hitPoints: Int = 0,
    val name: String = "",
    val drawable: Int = 0
)
