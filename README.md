# Deduce It 2

Deduce It 2 is a standalone Android detective logic-grid game built with Kotlin and Jetpack Compose.

## Features

- Ten original mystery cases
- Suspect, weapon, and location deduction grids
- Clue checklists and cast dossiers
- Special cases where exactly one witness is lying
- Rewarded-ad hooks for checking and revealing answers
- Automated tests that brute-force every puzzle and verify a unique solution

## Development

Open the project in Android Studio, allow Gradle to sync, and run the `app` configuration on an Android 7.0+ emulator or device.

Release builds can read these environment variables:

- `KEYSTORE_PATH`
- `STORE_PASSWORD`
- `KEY_PASSWORD`
- `ADMOB_APP_ID`
- `ADMOB_REWARDED_CHECK_ANSWER_ID`
- `ADMOB_REWARDED_REVEAL_SOLUTION_ID`

Debug builds bypass rewarded ads so puzzles can be tested quickly.
