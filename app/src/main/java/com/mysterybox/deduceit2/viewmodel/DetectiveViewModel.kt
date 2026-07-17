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
        _activeGrid.value = emptyMap()
        _checkedClues.value = emptySet()
        _chosenSuspect.value = null
        _chosenWeapon.value = null
        _chosenLocation.value = null
        _chosenLiar.value = null
        _accusationResult.value = AccusationResult.Idle
        _isActiveCaseCompleted.value = caseId != null && caseId in _completedCaseIds.value
    }

    fun toggleGridCell(row: Int, column: Int) {
        val key = row to column
        val next = when (_activeGrid.value[key]) {
            "X" -> "O"
            "O" -> ""
            else -> "X"
        }
        _activeGrid.value = _activeGrid.value.toMutableMap().apply {
            if (next.isEmpty()) remove(key) else put(key, next)
        }
    }

    fun resetGrid() {
        _activeGrid.value = emptyMap()
    }

    fun toggleClueChecked(index: Int) {
        _checkedClues.value = _checkedClues.value.toMutableSet().apply {
            if (!add(index)) remove(index)
        }
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
