package com.mysterybox.deduceit2.data

data class Assignment(
    val locationBySuspect: Map<String, String>,
    val weaponBySuspect: Map<String, String>
)

sealed interface Constraint {
    data class SuspectAt(val suspect: String, val location: String) : Constraint
    data class SuspectNotAt(val suspect: String, val location: String) : Constraint
    data class SuspectHas(val suspect: String, val weapon: String) : Constraint
    data class SuspectNotHas(val suspect: String, val weapon: String) : Constraint
    data class WeaponAt(val weapon: String, val location: String) : Constraint
    data class WeaponNotAt(val weapon: String, val location: String) : Constraint
}

fun Constraint.matches(assignment: Assignment): Boolean {
    val locationBySuspect = assignment.locationBySuspect
    val weaponBySuspect = assignment.weaponBySuspect
    fun holderOf(weapon: String): String? = weaponBySuspect.entries.firstOrNull { it.value == weapon }?.key

    return when (this) {
        is Constraint.SuspectAt -> locationBySuspect[suspect] == location
        is Constraint.SuspectNotAt -> locationBySuspect[suspect] != location
        is Constraint.SuspectHas -> weaponBySuspect[suspect] == weapon
        is Constraint.SuspectNotHas -> weaponBySuspect[suspect] != weapon
        is Constraint.WeaponAt -> holderOf(weapon)?.let { locationBySuspect[it] == location } == true
        is Constraint.WeaponNotAt -> holderOf(weapon)?.let { locationBySuspect[it] != location } == true
    }
}

data class Clue(
    val text: String,
    val constraint: Constraint
)

data class WitnessStatement(
    val speaker: String,
    val text: String,
    val constraint: Constraint
)

data class MysteryCase(
    val id: Int,
    val title: String,
    val difficulty: String,
    val story: String,
    val suspects: List<String>,
    val weapons: List<String>,
    val locations: List<String>,
    val suspectDescriptions: Map<String, String>,
    val weaponDescriptions: Map<String, String>,
    val locationDescriptions: Map<String, String>,
    val clues: List<Clue>,
    val statements: List<WitnessStatement> = emptyList(),
    val solutionSuspect: String,
    val solutionWeapon: String,
    val solutionLocation: String,
    val solutionLiar: String? = null,
    val explanation: String
) {
    val hasLiar: Boolean get() = statements.isNotEmpty()
}
