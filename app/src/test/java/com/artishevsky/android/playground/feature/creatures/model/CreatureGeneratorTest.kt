package com.artishevsky.android.playground.feature.creatures.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CreatureGeneratorTest {
    private lateinit var creatureGenerator: CreatureGenerator

    @Before
    fun setUp() {
        creatureGenerator = CreatureGenerator()
    }

    @Test
    fun testGenerateHitPoints() {
        // arrange
        val attributes = CreatureAttributes(
            intelligence = 7,
            strength = 3,
            endurance = 10
        )
        val name = "Pikachu"
        val expectedCreature = Creature(attributes, 84, name)

        // act
        val generatedCreature = creatureGenerator.generateCreature(attributes, name)

        // assert
        assertEquals(expectedCreature, generatedCreature)
    }
}
