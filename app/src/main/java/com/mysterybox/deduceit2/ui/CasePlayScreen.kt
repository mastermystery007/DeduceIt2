package com.mysterybox.deduceit2.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mysterybox.deduceit2.ads.RewardedAdManager
import com.mysterybox.deduceit2.ads.RewardedAdPurpose
import com.mysterybox.deduceit2.data.MysteryCase
import com.mysterybox.deduceit2.viewmodel.AccusationResult
import com.mysterybox.deduceit2.viewmodel.DetectiveViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CasePlayScreen(
    viewModel: DetectiveViewModel,
    rewardedAdManager: RewardedAdManager,
    onBack: () -> Unit
) {
    val mystery by viewModel.activeCase.collectAsState()
    val gridState by viewModel.activeGrid.collectAsState()
    val checkedClues by viewModel.checkedClues.collectAsState()
    val completed by viewModel.isActiveCaseCompleted.collectAsState()
    val chosenSuspect by viewModel.chosenSuspect.collectAsState()
    val chosenWeapon by viewModel.chosenWeapon.collectAsState()
    val chosenLocation by viewModel.chosenLocation.collectAsState()
    val chosenLiar by viewModel.chosenLiar.collectAsState()
    val result by viewModel.accusationResult.collectAsState()
    val activeCase = mystery ?: return

    var activeTab by remember(activeCase.id) { mutableStateOf("Dossier") }
    var focusedCell by remember(activeCase.id) { mutableStateOf<Pair<Int, Int>?>(null) }
    var showSolutionDialog by remember { mutableStateOf(false) }
    var solutionDialogTitle by remember { mutableStateOf("MYSTERY SOLVED") }
    var revealedLiar by rememberSaveable(activeCase.id) { mutableStateOf<String?>(null) }
    var showLiarDialog by remember { mutableStateOf(false) }
    var showAdUnavailableDialog by remember { mutableStateOf(false) }

    LaunchedEffect(result) {
        if (result == AccusationResult.Success) {
            solutionDialogTitle = "MYSTERY SOLVED"
            showSolutionDialog = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "CASE #${activeCase.id}",
                            style = MaterialTheme.typography.labelSmall,
                            fontFamily = FontFamily.Monospace,
                            color = NoirAmber,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = activeCase.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = GridWhite
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "Back", tint = GridWhite)
                    }
                },
                actions = {
                    if (completed) {
                        IconButton(onClick = viewModel::clearCaseCompletion) {
                            Icon(Icons.Default.LockOpen, "Reset completion status", tint = ClueGreen)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CarbonDark)
            )
        },
        containerColor = CarbonDark
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val difficultyColor = when (activeCase.difficulty) {
                        "Easy" -> ClueGreen
                        "Medium" -> NoirAmber
                        else -> BloodRed
                    }
                    CaseBadge("${activeCase.difficulty.uppercase()} DIFFICULTY", difficultyColor)
                    if (activeCase.hasLiar) CaseBadge("ONE WITNESS MAY BE LYING", BloodRed)
                }

                if (completed) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.CheckCircle, "Mystery solved", tint = ClueGreen, modifier = Modifier.size(20.dp))
                        Text(
                            "SOLVED",
                            color = ClueGreen,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            CaseTabRow(activeTab) { activeTab = it }
            Spacer(Modifier.height(16.dp))

            when (activeTab) {
                "Dossier" -> DossierTab(activeCase, checkedClues, viewModel::toggleClueChecked)
                "Cast" -> CastTab(activeCase)
                "Logic Grid" -> LogicGridTab(
                    mystery = activeCase,
                    gridState = gridState,
                    focusedCell = focusedCell,
                    onCellClick = { row, column ->
                        focusedCell = row to column
                        viewModel.toggleGridCell(row, column)
                    },
                    onResetGrid = viewModel::resetGrid
                )
                "Deduce" -> Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (activeCase.hasLiar && activeCase.solutionLiar != null) {
                        Button(
                            onClick = {
                                if (revealedLiar != null) {
                                    showLiarDialog = true
                                } else {
                                    rewardedAdManager.show(
                                        purpose = RewardedAdPurpose.RevealLiar,
                                        onRewarded = {
                                            revealedLiar = activeCase.solutionLiar
                                            showLiarDialog = true
                                        },
                                        onUnavailable = { showAdUnavailableDialog = true }
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = NoirAmber, contentColor = Color.Black),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Visibility, null)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                if (revealedLiar == null) "WATCH AD TO REVEAL THE LIAR" else "VIEW REVEALED LIAR",
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    DeduceTab(
                        mystery = activeCase,
                        suspect = chosenSuspect,
                        weapon = chosenWeapon,
                        location = chosenLocation,
                        liar = chosenLiar,
                        result = result,
                        onSuspect = viewModel::chooseSuspect,
                        onWeapon = viewModel::chooseWeapon,
                        onLocation = viewModel::chooseLocation,
                        onLiar = viewModel::chooseLiar,
                        onCheck = viewModel::checkAccusation,
                        onReveal = {
                            rewardedAdManager.show(
                                purpose = RewardedAdPurpose.RevealSolution,
                                onRewarded = {
                                    solutionDialogTitle = "SOLUTION REVEALED"
                                    showSolutionDialog = true
                                },
                                onUnavailable = { showAdUnavailableDialog = true }
                            )
                        }
                    )
                }
            }
        }
    }

    if (showSolutionDialog) {
        SolutionDialog(
            title = solutionDialogTitle,
            mystery = activeCase,
            onDismiss = {
                showSolutionDialog = false
                if (result == AccusationResult.Success) onBack()
            }
        )
    }

    if (showLiarDialog && revealedLiar != null) {
        AlertDialog(
            onDismissRequest = { showLiarDialog = false },
            title = {
                Text("LIAR REVEALED", color = NoirAmber, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "$revealedLiar made the one false statement. The remaining witness statements are true.",
                    color = GridWhite
                )
            },
            confirmButton = {
                TextButton(onClick = { showLiarDialog = false }) {
                    Text("CLOSE", color = NoirAmber, fontFamily = FontFamily.Monospace)
                }
            },
            containerColor = CharcoalSurface,
            shape = RoundedCornerShape(16.dp)
        )
    }

    if (showAdUnavailableDialog) {
        AlertDialog(
            onDismissRequest = { showAdUnavailableDialog = false },
            title = {
                Text("AD UNAVAILABLE", color = NoirAmber, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            },
            text = {
                Text("A rewarded ad is not available right now. Check your connection and try again shortly.", color = GridWhite)
            },
            confirmButton = {
                TextButton(onClick = { showAdUnavailableDialog = false }) {
                    Text("CLOSE", color = NoirAmber, fontFamily = FontFamily.Monospace)
                }
            },
            containerColor = CharcoalSurface,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
private fun CaseBadge(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.15f))
            .border(1.dp, color.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, color = color, fontWeight = FontWeight.Bold, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
    }
}

@Composable
private fun CaseTabRow(selected: String, onSelect: (String) -> Unit) {
    val tabs = listOf("Dossier", "Cast", "Logic Grid", "Deduce")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SlateCard)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        tabs.forEach { tab ->
            val active = tab == selected
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (active) NoirAmber else Color.Transparent)
                    .clickable { onSelect(tab) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    tab.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (active) Color.Black else MutedGrey,
                    fontFamily = FontFamily.Monospace,
                    fontSize = if (tab == "Logic Grid") 9.sp else 11.sp,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun DossierTab(
    mystery: MysteryCase,
    checkedClues: Set<Int>,
    onToggleClue: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CharcoalSurface)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionHeader("CASE BRIEF", Icons.Default.Edit)
                Text(mystery.story, style = MaterialTheme.typography.bodyMedium, color = GridWhite, lineHeight = 20.sp)
            }
        }

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CharcoalSurface)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SectionHeader("COLLECTED EVIDENCE", Icons.Default.FindInPage)
                Divider(color = Color(0x33B0BEC5))
                if (mystery.hasLiar) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(BloodRed.copy(alpha = 0.12f))
                            .border(1.dp, BloodRed.copy(alpha = 0.45f), RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            "This file includes one false witness statement. Check the Cast tab before deducing.",
                            style = MaterialTheme.typography.bodySmall,
                            color = BloodRed,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                mystery.clues.forEachIndexed { index, clue ->
                    val checked = index in checkedClues
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { onToggleClue(index) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = NoirAmber,
                                uncheckedColor = MutedGrey,
                                checkmarkColor = Color.Black
                            )
                        )
                        Text(
                            clue.text,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (checked) MutedGrey.copy(alpha = 0.55f) else SlateGrey,
                            lineHeight = 18.sp,
                            textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CastTab(mystery: MysteryCase) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CastSectionCard(
            title = "PEOPLE",
            icon = Icons.Default.Person,
            items = mystery.suspects.map { it to mystery.suspectDescriptions[it].orEmpty() }
        )
        CastSectionCard(
            title = "OBJECTS",
            icon = Icons.Default.Build,
            items = mystery.weapons.map { it to mystery.weaponDescriptions[it].orEmpty() }
        )
        CastSectionCard(
            title = "LOCATIONS",
            icon = Icons.Default.Place,
            items = mystery.locations.map { it to mystery.locationDescriptions[it].orEmpty() }
        )
        if (mystery.statements.isNotEmpty()) WitnessStatementsCard(mystery)
    }
}

