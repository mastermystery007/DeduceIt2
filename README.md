# Deduce It 2

Deduce It 2 is a standalone Android detective logic-grid game built with Kotlin and Jetpack Compose.

## Features

- Ten original mystery cases
- Cases 1 and 2 are free; later cases permanently unlock after one rewarded ad each
- Suspect, object, and location deduction grids
- Dossier-first navigation with clue checklists and cast profiles
- Special cases where exactly one witness is lying
- Free accusation checking and rewarded early solution reveals
- Google UMP consent flow with privacy choices
- Automated tests that brute-force every puzzle and verify a unique solution

## Development

Open the project in Android Studio, allow Gradle to sync, and run the `app` configuration on an Android 7.0+ emulator or device.

Release builds can read these environment variables:

- `KEYSTORE_PATH`
- `STORE_PASSWORD`
- `KEY_PASSWORD`
- `ADMOB_APP_ID`
- `ADMOB_REWARDED_UNLOCK_CASE_ID`
- `ADMOB_REWARDED_REVEAL_SOLUTION_ID`

Debug builds use Google's test ad configuration and instantly grant fake rewards so the unlock flow can be tested quickly.
