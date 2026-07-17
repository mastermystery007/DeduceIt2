plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.mysterybox.deduceit2"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mysterybox.deduceit2"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

        val testAppId = "ca-app-pub-3940256099942544~3347511713"
        val testRewardedId = "ca-app-pub-3940256099942544/5224354917"
        val adMobAppId = providers.environmentVariable("ADMOB_APP_ID")
            .orElse(providers.gradleProperty("ADMOB_APP_ID"))
            .orElse(testAppId)
            .get()
        val rewardedCheckId = providers.environmentVariable("ADMOB_REWARDED_CHECK_ANSWER_ID")
            .orElse(providers.gradleProperty("ADMOB_REWARDED_CHECK_ANSWER_ID"))
            .orElse(testRewardedId)
            .get()
        val rewardedUnlockId = providers.environmentVariable("ADMOB_REWARDED_UNLOCK_CASE_ID")
            .orElse(providers.gradleProperty("ADMOB_REWARDED_UNLOCK_CASE_ID"))
            .orElse(testRewardedId)
            .get()
        val rewardedRevealId = providers.environmentVariable("ADMOB_REWARDED_REVEAL_SOLUTION_ID")
            .orElse(providers.gradleProperty("ADMOB_REWARDED_REVEAL_SOLUTION_ID"))
            .orElse(testRewardedId)
            .get()

        manifestPlaceholders["adMobAppId"] = adMobAppId
        buildConfigField("String", "ADMOB_REWARDED_CHECK_ANSWER_ID", "\"$rewardedCheckId\"")
        buildConfigField("String", "ADMOB_REWARDED_UNLOCK_CASE_ID", "\"$rewardedUnlockId\"")
        buildConfigField("String", "ADMOB_REWARDED_REVEAL_SOLUTION_ID", "\"$rewardedRevealId\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val keystorePath = System.getenv("KEYSTORE_PATH") ?: project.findProperty("KEYSTORE_PATH")?.toString()
    val storePasswordValue = System.getenv("STORE_PASSWORD") ?: project.findProperty("STORE_PASSWORD")?.toString()
    val keyPasswordValue = System.getenv("KEY_PASSWORD") ?: project.findProperty("KEY_PASSWORD")?.toString()
    val hasReleaseSigning = keystorePath != null && storePasswordValue != null && keyPasswordValue != null

    signingConfigs {
        if (hasReleaseSigning) {
            create("release") {
                storeFile = file(keystorePath!!)
                storePassword = storePasswordValue
                keyAlias = "upload"
                keyPassword = keyPasswordValue
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.findByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.play.services.ads)
    implementation(libs.user.messaging.platform)

    testImplementation(libs.junit)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
