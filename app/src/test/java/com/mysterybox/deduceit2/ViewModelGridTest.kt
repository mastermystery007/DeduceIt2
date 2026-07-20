package com.mysterybox.deduceit2

import android.app.Application
import com.mysterybox.deduceit2.viewmodel.DetectiveViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class ViewModelGridTest {
    private lateinit var application: Application

    @Before
    fun clearProgress() {
        application = RuntimeEnvironment.getApplication()
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
