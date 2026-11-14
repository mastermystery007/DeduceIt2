package com.mysterybox.deduceit2

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import com.google.android.play.core.review.ReviewManagerFactory

/**
 * Google Play's in-app rating sheet, shown only after a good moment (solving a case yourself, from
 * the second solved case on), at most three times and never more than once a month. Google also
 * applies its own quota, so the sheet may not appear even when this asks for it.
 */
internal object ReviewPrompt {
    private const val PREFS = "deduce_it_review"
    private const val KEY_ASKS = "asks"
    private const val KEY_LAST_ASK = "last_ask_ms"
    private const val MIN_SOLVES = 2
    private const val MAX_ASKS = 3
    private const val MIN_GAP_MS = 30L * 24 * 60 * 60 * 1000

    fun maybeAsk(context: Context, solvedCount: Int) {
        if (solvedCount < MIN_SOLVES) return
        val activity = context.findActivity() ?: return
        val prefs = activity.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val asks = prefs.getInt(KEY_ASKS, 0)
        val now = System.currentTimeMillis()
        if (asks >= MAX_ASKS || now - prefs.getLong(KEY_LAST_ASK, 0L) < MIN_GAP_MS) return
        prefs.edit().putInt(KEY_ASKS, asks + 1).putLong(KEY_LAST_ASK, now).apply()

        val manager = ReviewManagerFactory.create(activity)
        manager.requestReviewFlow().addOnCompleteListener { request ->
            if (request.isSuccessful && !activity.isFinishing) {
                manager.launchReviewFlow(activity, request.result)
            }
        }
    }
}

internal tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
