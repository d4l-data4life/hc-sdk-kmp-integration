plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(AndroidConfig.compileSdkVersion)

    defaultConfig {
        applicationId = "care.data4life.integration.app"

        minSdkVersion(AndroidConfig.minSdkVersion)
        targetSdkVersion(AndroidConfig.targetSdkVersion)

        versionCode = 1
        versionName = "1.0"

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments(mapOf(
                "clearPackageData" to "true"
        ))

        manifestPlaceholders = mapOf<String, Any>(
                "clientId" to "73b2a47c-535e-40f3-bcc7-88deccec1dab#android",
                "clientSecret" to "androidsupersecret",
                "environment" to "staging",
                "redirectScheme" to "de.gesundheitscloud.73b2a47c-535e-40f3-bcc7-88deccec1dab",
                "debug" to "true"
        )
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions("environment")

    productFlavors {
        create("development") {
            setDimension("environment")
            manifestPlaceholders = mapOf<String, Any>(
                    "environment" to "development"
            )
        }
        create("staging") {
            setDimension("environment")
            manifestPlaceholders = mapOf<String, Any>(
                    "environment" to "staging"
            )
        }
        create("production") {
            setDimension("environment")
            manifestPlaceholders = mapOf<String, Any>(
                    "environment" to "production"
            )
        }
    }

    defaultPublishConfig = "developmentDebug"


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    lintOptions {
        isAbortOnError = false
    }

    sourceSets {
        getByName("main") {
            res.setSrcDirs(setOf(
                    "src/main/res",

                    // feature resources
                    "src/main/res_feature/home",
                    "src/main/res_feature/document",
                    "src/main/res_feature/welcome"
            ))
        }
    }
}


dependencies {
    implementation(Libraries.kotlinStdLibJdk7)
    implementation(Libraries.kotlinCoroutinesCore)

    implementation(Libraries.androidXKtx)
    implementation(Libraries.androidXAppCompat)
    implementation(Libraries.androidXBrowser)
    implementation(Libraries.androidXConstraintLayout)

    implementation(Libraries.androidXLifecylceCommonJava8)
    implementation(Libraries.androidXLifecylceExtensions)

    implementation(Libraries.androidXNavigationFragmentKtx)
    implementation(Libraries.androidXNavigationUiKtx)

    implementation(Libraries.material)

    implementation(Libraries.hcSdk) {
        exclude(group = "org.threeten", module = "threetenbp")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "auth-jvm")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "crypto-jvm")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "sdk-jvm")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "securestore-jvm")
        exclude(group = "care.data4life.hc-sdk-android", module = "util-jvm")
    }
    implementation(Libraries.threeTenABP)
    implementation(Libraries.fhirSdk)
    implementation(Libraries.fhirHelper) {
        exclude("de.gesundheitscloud.sdk-util-multiplatform", "util-android")
    }

    testImplementation(Libraries.testJunit)

    androidTestImplementation(Libraries.testKotlin)
    androidTestImplementation(Libraries.testKotlinJunit)

    androidTestImplementation(Libraries.androidXTestRunner)
    androidTestImplementation(Libraries.androidXTestRules)
    androidTestImplementation(Libraries.androidXTestOrchestrator)
    androidTestImplementation(Libraries.androidXTestExtJUnit)

    androidTestImplementation(Libraries.androidXTestEspressoCore)
    androidTestImplementation(Libraries.androidXTestEspressoIntents)
    androidTestImplementation(Libraries.androidXTestEspressoWeb)

    androidTestImplementation(Libraries.androidXTestUiAutomator)

    androidTestImplementation(Libraries.androidXTestKakao)

    androidTestImplementation(Libraries.sparkJava)
    androidTestImplementation(Libraries.twilioSDK)
}
