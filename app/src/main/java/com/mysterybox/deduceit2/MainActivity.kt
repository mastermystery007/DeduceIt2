package com.mysterybox.deduceit2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.mysterybox.deduceit2.ads.RewardedAdManager
import com.mysterybox.deduceit2.ads.RewardedAdPurpose
import com.mysterybox.deduceit2.ads.UnavailableRewardedAdManager
import com.mysterybox.deduceit2.privacy.AdConsentManager
import com.mysterybox.deduceit2.ui.CasePlayScreen
import com.mysterybox.deduceit2.ui.DashboardScreen
import com.mysterybox.deduceit2.ui.DeduceItTheme
import com.mysterybox.deduceit2.ui.HowToPlayScreen
import com.mysterybox.deduceit2.viewmodel.DetectiveViewModel
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.flow.MutableStateFlow

private const val FREE_CASE_COUNT = 2

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

private object CaseUnlockPreferences {
    private const val PREFS = "deduce_it_2_unlocks"
    private const val UNLOCKED_CASE_IDS = "unlocked_case_ids"

    fun load(context: Context): Set<Int> {
        val stored = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getStringSet(UNLOCKED_CASE_IDS, emptySet())
            .orEmpty()
            .mapNotNull { it.toIntOrNull() }
            .toSet()
        return stored + (1..FREE_CASE_COUNT)
    }

    fun unlock(context: Context, caseId: Int, current: Set<Int>): Set<Int> {
        val updated = current + caseId + (1..FREE_CASE_COUNT)
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putStringSet(UNLOCKED_CASE_IDS, updated.map { it.toString() }.toSet())
            .apply()
        return updated
    }
}

class MainActivity : ComponentActivity() {
    private val viewModel: DetectiveViewModel by viewModels()

    private lateinit var consentManager: AdConsentManager
    private val rewardedAdManagerState =
        MutableStateFlow<RewardedAdManager>(UnavailableRewardedAdManager)
    private val unlockedCaseIdsState = MutableStateFlow((1..FREE_CASE_COUNT).toSet())
    private val privacyOptionsRequiredState = MutableStateFlow(false)
    private val mobileAdsInitializationStarted = AtomicBoolean(false)
    private val mobileAdsInitializationComplete = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        consentManager = AdConsentManager.getInstance(applicationContext)
        unlockedCaseIdsState.value = CaseUnlockPreferences.load(this)
        if (BuildConfig.DEBUG) {
            rewardedAdManagerState.value = FakeRewardedAdManager()
        }

        setContent {
            DeduceItTheme {
                val navController = rememberNavController()
                val activity = this@MainActivity
                val rewardedAds by rewardedAdManagerState.collectAsState()
                val unlockedCaseIds by unlockedCaseIdsState.collectAsState()
                val privacyOptionsRequired by privacyOptionsRequiredState.collectAsState()
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
                                unlockedCaseIds = unlockedCaseIds,
                                onOpenCase = { caseId ->
                                    if (caseId <= FREE_CASE_COUNT || caseId in unlockedCaseIds) {
                                        navController.navigate("case/$caseId")
                                    } else {
                                        rewardedAds.show(
                                            purpose = RewardedAdPurpose.UnlockCase,
                                            onRewarded = {
                                                unlockedCaseIdsState.value = CaseUnlockPreferences.unlock(
                                                    activity,
                                                    caseId,
                                                    unlockedCaseIdsState.value
                                                )
                                                navController.navigate("case/$caseId")
                                            },
                                            onUnavailable = {
                                                Toast.makeText(
                                                    activity,
                                                    "Rewarded ad is not ready. Please try again shortly.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    }
                                },
                                onOpenHowToPlay = { showHowToPlay = true },
                                showPrivacyOptions = privacyOptionsRequired,
                                onOpenPrivacyOptions = {
                                    consentManager.showPrivacyOptionsForm(activity) { formError ->
                                        if (formError != null) {
                                            Log.w(TAG, "Privacy options form error: ${formError.message}")
                                        }
                                        refreshPrivacyOptionsState()
                                        configureRewardedAdsForCurrentConsent()
                                    }
                                }
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

        requestConsentAndConfigureAds()
    }

    private fun requestConsentAndConfigureAds() {
        consentManager.gatherConsent(this) { error ->
            if (error != null) {
                Log.w(TAG, "Consent gathering error: ${error.message}")
            }
            refreshPrivacyOptionsState()
            configureRewardedAdsForCurrentConsent()
        }

        refreshPrivacyOptionsState()
        configureRewardedAdsForCurrentConsent()
    }

    private fun refreshPrivacyOptionsState() {
        privacyOptionsRequiredState.value = consentManager.isPrivacyOptionsRequired
    }

    private fun configureRewardedAdsForCurrentConsent() {
        if (BuildConfig.DEBUG) {
            rewardedAdManagerState.value = FakeRewardedAdManager()
            return
        }

        if (!consentManager.canRequestAds) {
            rewardedAdManagerState.value = UnavailableRewardedAdManager
            return
        }

        if (mobileAdsInitializationStarted.compareAndSet(false, true)) {
            MobileAds.initialize(this) {
                mobileAdsInitializationComplete.set(true)
                rewardedAdManagerState.value =
                    if (consentManager.canRequestAds) {
                        AdMobRewardedAdManager(this)
                    } else {
                        UnavailableRewardedAdManager
                    }
            }
            return
        }

        if (
            mobileAdsInitializationComplete.get() &&
            rewardedAdManagerState.value === UnavailableRewardedAdManager
        ) {
            rewardedAdManagerState.value = AdMobRewardedAdManager(this)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