@Composable
private fun CastSectionCard(title: String, icon: ImageVector, items: List<Pair<String, String>>) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CharcoalSurface)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            SectionHeader(title, icon)
            Divider(color = Color(0x33B0BEC5))
            items.forEach { (name, description) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SlateCard.copy(alpha = 0.45f))
                        .border(1.dp, Color(0x22B0BEC5), RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = GridWhite)
                    if (description.isNotBlank()) {
                        Text(description, style = MaterialTheme.typography.bodySmall, color = SlateGrey, lineHeight = 17.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun WitnessStatementsCard(mystery: MysteryCase) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CharcoalSurface)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            SectionHeader("WITNESS STATEMENTS", Icons.Default.RecordVoiceOver)
            Divider(color = Color(0x33B0BEC5))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(BloodRed.copy(alpha = 0.12f))
                    .border(1.dp, BloodRed.copy(alpha = 0.45f), RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Text(
                    "Exactly one person is lying. Use these statements together with the physical clues.",
                    style = MaterialTheme.typography.bodySmall,
                    color = BloodRed,
                    fontWeight = FontWeight.SemiBold
                )
            }
            mystery.statements.forEach { statement ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SlateCard.copy(alpha = 0.55f))
                        .border(1.dp, Color(0x22B0BEC5), RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        statement.speaker,
                        style = MaterialTheme.typography.labelSmall,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = NoirAmber
                    )
                    Text("“${statement.text}”", style = MaterialTheme.typography.bodyMedium, color = SlateGrey, lineHeight = 18.sp)
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(icon, "$title icon", tint = NoirAmber)
        Text(
            title,
            style = MaterialTheme.typography.labelLarge,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            color = NoirAmber
        )
    }
}

