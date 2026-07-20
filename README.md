# Deduce It 2

Deduce It 2 is a standalone Android logic-grid game built with Kotlin and Jetpack Compose.

The cases focus on harmless mysteries and playful mischief: a painted theatre cat, mixed-up greenhouse labels, scrambled display boards, joke broadcasts, and similar incidents. Players use clues to identify the responsible person, object, location, and—where applicable—the single false witness.

## Features

- Ten original, non-violent mystery cases
- Suspect, object, and location deduction grids
- Clue checklists and cast dossiers
- Special cases where exactly one witness is lying
- Cases 1 and 2 are free
- One rewarded ad permanently unlocks each later case
- Optional rewarded ad for revealing a solution early
- Google UMP consent handling and permanent privacy-policy access
- Automated tests that brute-force every puzzle and verify a unique solution

## Development

Open the project in Android Studio, allow Gradle to sync, and run the `app` configuration on an Android 7.0+ emulator or device.

Debug builds use fake rewarded ads, so locked cases unlock immediately during testing.

Release builds can read these environment variables or Gradle properties:

- `KEYSTORE_PATH`
- `STORE_PASSWORD`
- `KEY_PASSWORD`
- `ADMOB_APP_ID`
- `ADMOB_REWARDED_UNLOCK_CASE_ID`
- `ADMOB_REWARDED_REVEAL_SOLUTION_ID`

Android Studio’s **Generate Signed Bundle / APK** wizard is also supported.

## Privacy policy

The app links to its permanent policy page:

`https://mastermystery007.github.io/privacy_policies/deduce-it-2/`
