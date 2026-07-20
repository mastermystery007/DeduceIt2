package com.mysterybox.deduceit2

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.mysterybox.deduceit2.viewmodel.DetectiveViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ViewModelGridTest {
    private lateinit var application: Application

    @Before
    fun clearProgress() {
        application = ApplicationProvider.getApplicationContext()
        application.getSharedPreferences("deduce_it_2_progress", 0).edit().clear().commit()
    }

    @Test
    fun confirmingMatchEliminatesConflictingCellsAndPersists() {
        val first = DetectiveViewModel(application)
        first.selectCase(1)
        first.toggleGridCell(0, 0) // blank -> X
        first.toggleGridCell(0, 0) // X -> O

        assertEquals("O", first.activeGrid.value[0 to 0])
        assertEquals("X", first.activeGrid.value[0 to 1])
        assertEquals("X", first.activeGrid.value[0 to 2])
        assertEquals("X", first.activeGrid.value[1 to 0])
        assertEquals("X", first.activeGrid.value[2 to 0])

        val restored = DetectiveViewModel(application)
        restored.selectCase(1)
        assertEquals(first.activeGrid.value, restored.activeGrid.value)
    }
}
