package com.mysterybox.deduceit2.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val CarbonDark = Color(0xFF0B0D10)
val CharcoalSurface = Color(0xFF15181D)
val SlateCard = Color(0xFF20242B)
val NoirAmber = Color(0xFFFFB300)
val GridWhite = Color(0xFFF4F4F4)
val SlateGrey = Color(0xFFB0BEC5)
val MutedGrey = Color(0xFF7C8791)
val BloodRed = Color(0xFFD64B4B)
val ClueGreen = Color(0xFF8BC34A)

private val DeduceColorScheme = darkColorScheme(
    primary = NoirAmber,
    onPrimary = Color.Black,
    secondary = ClueGreen,
    background = CarbonDark,
    onBackground = GridWhite,
    surface = CharcoalSurface,
    onSurface = GridWhite,
    error = BloodRed
)

@Composable
fun DeduceItTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DeduceColorScheme,
        content = content
    )
}
