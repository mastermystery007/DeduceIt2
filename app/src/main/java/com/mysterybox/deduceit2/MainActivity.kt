package com.mysterybox.deduceit2

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.ads.MobileAds
import com.mysterybox.deduceit2.ads.AdMobRewardedAdManager
import com.mysterybox.deduceit2.ads.FakeRewardedAdManager
import com.mysterybox.deduceit2.ui.CasePlayScreen
import com.mysterybox.deduceit2.ui.DashboardScreen
import com.mysterybox.deduceit2.ui.DeduceItTheme
import com.mysterybox.deduceit2.ui.HowToPlayScreen
import com.mysterybox.deduceit2.viewmodel.DetectiveViewModel

private object OnboardingPreferences {
    private const val PREFS = "deduce_it_2_onboarding"
    private const val SEEN = "has_seen_how_to_play"

    fun hasSeen(context: Context): Boolean =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(SEEN, false)

    fun markSeen(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(SEEN, true)
            .apply()
    }
}

class MainActivity : ComponentActivity() {
    private val viewModel: DetectiveViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        MobileAds.initialize(this)

        setContent {
            DeduceItTheme {
                val navController = rememberNavController()
                val activity = this@MainActivity
                val rewardedAds = remember {
                    if (BuildConfig.DEBUG) FakeRewardedAdManager()
                    else AdMobRewardedAdManager(activity)
                }
                var showHowToPlay by remember {
                    mutableStateOf(!OnboardingPreferences.hasSeen(activity))
                }

                if (showHowToPlay) {
                    HowToPlayScreen(
                        onDone = {
                            OnboardingPreferences.markSeen(activity)
                            showHowToPlay = false
                        }
                    )
                } else {
                    NavHost(navController = navController, startDestination = "dashboard") {
                        composable("dashboard") {
                            DashboardScreen(
                                viewModel = viewModel,
                                onOpenCase = { caseId ->
                                    navController.navigate("case/$caseId")
                                },
                                onOpenHowToPlay = { showHowToPlay = true }
                            )
                        }
                        composable(
                            route = "case/{caseId}",
                            arguments = listOf(navArgument("caseId") { type = NavType.IntType })
                        ) { entry ->
                            val caseId = entry.arguments?.getInt("caseId")
                            LaunchedEffect(caseId) {
                                viewModel.selectCase(caseId)
                            }
                            CasePlayScreen(
                                viewModel = viewModel,
                                rewardedAdManager = rewardedAds,
                                onBack = {
                                    viewModel.selectCase(null)
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
