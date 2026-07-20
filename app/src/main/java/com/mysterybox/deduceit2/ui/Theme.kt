package com.mysterybox.deduceit2.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val CarbonDark = Color(0xFF0F0F11)
val CharcoalSurface = Color(0xFF161619)
val SlateCard = Color(0xFF222227)
val NoirAmber = Color(0xFFFFB300)
val BloodRed = Color(0xFFD32F2F)
val MutedGrey = Color(0xFF9EA3B0)
val SlateGrey = Color(0xFFCFD8DC)
val ClueGreen = Color(0xFF388E3C)
val GridWhite = Color(0xFFE0E0E0)
val SelectedBox = Color(0xFF2B2B30)

private val DeduceTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

private val CarbonNoirColorScheme = darkColorScheme(
    primary = NoirAmber,
    secondary = BloodRed,
    tertiary = ClueGreen,
    background = CarbonDark,
    surface = CharcoalSurface,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = GridWhite,
    onSurface = GridWhite,
    surfaceVariant = SlateCard,
    onSurfaceVariant = SlateGrey
)

@Composable
fun DeduceItTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CarbonNoirColorScheme,
        typography = DeduceTypography,
        content = content
    )
}