@Composable
private fun LogicGridTab(
    mystery: MysteryCase,
    gridState: Map<Pair<Int, Int>, String>,
    focusedCell: Pair<Int, Int>?,
    onCellClick: (Int, Int) -> Unit,
    onResetGrid: () -> Unit
) {
    val scrollState = rememberScrollState()
    var fitToScreen by remember { mutableStateOf(true) }
    val rowHeaderWidth = if (fitToScreen) 92.dp else 140.dp
    val cellWidth = if (fitToScreen) 52.dp else 120.dp
    val cellHeight = if (fitToScreen) 48.dp else 56.dp
    val headerFontSize = if (fitToScreen) 7.sp else 9.sp
    val markFontSize = if (fitToScreen) 15.sp else 18.sp
    val columnHeaders = mystery.suspects + mystery.weapons
    val rowHeaders = mystery.locations + mystery.weapons

    fun playable(row: Int, column: Int): Boolean = !(row >= mystery.locations.size && column >= mystery.suspects.size)

    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            colors = CardDefaults.cardColors(containerColor = CharcoalSurface)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0x1AFFB300))
                ) {
                    Icon(Icons.Default.Info, "Cell inspector", tint = NoirAmber, modifier = Modifier.size(20.dp))
                }
                Column {
                    Text("CELL INSPECTOR", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace, color = MutedGrey)
                    if (focusedCell != null && playable(focusedCell.first, focusedCell.second)) {
                        val row = focusedCell.first
                        val column = focusedCell.second
                        val rowLabel = if (row < mystery.locations.size) {
                            "Location: ${mystery.locations[row]}"
                        } else {
                            "Object: ${mystery.weapons[row - mystery.locations.size]}"
                        }
                        val columnLabel = if (column < mystery.suspects.size) {
                            "Person: ${mystery.suspects[column]}"
                        } else {
                            "Object: ${mystery.weapons[column - mystery.suspects.size]}"
                        }
                        val mark = gridState[focusedCell].orEmpty()
                        Text("$rowLabel  ✕  $columnLabel", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = GridWhite)
                        Text(
                            "Current State: " + when (mark) {
                                "O" -> "CONFIRMED (O)"
                                "X" -> "ELIMINATED (X)"
                                else -> "Unknown (Tap to toggle)"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            fontFamily = FontFamily.Monospace,
                            color = when (mark) {
                                "O" -> ClueGreen
                                "X" -> BloodRed
                                else -> MutedGrey
                            }
                        )
                    } else {
                        Text("Tap any active grid cell below to investigate relationships", style = MaterialTheme.typography.bodySmall, color = SlateGrey)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(2.dp, Color(0x33B0BEC5), RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(CharcoalSurface)
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .size(width = rowHeaderWidth, height = cellHeight)
                        .border(0.5.dp, Color(0x33B0BEC5))
                        .background(SlateCard)
                )
                Row(modifier = Modifier.horizontalScroll(scrollState)) {
                    columnHeaders.forEachIndexed { index, header ->
                        GridHeaderCell(
                            text = header,
                            isObject = index >= mystery.suspects.size,
                            width = cellWidth,
                            height = cellHeight,
                            fontSize = headerFontSize,
                            alignStart = false
                        )
                    }
                }
            }

            rowHeaders.forEachIndexed { row, label ->
                Row {
                    GridHeaderCell(
                        text = label,
                        isObject = row >= mystery.locations.size,
                        width = rowHeaderWidth,
                        height = cellHeight,
                        fontSize = headerFontSize,
                        alignStart = true
                    )
                    Row(modifier = Modifier.horizontalScroll(scrollState)) {
                        columnHeaders.forEachIndexed { column, _ ->
                            if (!playable(row, column)) {
                                DisabledGridCell(cellWidth, cellHeight)
                            } else {
                                ActiveGridCell(
                                    mark = gridState[row to column].orEmpty(),
                                    focused = focusedCell == row to column,
                                    width = cellWidth,
                                    height = cellHeight,
                                    markFontSize = markFontSize,
                                    onClick = { onCellClick(row, column) }
                                )
                            }
                        }
                    }
                }
            }
        }

        Text(
            "Toggles: Unknown ➔ ✕ ➔ ⬤",
            style = MaterialTheme.typography.labelSmall,
            color = MutedGrey,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { fitToScreen = !fitToScreen },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, NoirAmber),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = NoirAmber)
            ) {
                Text(
                    if (fitToScreen) "LARGE VIEW" else "FIT TO SCREEN",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
            OutlinedButton(
                onClick = onResetGrid,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, BloodRed),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = BloodRed)
            ) {
                Text("CLEAR GRID", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 10.sp)
            }
        }
    }
}

