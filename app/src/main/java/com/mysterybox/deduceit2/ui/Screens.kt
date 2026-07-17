package com.mysterybox.deduceit2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.setValue
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
import com.mysterybox.deduceit2.ads.RewardedAdManager
import com.mysterybox.deduceit2.ads.RewardedAdPurpose
import com.mysterybox.deduceit2.data.MysteryCase
import com.mysterybox.deduceit2.viewmodel.AccusationResult
import com.mysterybox.deduceit2.viewmodel.DetectiveViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DetectiveViewModel,
    onOpenCase: (Int) -> Unit,
    onOpenHowToPlay: () -> Unit
) {
    val completedIds by viewModel.completedCaseIds.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "DEDUCE IT 2",
                        color = NoirAmber,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                },
                actions = {
                    IconButton(onClick = onOpenHowToPlay) {
                        Icon(Icons.Default.HelpOutline, "How to play", tint = NoirAmber)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = CarbonDark)
            )
        },
        containerColor = CarbonDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "NEW FILES. NEW SUSPECTS. NEW LIES.",
                color = SlateGrey,
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
            )
            Text(
                text = "${completedIds.size} / ${viewModel.cases.size} cases solved",
                color = MutedGrey,
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 14.dp)
            )
            Text(
                text = "CASE FILES",
                color = MutedGrey,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(viewModel.cases) { mystery ->
                    CaseCard(
                        mystery = mystery,
                        solved = mystery.id in completedIds,
                        onClick = { onOpenCase(mystery.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CaseCard(mystery: MysteryCase, solved: Boolean, onClick: () -> Unit) {
    val difficultyColor = when (mystery.difficulty) {
        "Easy" -> ClueGreen
        "Medium" -> NoirAmber
        else -> BloodRed
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .border(
                width = if (solved) 1.dp else 0.dp,
                color = if (solved) ClueGreen else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = CharcoalSurface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    Badge(mystery.difficulty.uppercase(), difficultyColor)
                    if (mystery.hasLiar) Badge("ONE LIAR", BloodRed)
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Case #${mystery.id}: ${mystery.title}",
                    color = GridWhite,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = mystery.story,
                    color = MutedGrey,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.width(12.dp))
            Icon(
                imageVector = if (solved) Icons.Default.CheckCircle else Icons.Default.PlayArrow,
                contentDescription = if (solved) "Solved" else "Open",
                tint = if (solved) ClueGreen else NoirAmber,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun Badge(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.14f))
            .border(1.dp, color.copy(alpha = 0.45f), RoundedCornerShape(4.dp))
            .padding(horizontal = 7.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            color = color,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 9.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HowToPlayScreen(onDone: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "HOW TO PLAY",
                        color = NoirAmber,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = CarbonDark)
            )
        },
        containerColor = CarbonDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InstructionCard("1. READ THE FILE", "Study the story, cast descriptions, physical clues, and witness statements.")
            InstructionCard("2. MARK THE GRID", "Tap a cell to cycle through X, O, and blank. Use X to exclude a pairing and O to confirm it.")
            InstructionCard("3. FIND THE COMPLETE MATCH", "Every suspect has exactly one weapon and one location. Every weapon and location is used once.")
            InstructionCard("4. HANDLE LIAR CASES", "When a case says exactly one witness is lying, test all three statements and identify the false witness.")
            InstructionCard("5. MAKE THE ACCUSATION", "Choose the culprit, weapon, location, and liar when required. A correct accusation closes the case.")
            Button(
                onClick = onDone,
                colors = ButtonDefaults.buttonColors(containerColor = NoirAmber, contentColor = Color.Black),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("OPEN CASE FILES", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun InstructionCard(title: String, body: String) {
    Card(colors = CardDefaults.cardColors(containerColor = CharcoalSurface)) {
        Column(Modifier.fillMaxWidth().padding(14.dp)) {
            Text(title, color = NoirAmber, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text(body, color = SlateGrey, lineHeight = 20.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CasePlayScreen(
    viewModel: DetectiveViewModel,
    rewardedAdManager: RewardedAdManager,
    onBack: () -> Unit
) {
    val mystery by viewModel.activeCase.collectAsState()
    val grid by viewModel.activeGrid.collectAsState()
    val checkedClues by viewModel.checkedClues.collectAsState()
    val completed by viewModel.isActiveCaseCompleted.collectAsState()
    val chosenSuspect by viewModel.chosenSuspect.collectAsState()
    val chosenWeapon by viewModel.chosenWeapon.collectAsState()
    val chosenLocation by viewModel.chosenLocation.collectAsState()
    val chosenLiar by viewModel.chosenLiar.collectAsState()
    val result by viewModel.accusationResult.collectAsState()
    val activeCase = mystery ?: return

    var tab by remember(activeCase.id) { mutableStateOf("CAST") }
    var showSolvedDialog by remember { mutableStateOf(false) }
    var showRevealDialog by remember { mutableStateOf(false) }
    var adNotice by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(result) {
        if (result == AccusationResult.Success) showSolvedDialog = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "CASE #${activeCase.id}",
                            color = NoirAmber,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp
                        )
                        Text(activeCase.title, color = GridWhite, fontWeight = FontWeight.Bold)
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
                            Icon(Icons.Default.LockOpen, "Mark unsolved", tint = ClueGreen)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CarbonDark)
            )
        },
        containerColor = CarbonDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    Badge(activeCase.difficulty.uppercase(), when (activeCase.difficulty) {
                        "Easy" -> ClueGreen
                        "Medium" -> NoirAmber
                        else -> BloodRed
                    })
                    if (activeCase.hasLiar) Badge("ONE LIAR", BloodRed)
                }
                if (completed) Text("SOLVED", color = ClueGreen, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(14.dp))
            TabRow(selected = tab, onSelect = { tab = it })
            Spacer(Modifier.height(16.dp))

            when (tab) {
                "CAST" -> CastTab(activeCase)
                "DOSSIER" -> DossierTab(activeCase, checkedClues, viewModel::toggleClueChecked)
                "GRID" -> LogicGridTab(activeCase, grid, viewModel::toggleGridCell, viewModel::resetGrid)
                "DEDUCE" -> DeduceTab(
                    mystery = activeCase,
                    suspect = chosenSuspect,
                    weapon = chosenWeapon,
                    location = chosenLocation,
                    liar = chosenLiar,
                    result = result,
                    adNotice = adNotice,
                    onSuspect = viewModel::chooseSuspect,
                    onWeapon = viewModel::chooseWeapon,
                    onLocation = viewModel::chooseLocation,
                    onLiar = viewModel::chooseLiar,
                    onCheck = {
                        rewardedAdManager.show(
                            purpose = RewardedAdPurpose.CheckAnswer,
                            onRewarded = viewModel::checkAccusation,
                            onUnavailable = {
                                adNotice = "Rewarded ad was unavailable, so the answer was checked without one."
                                viewModel.checkAccusation()
                            }
                        )
                    },
                    onReveal = {
                        rewardedAdManager.show(
                            purpose = RewardedAdPurpose.RevealSolution,
                            onRewarded = { showRevealDialog = true },
                            onUnavailable = {
                                adNotice = "Rewarded ad was unavailable, so the solution was revealed without one."
                                showRevealDialog = true
                            }
                        )
                    }
                )
            }
        }
    }

    if (showSolvedDialog) {
        SolutionDialog(
            title = "CASE CLOSED",
            mystery = activeCase,
            onDismiss = {
                showSolvedDialog = false
                onBack()
            }
        )
    }

    if (showRevealDialog) {
        SolutionDialog(
            title = "SOLUTION REVEALED",
            mystery = activeCase,
            onDismiss = { showRevealDialog = false }
        )
    }
}

@Composable
private fun TabRow(selected: String, onSelect: (String) -> Unit) {
    val tabs = listOf("CAST", "DOSSIER", "GRID", "DEDUCE")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SlateCard)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        tabs.forEach { tab ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (selected == tab) NoirAmber else Color.Transparent)
                    .clickable { onSelect(tab) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    tab,
                    color = if (selected == tab) Color.Black else MutedGrey,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
private fun CastTab(mystery: MysteryCase) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        CastSection("SUSPECTS", mystery.suspects, mystery.suspectDescriptions)
        CastSection("OBJECTS", mystery.weapons, mystery.weaponDescriptions)
        CastSection("LOCATIONS", mystery.locations, mystery.locationDescriptions)
    }
}

@Composable
private fun CastSection(title: String, items: List<String>, descriptions: Map<String, String>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, color = NoirAmber, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        items.forEach { item ->
            Card(colors = CardDefaults.cardColors(containerColor = CharcoalSurface)) {
                Column(Modifier.fillMaxWidth().padding(12.dp)) {
                    Text(item, color = GridWhite, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                    Text(descriptions[item].orEmpty(), color = SlateGrey, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
private fun DossierTab(
    mystery: MysteryCase,
    checked: Set<Int>,
    onToggle: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = CharcoalSurface)) {
            Column(Modifier.fillMaxWidth().padding(14.dp)) {
                Text("INCIDENT REPORT", color = NoirAmber, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(7.dp))
                Text(mystery.story, color = SlateGrey, lineHeight = 20.sp)
            }
        }
        Text("VERIFIED CLUES", color = NoirAmber, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        mystery.clues.forEachIndexed { index, clue ->
            Card(colors = CardDefaults.cardColors(containerColor = SlateCard)) {
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { onToggle(index) }.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = index in checked,
                        onCheckedChange = { onToggle(index) },
                        colors = CheckboxDefaults.colors(checkedColor = ClueGreen)
                    )
                    Text(clue.text, color = if (index in checked) MutedGrey else GridWhite, fontSize = 13.sp)
                }
            }
        }
        if (mystery.hasLiar) {
            Text("WITNESS STATEMENTS", color = BloodRed, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            Text("Exactly one statement below is false.", color = MutedGrey, fontSize = 12.sp)
            mystery.statements.forEach { statement ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = CharcoalSurface),
                    modifier = Modifier.border(1.dp, BloodRed.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                ) {
                    Column(Modifier.fillMaxWidth().padding(12.dp)) {
                        Text(statement.speaker, color = BloodRed, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))
                        Text("“${statement.text}”", color = GridWhite)
                    }
                }
            }
        }
    }
}

@Composable
private fun LogicGridTab(
    mystery: MysteryCase,
    grid: Map<Pair<Int, Int>, String>,
    onCellClick: (Int, Int) -> Unit,
    onReset: () -> Unit
) {
    val columns = mystery.suspects + mystery.weapons
    val rows = mystery.locations + mystery.weapons
    val scroll = rememberScrollState()

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("DEDUCTION GRID", color = NoirAmber, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                Text("Tap: blank → X → O", color = MutedGrey, fontSize = 11.sp)
            }
            OutlinedButton(onClick = onReset) {
                Icon(Icons.Default.Refresh, "Reset", tint = NoirAmber, modifier = Modifier.size(17.dp))
                Spacer(Modifier.width(5.dp))
                Text("RESET", color = NoirAmber, fontSize = 10.sp)
            }
        }

        Column(modifier = Modifier.horizontalScroll(scroll)) {
            Row {
                GridHeaderCell("", 100)
                columns.forEach { GridHeaderCell(it, 66) }
            }
            rows.forEachIndexed { rowIndex, rowName ->
                Row {
                    GridHeaderCell(rowName, 100)
                    columns.forEachIndexed { columnIndex, _ ->
                        val enabled = !(rowIndex >= 3 && columnIndex >= 3)
                        GridCell(
                            mark = grid[rowIndex to columnIndex].orEmpty(),
                            enabled = enabled,
                            onClick = { if (enabled) onCellClick(rowIndex, columnIndex) }
                        )
                    }
                }
            }
        }

        Text(
            "Top block: location ↔ suspect and location ↔ object. Bottom-left block: object ↔ suspect.",
            color = MutedGrey,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun GridHeaderCell(text: String, width: Int) {
    Box(
        modifier = Modifier
            .width(width.dp)
            .height(58.dp)
            .background(SlateCard)
            .border(0.5.dp, MutedGrey.copy(alpha = 0.35f))
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = GridWhite,
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun GridCell(mark: String, enabled: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 66.dp, height = 58.dp)
            .background(if (enabled) CharcoalSurface else SlateCard.copy(alpha = 0.45f))
            .border(0.5.dp, MutedGrey.copy(alpha = 0.35f))
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = mark,
            color = if (mark == "O") ClueGreen else BloodRed,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
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
    adNotice: String?,
    onSuspect: (String) -> Unit,
    onWeapon: (String) -> Unit,
    onLocation: (String) -> Unit,
    onLiar: (String) -> Unit,
    onCheck: () -> Unit,
    onReveal: () -> Unit
) {
    val ready = suspect != null && weapon != null && location != null && (!mystery.hasLiar || liar != null)
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("FINAL ACCUSATION", color = NoirAmber, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        SelectionMenu("Culprit", suspect, mystery.suspects, onSuspect)
        SelectionMenu("Object used", weapon, mystery.weapons, onWeapon)
        SelectionMenu("Location", location, mystery.locations, onLocation)
        if (mystery.hasLiar) SelectionMenu("False witness", liar, mystery.suspects, onLiar)

        if (result == AccusationResult.Incorrect) {
            Card(colors = CardDefaults.cardColors(containerColor = BloodRed.copy(alpha = 0.14f))) {
                Text(
                    "The accusation conflicts with the evidence. Recheck the grid and liar statements.",
                    color = BloodRed,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
        if (adNotice != null) Text(adNotice, color = MutedGrey, fontSize = 11.sp)

        Button(
            onClick = onCheck,
            enabled = ready,
            colors = ButtonDefaults.buttonColors(containerColor = NoirAmber, contentColor = Color.Black),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CHECK ACCUSATION", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        }
        OutlinedButton(onClick = onReveal, modifier = Modifier.fillMaxWidth()) {
            Text("REVEAL SOLUTION", color = BloodRed, fontFamily = FontFamily.Monospace)
        }
        Text(
            "Release builds may show a rewarded ad before checking or revealing. Debug builds skip ads.",
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
        Box {
            OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text(selected ?: "Select $label", color = selected?.let { GridWhite } ?: MutedGrey)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            expanded = false
                            onSelected(option)
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
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SolutionLine("Culprit", mystery.solutionSuspect)
                SolutionLine("Object", mystery.solutionWeapon)
                SolutionLine("Location", mystery.solutionLocation)
                if (mystery.hasLiar) SolutionLine("False witness", mystery.solutionLiar.orEmpty())
                HorizontalDivider(color = MutedGrey.copy(alpha = 0.35f))
                Text("EXPLANATION", color = NoirAmber, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                Text(mystery.explanation, color = SlateGrey, lineHeight = 20.sp)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("CLOSE FILE", color = NoirAmber, fontFamily = FontFamily.Monospace)
            }
        },
        containerColor = CharcoalSurface
    )
}

@Composable
private fun SolutionLine(label: String, value: String) {
    Row {
        Text("$label:", color = NoirAmber, modifier = Modifier.width(105.dp), fontFamily = FontFamily.Monospace, fontSize = 12.sp)
        Text(value, color = GridWhite, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}
