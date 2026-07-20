package com.mysterybox.deduceit2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mysterybox.deduceit2.data.CaseSeeds
import com.mysterybox.deduceit2.data.MysteryCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AccusationResult { Idle, Success, Incorrect }

class DetectiveViewModel(application: Application) : AndroidViewModel(application) {
    val cases: List<MysteryCase> = CaseSeeds.cases

    private val preferences = application.getSharedPreferences("deduce_it_2_progress", 0)

    private val _activeCase = MutableStateFlow<MysteryCase?>(null)
    val activeCase: StateFlow<MysteryCase?> = _activeCase.asStateFlow()

    private val _activeGrid = MutableStateFlow<Map<Pair<Int, Int>, String>>(emptyMap())
    val activeGrid: StateFlow<Map<Pair<Int, Int>, String>> = _activeGrid.asStateFlow()

    private val _checkedClues = MutableStateFlow<Set<Int>>(emptySet())
    val checkedClues: StateFlow<Set<Int>> = _checkedClues.asStateFlow()

    private val _completedCaseIds = MutableStateFlow(loadCompletedIds())
    val completedCaseIds: StateFlow<Set<Int>> = _completedCaseIds.asStateFlow()

    private val _isActiveCaseCompleted = MutableStateFlow(false)
    val isActiveCaseCompleted: StateFlow<Boolean> = _isActiveCaseCompleted.asStateFlow()

    private val _chosenSuspect = MutableStateFlow<String?>(null)
    val chosenSuspect: StateFlow<String?> = _chosenSuspect.asStateFlow()

    private val _chosenWeapon = MutableStateFlow<String?>(null)
    val chosenWeapon: StateFlow<String?> = _chosenWeapon.asStateFlow()

    private val _chosenLocation = MutableStateFlow<String?>(null)
    val chosenLocation: StateFlow<String?> = _chosenLocation.asStateFlow()

    private val _chosenLiar = MutableStateFlow<String?>(null)
    val chosenLiar: StateFlow<String?> = _chosenLiar.asStateFlow()

    private val _accusationResult = MutableStateFlow(AccusationResult.Idle)
    val accusationResult: StateFlow<AccusationResult> = _accusationResult.asStateFlow()

    fun selectCase(caseId: Int?) {
        _activeCase.value = cases.firstOrNull { it.id == caseId }
        _activeGrid.value = caseId?.let(::loadGrid) ?: emptyMap()
        _checkedClues.value = caseId?.let(::loadCheckedClues) ?: emptySet()
        _chosenSuspect.value = null
        _chosenWeapon.value = null
        _chosenLocation.value = null
        _chosenLiar.value = null
        _accusationResult.value = AccusationResult.Idle
        _isActiveCaseCompleted.value = caseId != null && caseId in _completedCaseIds.value
    }

    fun toggleGridCell(row: Int, column: Int) {
        val caseId = _activeCase.value?.id ?: return
        if (row !in 0..5 || column !in 0..5 || (row >= 3 && column >= 3)) return

        val key = row to column
        val current = _activeGrid.value
        val next = when (current[key]) {
            "X" -> "O"
            "O" -> ""
            else -> "X"
        }
        val updated = current.toMutableMap()

        if (next.isEmpty()) updated.remove(key) else updated[key] = next

        if (next == "O") {
            val rowGroup = if (row < 3) 0..2 else 3..5
            val columnGroup = if (column < 3) 0..2 else 3..5

            for (otherColumn in columnGroup) {
                val otherKey = row to otherColumn
                if (otherColumn != column && current[otherKey] != "O") updated[otherKey] = "X"
            }
            for (otherRow in rowGroup) {
                val otherKey = otherRow to column
                if (otherRow != row && current[otherKey] != "O") updated[otherKey] = "X"
            }
        }

        _activeGrid.value = updated
        persistGrid(caseId, updated)
    }

    fun resetGrid() {
        val caseId = _activeCase.value?.id ?: return
        _activeGrid.value = emptyMap()
        persistGrid(caseId, emptyMap())
    }

    fun toggleClueChecked(index: Int) {
        val caseId = _activeCase.value?.id ?: return
        val updated = _checkedClues.value.toMutableSet().apply {
            if (!add(index)) remove(index)
        }
        _checkedClues.value = updated
        persistCheckedClues(caseId, updated)
    }

    fun chooseSuspect(value: String) { _chosenSuspect.value = value; clearResult() }
    fun chooseWeapon(value: String) { _chosenWeapon.value = value; clearResult() }
    fun chooseLocation(value: String) { _chosenLocation.value = value; clearResult() }
    fun chooseLiar(value: String) { _chosenLiar.value = value; clearResult() }

    fun checkAccusation() {
        val case = _activeCase.value ?: return
        val correct = _chosenSuspect.value == case.solutionSuspect &&
            _chosenWeapon.value == case.solutionWeapon &&
            _chosenLocation.value == case.solutionLocation &&
            (!case.hasLiar || _chosenLiar.value == case.solutionLiar)

        _accusationResult.value = if (correct) AccusationResult.Success else AccusationResult.Incorrect
        if (correct) markCompleted(case.id)
    }

    fun clearResult() {
        _accusationResult.value = AccusationResult.Idle
    }

    fun clearCaseCompletion() {
        val id = _activeCase.value?.id ?: return
        _completedCaseIds.value = _completedCaseIds.value - id
        persistCompletedIds()
        _isActiveCaseCompleted.value = false
    }

    private fun markCompleted(id: Int) {
        _completedCaseIds.value = _completedCaseIds.value + id
        persistCompletedIds()
        _isActiveCaseCompleted.value = true
    }

    private fun gridKey(caseId: Int) = "grid_case_$caseId"
    private fun cluesKey(caseId: Int) = "clues_case_$caseId"

    private fun loadGrid(caseId: Int): Map<Pair<Int, Int>, String> = preferences
        .getString(gridKey(caseId), "")
        .orEmpty()
        .split(';')
        .mapNotNull { encoded ->
            val parts = encoded.split(':')
            if (parts.size != 3) return@mapNotNull null
            val row = parts[0].toIntOrNull() ?: return@mapNotNull null
            val column = parts[1].toIntOrNull() ?: return@mapNotNull null
            val mark = parts[2]
            if (row !in 0..5 || column !in 0..5 || mark !in setOf("X", "O")) null
            else (row to column) to mark
        }
        .toMap()

    private fun persistGrid(caseId: Int, grid: Map<Pair<Int, Int>, String>) {
        val encoded = grid.entries
            .sortedWith(compareBy({ it.key.first }, { it.key.second }))
            .joinToString(";") { (cell, mark) -> "${cell.first}:${cell.second}:$mark" }
        preferences.edit().putString(gridKey(caseId), encoded).apply()
    }

    private fun loadCheckedClues(caseId: Int): Set<Int> = preferences
        .getString(cluesKey(caseId), "")
        .orEmpty()
        .split(',')
        .mapNotNull { it.toIntOrNull() }
        .toSet()

    private fun persistCheckedClues(caseId: Int, clues: Set<Int>) {
        preferences.edit()
            .putString(cluesKey(caseId), clues.sorted().joinToString(","))
            .apply()
    }

    private fun loadCompletedIds(): Set<Int> = preferences
        .getString("completed_case_ids", "")
        .orEmpty()
        .split(',')
        .mapNotNull { it.toIntOrNull() }
        .toSet()

    private fun persistCompletedIds() {
        preferences.edit()
            .putString("completed_case_ids", _completedCaseIds.value.sorted().joinToString(","))
            .apply()
    }
}