@Composable
private fun GridHeaderCell(
    text: String,
    isObject: Boolean,
    width: Dp,
    height: Dp,
    fontSize: androidx.compose.ui.unit.TextUnit,
    alignStart: Boolean
) {
    Box(
        modifier = Modifier
            .size(width = width, height = height)
            .border(0.5.dp, Color(0x33B0BEC5))
            .background(if (isObject) Color(0x22388E3C) else Color(0x22FFB300)),
        contentAlignment = if (alignStart) Alignment.CenterStart else Alignment.Center
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = GridWhite,
            fontFamily = FontFamily.Monospace,
            fontSize = fontSize,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = if (alignStart) TextAlign.Start else TextAlign.Center,
            modifier = Modifier.padding(horizontal = if (alignStart) 8.dp else 6.dp)
        )
    }
}

@Composable
private fun DisabledGridCell(width: Dp, height: Dp) {
    Box(
        modifier = Modifier
            .size(width = width, height = height)
            .border(0.5.dp, Color(0x33B0BEC5))
            .background(MutedGrey.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
    ) {
        Text("–", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MutedGrey.copy(alpha = 0.45f), fontFamily = FontFamily.Monospace)
    }
}

@Composable
private fun ActiveGridCell(
    mark: String,
    focused: Boolean,
    width: Dp,
    height: Dp,
    markFontSize: androidx.compose.ui.unit.TextUnit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = width, height = height)
            .border(if (focused) 1.5.dp else 0.5.dp, if (focused) NoirAmber else Color(0x33B0BEC5))
            .background(if (focused) SelectedBox else CharcoalSurface)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        when (mark) {
            "X" -> Text("✕", color = BloodRed, fontWeight = FontWeight.Bold, fontSize = markFontSize)
            "O" -> Text("⬤", color = ClueGreen, fontWeight = FontWeight.Bold, fontSize = markFontSize)
        }
    }
}

