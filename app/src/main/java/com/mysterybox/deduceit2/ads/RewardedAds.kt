package com.mysterybox.deduceit2.ads

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.mysterybox.deduceit2.BuildConfig

enum class RewardedAdPurpose {
    UnlockCase,
    RevealSolution
}

interface RewardedAdManager {
    fun show(
        purpose: RewardedAdPurpose,
        onRewarded: () -> Unit,
        onUnavailable: () -> Unit
    )
}

object UnavailableRewardedAdManager : RewardedAdManager {
    override fun show(
        purpose: RewardedAdPurpose,
        onRewarded: () -> Unit,
        onUnavailable: () -> Unit
    ) = onUnavailable()
}

class FakeRewardedAdManager : RewardedAdManager {
    override fun show(
        purpose: RewardedAdPurpose,
        onRewarded: () -> Unit,
        onUnavailable: () -> Unit
    ) = onRewarded()
}

class AdMobRewardedAdManager(private val activity: Activity) : RewardedAdManager {
    private val rewardedAds = mutableMapOf<RewardedAdPurpose, RewardedAd?>()
    private val loadingPurposes = mutableSetOf<RewardedAdPurpose>()

    init {
        RewardedAdPurpose.entries.forEach(::load)
    }

    override fun show(
        purpose: RewardedAdPurpose,
        onRewarded: () -> Unit,
        onUnavailable: () -> Unit
    ) {
        val ad = rewardedAds[purpose]
        if (ad == null) {
            load(purpose)
            onUnavailable()
            return
        }

        rewardedAds[purpose] = null
        var rewardEarned = false
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                load(purpose)
                if (!rewardEarned) onUnavailable()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                load(purpose)
                onUnavailable()
            }
        }
        ad.show(activity) {
            rewardEarned = true
            onRewarded()
        }
    }

    private fun load(purpose: RewardedAdPurpose) {
        if (rewardedAds[purpose] != null || !loadingPurposes.add(purpose)) return

        val adUnitId = when (purpose) {
            RewardedAdPurpose.UnlockCase -> BuildConfig.ADMOB_REWARDED_UNLOCK_CASE_ID
            RewardedAdPurpose.RevealSolution -> BuildConfig.ADMOB_REWARDED_REVEAL_SOLUTION_ID
        }

        RewardedAd.load(
            activity,
            adUnitId,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    loadingPurposes.remove(purpose)
                    rewardedAds[purpose] = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    loadingPurposes.remove(purpose)
                    rewardedAds[purpose] = null
                }
            }
        )
    }
}
