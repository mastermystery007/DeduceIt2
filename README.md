# Deduce It 2

Deduce It 2 is a standalone Android logic-grid mystery game built with Kotlin and Jetpack Compose.

## Features

- Ten original, harmless mystery cases
- The same Carbon Noir theme, typography, launcher styling, responsive grid and tutorial design as Deduce It
- Amber person/location headers and green object headers in the deduction grid
- Cell inspector, fit-to-screen and large grid modes
- Persistent clue checks, grid markings, completed cases and rewarded case unlocks
- Cases 1 and 2 are free; each later case is permanently unlocked after one rewarded ad
- Optional rewarded hints for liar cases and early solution reveals
- Google UMP consent handling and a permanent privacy-policy link
- Automated tests that brute-force every puzzle and verify a unique solution

## Development

Open the project in Android Studio, allow Gradle to sync, and run the `app` configuration on an Android 7.0+ emulator or device.

Release builds can read these environment variables or Gradle properties:

- `KEYSTORE_PATH`
- `STORE_PASSWORD`
- `KEY_PASSWORD`
- `ADMOB_APP_ID`
- `ADMOB_REWARDED_UNLOCK_CASE_ID`
- `ADMOB_REWARDED_REVEAL_LIAR_ID`
- `ADMOB_REWARDED_REVEAL_SOLUTION_ID`

Debug builds use fake rewards so cases and reveal flows can be tested without requesting live advertisements.