@Composable
private fun DeduceTab(
    mystery: MysteryCase,
    suspect: String?,
    weapon: String?,
    location: String?,
    liar: String?,
    result: AccusationResult,
    onSuspect: (String) -> Unit,
    onWeapon: (String) -> Unit,
    onLocation: (String) -> Unit,
    onLiar: (String) -> Unit,
    onCheck: () -> Unit,
    onReveal: () -> Unit
) {
    val ready = suspect != null && weapon != null && location != null && (!mystery.hasLiar || liar != null)
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("FINAL DEDUCTION", color = NoirAmber, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        SelectionMenu("Responsible person", suspect, mystery.suspects, onSuspect)
        SelectionMenu("Object involved", weapon, mystery.weapons, onWeapon)
        SelectionMenu("Location", location, mystery.locations, onLocation)
        if (mystery.hasLiar) SelectionMenu("False witness", liar, mystery.suspects, onLiar)

        if (result == AccusationResult.Incorrect) {
            Card(colors = CardDefaults.cardColors(containerColor = BloodRed.copy(alpha = 0.14f))) {
                Text(
                    "That deduction conflicts with the evidence. Recheck the grid and witness statements.",
                    color = BloodRed,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        Button(
            onClick = onCheck,
            enabled = ready,
            colors = ButtonDefaults.buttonColors(containerColor = NoirAmber, contentColor = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("CHECK DEDUCTION", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        }
        OutlinedButton(onClick = onReveal, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
            Text("WATCH AD TO REVEAL SOLUTION", color = BloodRed, fontFamily = FontFamily.Monospace)
        }
        Text(
            "Checking a deduction is free. Revealing the liar or complete solution early requires a rewarded ad.",
            color = MutedGrey,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun SelectionMenu(
    label: String,
    selected: String?,
    options: List<String>,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Text(label.uppercase(), color = MutedGrey, fontFamily = FontFamily.Monospace, fontSize = 10.sp)
        Spacer(Modifier.height(4.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text(selected ?: "Select $label", color = if (selected == null) MutedGrey else GridWhite)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SolutionDialog(title: String, mystery: MysteryCase, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(title, color = NoirAmber, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        },
        text = {
            Column(
                modifier = Modifier.heightIn(max = 520.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Your deduction matches the evidence.", style = MaterialTheme.typography.bodyMedium, color = GridWhite)
                Card(colors = CardDefaults.cardColors(containerColor = SlateCard), shape = RoundedCornerShape(8.dp)) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        SolutionRow("Responsible:", mystery.solutionSuspect)
                        SolutionRow("Object:", mystery.solutionWeapon)
                        SolutionRow("Location:", mystery.solutionLocation)
                        if (mystery.solutionLiar != null) SolutionRow("False witness:", mystery.solutionLiar)
                    }
                }
                Divider(color = Color(0x33B0BEC5))
                Text("MYSTERY EXPLANATION", style = MaterialTheme.typography.labelMedium, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, color = NoirAmber)
                Text(mystery.explanation, style = MaterialTheme.typography.bodyMedium, color = SlateGrey, lineHeight = 20.sp)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("CLOSE FILE", color = NoirAmber, fontFamily = FontFamily.Monospace)
            }
        },
        containerColor = CharcoalSurface,
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun SolutionRow(label: String, value: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(label, color = NoirAmber, style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace, modifier = Modifier.width(105.dp))
        Text(value, color = GridWhite, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
    }
}
