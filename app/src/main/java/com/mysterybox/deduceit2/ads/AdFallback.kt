package com.mysterybox.deduceit2.ads

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.time.LocalDate

/**
 * When a rewarded ad can't be shown, decides whether to give the reward anyway.
 *
 * - Offline: never. Otherwise turning the internet off would skip every ad.
 * - Online but Google has no ad to show (or it failed): from the second try in a row, at most
 *   [MAX_FREE_PER_DAY] times a day, so a real ad shortage doesn't leave a player stuck.
 */
object AdFallback {
    const val MAX_FREE_PER_DAY = 2
    private const val PREFS = "ad_fallback"
    private const val KEY_DAY = "day"
    private const val KEY_USED = "used"

    fun isOnline(context: Context): Boolean {
        val manager = context.getSystemService(ConnectivityManager::class.java) ?: return false
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    /**
     * Call after an ad failed to show. [failedTries] counts consecutive failures for this reward,
     * including this one. Returns true (and uses up one of today's free grants) when the reward
     * may be given without an ad.
     */
    fun allowFreeGrant(context: Context, failedTries: Int): Boolean {
        if (failedTries < 2 || !isOnline(context)) return false
        val prefs = context.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val today = LocalDate.now().toEpochDay()
        val used = if (prefs.getLong(KEY_DAY, Long.MIN_VALUE) == today) prefs.getInt(KEY_USED, 0) else 0
        if (used >= MAX_FREE_PER_DAY) return false
        prefs.edit().putLong(KEY_DAY, today).putInt(KEY_USED, used + 1).apply()
        return true
    }
}
