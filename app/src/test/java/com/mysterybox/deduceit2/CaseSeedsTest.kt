package com.mysterybox.deduceit2

import com.mysterybox.deduceit2.data.Assignment
import com.mysterybox.deduceit2.data.CaseSeeds
import com.mysterybox.deduceit2.data.MysteryCase
import com.mysterybox.deduceit2.data.matches
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CaseSeedsTest {
    @Test
    fun everyCaseHasExactlyOneLogicalSolution() {
        CaseSeeds.cases.forEach { mystery ->
            val solutions = solve(mystery)
            assertEquals("Case ${mystery.id} must have one solution", 1, solutions.size)
            val solution = solutions.single()
            assertEquals(mystery.solutionLocation, solution.locationBySuspect[mystery.solutionSuspect])
            assertEquals(mystery.solutionWeapon, solution.weaponBySuspect[mystery.solutionSuspect])
        }
    }

    @Test
    fun catalogueAndReferencesAreValid() {
        assertEquals(10, CaseSeeds.cases.size)
        assertEquals(CaseSeeds.cases.size, CaseSeeds.cases.map { it.id }.toSet().size)
        CaseSeeds.cases.forEach { mystery ->
            assertEquals(3, mystery.suspects.size)
            assertEquals(3, mystery.weapons.size)
            assertEquals(3, mystery.locations.size)
            assertEquals(mystery.suspects.toSet(), mystery.suspectDescriptions.keys)
            assertEquals(mystery.weapons.toSet(), mystery.weaponDescriptions.keys)
            assertEquals(mystery.locations.toSet(), mystery.locationDescriptions.keys)
            assertTrue(mystery.solutionSuspect in mystery.suspects)
            assertTrue(mystery.solutionWeapon in mystery.weapons)
            assertTrue(mystery.solutionLocation in mystery.locations)
            if (mystery.hasLiar) assertTrue(mystery.solutionLiar in mystery.suspects)
            else assertEquals(null, mystery.solutionLiar)
        }
    }

    @Test
    fun everyCaseIsWrittenAsHarmlessMischief() {
        val violentTerms = listOf(
            "murder", "killed", "killer", "dead", "attacked", "attack",
            "blood", "unconscious", "struck", "poisoned"
        )
        CaseSeeds.cases.forEach { mystery ->
            val playerFacingText = buildString {
                appendLine(mystery.title)
                appendLine(mystery.story)
                appendLine(mystery.explanation)
                mystery.suspectDescriptions.values.forEach(::appendLine)
                mystery.weaponDescriptions.values.forEach(::appendLine)
                mystery.locationDescriptions.values.forEach(::appendLine)
                mystery.clues.forEach { appendLine(it.text) }
                mystery.statements.forEach { appendLine(it.text) }
            }.lowercase()
            violentTerms.forEach { term ->
                assertFalse("Case ${mystery.id} contains violent term '$term'", term in playerFacingText)
            }
        }
    }

    @Test
    fun solutionObjectAndLocationAreNarrativelyReferenced() {
        CaseSeeds.cases.forEach { mystery ->
            val narrative = (mystery.story + " " + mystery.explanation).lowercase()
            assertTrue(
                "Case ${mystery.id} narrative should reference its solution object",
                mystery.solutionWeapon.lowercase() in narrative
            )
            assertTrue(
                "Case ${mystery.id} narrative should reference its solution location",
                mystery.solutionLocation.lowercase() in narrative
            )
        }
    }

    private fun solve(mystery: MysteryCase): List<Assignment> {
        val assignments = mutableListOf<Assignment>()
        permutations(mystery.locations).forEach { locationOrder ->
            val locations = mystery.suspects.zip(locationOrder).toMap()
            permutations(mystery.weapons).forEach { weaponOrder ->
                val assignment = Assignment(
                    locationBySuspect = locations,
                    weaponBySuspect = mystery.suspects.zip(weaponOrder).toMap()
                )
                if (!mystery.clues.all { it.constraint.matches(assignment) }) return@forEach
                if (mystery.hasLiar) {
                    val truthValues = mystery.statements.map { it.constraint.matches(assignment) }
                    if (truthValues.count { !it } != 1) return@forEach
                    val liar = mystery.statements[truthValues.indexOf(false)].speaker
                    if (liar != mystery.solutionLiar) return@forEach
                }
                assignments += assignment
            }
        }
        return assignments
    }

    private fun <T> permutations(values: List<T>): List<List<T>> = when (values.size) {
        0 -> listOf(emptyList())
        1 -> listOf(values)
        else -> values.flatMap { head ->
            permutations(values - head).map { tail -> listOf(head) + tail }
        }
    }
}
