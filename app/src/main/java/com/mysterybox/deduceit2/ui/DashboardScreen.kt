package com.mysterybox.deduceit2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mysterybox.deduceit2.data.MysteryCase
import com.mysterybox.deduceit2.viewmodel.DetectiveViewModel

private const val FREE_CASE_COUNT = 2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DetectiveViewModel,
    unlockedCaseIds: Set<Int> = (1..FREE_CASE_COUNT).toSet(),
    onOpenCase: (Int) -> Unit,
    onOpenHowToPlay: () -> Unit,
    onOpenPrivacyPolicy: () -> Unit = {},
    showPrivacyOptions: Boolean = false,
    onOpenPrivacyOptions: () -> Unit = {}
) {
    val completedIds by viewModel.completedCaseIds.collectAsState()
    val cases = viewModel.cases

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "DEDUCE IT 2",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = NoirAmber,
                        letterSpacing = 2.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = CarbonDark),
                actions = {
                    IconButton(onClick = onOpenPrivacyPolicy) {
                        Icon(Icons.Default.Policy, "Privacy policy", tint = NoirAmber)
                    }
                    if (showPrivacyOptions) {
                        IconButton(onClick = onOpenPrivacyOptions) {
                            Icon(Icons.Default.PrivacyTip, "Privacy choices", tint = NoirAmber)
                        }
                    }
                    IconButton(onClick = onOpenHowToPlay) {
                        Icon(Icons.Default.HelpOutline, "How to play", tint = NoirAmber)
                    }
                }
            )
        },
        containerColor = CarbonDark
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Open a file. Read the clues. Solve the mystery.",
                    style = MaterialTheme.typography.bodySmall,
                    color = SlateGrey,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${completedIds.size} / ${cases.size} files solved",
                    style = MaterialTheme.typography.labelSmall,
                    color = MutedGrey,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Cases 1 and 2 are free. Each later case unlocks permanently after one rewarded ad.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MutedGrey,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }

            Text(
                text = "CASE FILES",
                style = MaterialTheme.typography.labelLarge,
                color = MutedGrey,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(cases) { mystery ->
                    val solved = mystery.id in completedIds
                    val locked = mystery.id > FREE_CASE_COUNT && mystery.id !in unlockedCaseIds
                    CaseCard(
                        mystery = mystery,
                        solved = solved,
                        locked = locked,
                        onClick = { onOpenCase(mystery.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CaseCard(
    mystery: MysteryCase,
    solved: Boolean,
    locked: Boolean,
    onClick: () -> Unit
) {
    val difficultyColor = when (mystery.difficulty) {
        "Easy" -> Color(0xFF4CAF50)
        "Medium" -> NoirAmber
        "Hard" -> BloodRed
        else -> GridWhite
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .border(
                width = if (solved || locked) 1.dp else 0.dp,
                color = when {
                    solved -> ClueGreen
                    locked -> NoirAmber.copy(alpha = 0.45f)
                    else -> Color.Transparent
                },
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = CharcoalSurface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Badge(mystery.difficulty.uppercase(), difficultyColor)
                    if (mystery.hasLiar) Badge("LIAR TWIST", BloodRed)
                    if (locked) Badge("WATCH AD", NoirAmber)
                }

                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Case #${mystery.id}: ${mystery.title}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (locked) SlateGrey else GridWhite,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = if (locked) {
                        "Watch one rewarded ad to unlock this case permanently."
                    } else {
                        mystery.story
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedGrey,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(Modifier.width(12.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        when {
                            solved -> ClueGreen.copy(alpha = 0.15f)
                            else -> Color(0x1AFFB300)
                        }
                    )
                    .border(
                        1.dp,
                        if (solved) ClueGreen.copy(alpha = 0.4f) else Color(0x33FFB300),
                        RoundedCornerShape(8.dp)
                    )
            ) {
                Icon(
                    imageVector = when {
                        solved -> Icons.Default.CheckCircle
                        locked -> Icons.Default.Lock
                        else -> Icons.Default.PlayArrow
                    },
                    contentDescription = when {
                        solved -> "Solved case"
                        locked -> "Unlock with rewarded ad"
                        else -> "Open case"
                    },
                    tint = if (solved) ClueGreen else NoirAmber,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun Badge(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.15f))
            .border(1.dp, color.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}
