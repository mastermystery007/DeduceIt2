package com.mysterybox.deduceit2.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding

@Composable
fun HowToPlayScreen(
    onComplete: () -> Unit,
    isFirstLaunch: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CarbonDark)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isFirstLaunch) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onComplete) {
                    Icon(Icons.Default.Close, "Close how to play", tint = NoirAmber)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 680.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(top = if (isFirstLaunch) 24.dp else 8.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            TutorialHeader()
            TutorialStep(
                title = "1. READ THE CASE FILE",
                body = "Start in the Dossier, then use the Cast tab to review every person, object, location, clue, and witness statement.",
                icon = Icons.Default.Description
            ) { CaseFileVisual() }
            TutorialStep(
                title = "2. MARK THE LOGIC GRID",
                body = "Tap any playable cell to cycle through blank, X, and O. The amber headers are people or locations; green headers are objects.",
                icon = Icons.Default.GridOn
            ) { GridMarkDemo() }
            TutorialStep(
                title = "3. COMPLETE THE MATCHES",
                body = "Each mystery has three people, three objects, and three locations. Every person matches exactly one object and one location. Confirming an O automatically eliminates conflicting cells in that grid section.",
                icon = Icons.Default.Search
            ) { MatchDiagram() }
            TutorialStep(
                title = "4. MAKE YOUR FINAL DEDUCTION",
                body = "Open the Deduce tab and select the responsible person, object, and location. In liar cases, also identify the person who made the false statement.",
                icon = Icons.Default.TaskAlt
            ) { DeductionDemo() }
            TipCard()

            Button(
                onClick = onComplete,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NoirAmber, contentColor = CarbonDark),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = if (isFirstLaunch) "OPEN THE CASE FILES" else "BACK TO CASE FILES",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun TutorialHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(CircleShape)
                .background(NoirAmber.copy(alpha = 0.12f))
                .border(1.dp, NoirAmber.copy(alpha = 0.65f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.HelpOutline, null, tint = NoirAmber, modifier = Modifier.size(34.dp))
        }
        Text(
            "DETECTIVE TRAINING FILE",
            color = MutedGrey,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center
        )
        Text(
            "HOW TO PLAY",
            color = NoirAmber,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "Match every person to one object and one location, then identify who caused the harmless mischief.",
            color = SlateGrey,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        )
    }
}

@Composable
private fun TutorialStep(
    title: String,
    body: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CharcoalSurface),
        border = BorderStroke(1.dp, NoirAmber.copy(alpha = 0.35f))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(NoirAmber.copy(alpha = 0.14f))
                        .border(1.dp, NoirAmber.copy(alpha = 0.35f), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, null, tint = NoirAmber)
                }
                Text(
                    title,
                    color = GridWhite,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
            Text(body, color = SlateGrey, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.fillMaxWidth())
            content()
        }
    }
}

@Composable
private fun CaseFileVisual() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            MiniFileCard("DOSSIER", Icons.Default.Folder, Modifier.weight(1f))
            MiniFileCard("CAST", Icons.Default.Person, Modifier.weight(1f))
        }
        Text(
            "Clues → People → Deductions",
            color = NoirAmber,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun MiniFileCard(label: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SlateCard)
            .border(1.dp, NoirAmber.copy(alpha = 0.55f), RoundedCornerShape(12.dp))
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(icon, null, tint = NoirAmber, modifier = Modifier.size(28.dp))
        Text(label, color = GridWhite, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun GridMarkDemo() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GridStateCell("", "BLANK", "UNKNOWN")
            Text("→", color = NoirAmber, fontSize = 22.sp)
            GridStateCell("✕", "X", "IMPOSSIBLE", BloodRed)
            Text("→", color = NoirAmber, fontSize = 22.sp)
            GridStateCell("⬤", "O", "CONFIRMED", ClueGreen)
        }
        Text(
            "X rules out a pairing. O confirms it.",
            color = MutedGrey,
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            PairingExample("ATRIUM × MIRA", "✕", BloodRed, Modifier.weight(1f))
            PairingExample("ATRIUM × OWEN", "⬤", ClueGreen, Modifier.weight(1f))
        }
    }
}

@Composable
private fun GridStateCell(mark: String, title: String, label: String, markColor: Color = MutedGrey) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(SelectedBox)
                .border(1.dp, NoirAmber.copy(alpha = 0.65f), RoundedCornerShape(9.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(if (mark.isBlank()) "·" else mark, color = markColor, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }
        Text(title, color = GridWhite, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 11.sp)
        Text(label, color = MutedGrey, fontFamily = FontFamily.Monospace, fontSize = 9.sp)
    }
}

@Composable
private fun PairingExample(label: String, mark: String, markColor: Color, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(SlateCard)
            .border(1.dp, NoirAmber.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.size(34.dp).clip(RoundedCornerShape(6.dp)).background(SelectedBox),
            contentAlignment = Alignment.Center
        ) {
            Text(mark, color = markColor, fontWeight = FontWeight.Bold)
        }
        Text(label, color = GridWhite, fontFamily = FontFamily.Monospace, fontSize = 10.sp, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun MatchDiagram() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            RelationshipCard("PERSON", Icons.Default.Person, Modifier.weight(1f))
            RelationshipCard("OBJECT", Icons.Default.Search, Modifier.weight(1f))
            RelationshipCard("LOCATION", Icons.Default.Home, Modifier.weight(1f))
        }
        Text(
            "1 person = 1 object = 1 location",
            color = NoirAmber,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RelationshipCard(label: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SlateCard)
            .border(1.dp, NoirAmber.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(icon, null, tint = NoirAmber)
        Text(label, color = GridWhite, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 10.sp)
    }
}

@Composable
private fun DeductionDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        DemoSelection("RESPONSIBLE PERSON", "PRIYA VALE")
        DemoSelection("OBJECT", "BLUE PAINT TIN")
        DemoSelection("LOCATION", "ORCHESTRA PIT")
    }
}

@Composable
private fun DemoSelection(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(SlateCard)
            .border(1.dp, Color(0x33B0BEC5), RoundedCornerShape(10.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = MutedGrey, fontFamily = FontFamily.Monospace, fontSize = 10.sp)
        Text(value, color = GridWhite, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 11.sp)
    }
}

@Composable
private fun TipCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = NoirAmber.copy(alpha = 0.10f)),
        border = BorderStroke(1.dp, NoirAmber.copy(alpha = 0.45f)),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("DETECTIVE TIP", color = NoirAmber, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            Text(
                "Start with direct clues, confirm the obvious matches, and let each O remove impossible alternatives. Checking a deduction is free; revealing a solution early uses a rewarded ad.",
                color = SlateGrey,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
