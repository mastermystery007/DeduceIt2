package com.mysterybox.deduceit2.ads

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.mysterybox.deduceit2.BuildConfig

enum class RewardedAdPurpose { CheckAnswer, RevealSolution }

interface RewardedAdManager {
    fun show(
        purpose: RewardedAdPurpose,
        onRewarded: () -> Unit,
        onUnavailable: () -> Unit
    )
}

class FakeRewardedAdManager : RewardedAdManager {
    override fun show(
        purpose: RewardedAdPurpose,
        onRewarded: () -> Unit,
        onUnavailable: () -> Unit
    ) = onRewarded()
}

class AdMobRewardedAdManager(private val activity: Activity) : RewardedAdManager {
    override fun show(
        purpose: RewardedAdPurpose,
        onRewarded: () -> Unit,
        onUnavailable: () -> Unit
    ) {
        val unitId = when (purpose) {
            RewardedAdPurpose.CheckAnswer -> BuildConfig.ADMOB_REWARDED_CHECK_ANSWER_ID
            RewardedAdPurpose.RevealSolution -> BuildConfig.ADMOB_REWARDED_REVEAL_SOLUTION_ID
        }

        RewardedAd.load(
            activity,
            unitId,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    onUnavailable()
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    var rewardGranted = false
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdFailedToShowFullScreenContent(error: AdError) {
                            onUnavailable()
                        }

                        override fun onAdDismissedFullScreenContent() {
                            if (!rewardGranted) onUnavailable()
                        }
                    }
                    ad.show(activity) {
                        rewardGranted = true
                        onRewarded()
                    }
                }
            }
        )
    }
}
