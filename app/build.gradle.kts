plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(AppConfig.androidConfig.compileSdkVersion)

    defaultConfig {
        applicationId = AppConfig.androidConfig.applicationId

        minSdkVersion(AppConfig.androidConfig.minSdkVersion)
        targetSdkVersion(AppConfig.androidConfig.targetSdkVersion)

        versionCode = AppConfig.androidConfig.versionCode
        versionName = AppConfig.androidConfig.versionName

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments(mapOf(
                "clearPackageData" to "true"
        ))

        manifestPlaceholders = mapOf<String, Any>(
                "clientId" to "73b2a47c-535e-40f3-bcc7-88deccec1dab#android",
                "clientSecret" to "androidsupersecret",
                "environment" to "development",
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
            applicationIdSuffix = ".development"
            versionNameSuffix = "-development"
        }
        create("staging") {
            setDimension("environment")
            manifestPlaceholders = mapOf<String, Any>(
                    "environment" to "staging"
            )
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
        }
        create("sandbox") {
            setDimension("environment")
            manifestPlaceholders = mapOf<String, Any>(
                    "environment" to "sandbox"
            )
            applicationIdSuffix = ".sandbox"
            versionNameSuffix = "-sandbox"
        }
        create("production") {
            setDimension("environment")
            manifestPlaceholders = mapOf<String, Any>(
                    "environment" to "production"
            )
            applicationIdSuffix = ".production"
            versionNameSuffix = "-production"
        }
    }

    defaultPublishConfig = "developmentDebug"


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

    compileOptions {
        // Flag to enable support for the new language APIs
        coreLibraryDesugaringEnabled = false

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    lintOptions {
        isAbortOnError = false
    }

    buildFeatures {
        compose = false
    }

    testOptions {
        animationsDisabled = true

        unitTests.all(KotlinClosure1<Any, Test>({
            (this as Test).also { testTask ->
                testTask.testLogging {
                    events("passed", "skipped", "failed", "standardOut", "standardError")
                }
            }
        }, unitTests))

        execution = "ANDROID_TEST_ORCHESTRATOR"
    }
}


dependencies {
    coreLibraryDesugaring(Dependency.android.desugar)

    implementation(Dependency.kotlin.stdLib)
    implementation(Dependency.kotlin.coroutinesCore)

    implementation(Dependency.android.androidX.ktx)
    implementation(Dependency.android.androidX.appCompat)
    implementation(Dependency.android.androidX.browser)
    implementation(Dependency.android.androidX.constraintLayout)

    implementation(Dependency.android.androidX.lifecylceCommonJava8)
    implementation(Dependency.android.androidX.lifecylceExtensions)

    implementation(Dependency.android.androidX.navigationFragmentKtx)
    implementation(Dependency.android.androidX.navigationUiKtx)

    implementation(Dependency.android.material)

    implementation(Dependency.android.d4l.hcSdk) {
        exclude(group = "org.threeten", module = "threetenbp")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "auth-jvm")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "crypto-jvm")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "sdk-jvm")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "securestore-jvm")
        exclude(group = "care.data4life.hc-sdk-android", module = "util-jvm")
    }
    implementation(Dependency.android.threeTenABP)
    implementation(Dependency.android.d4l.fhirSdk)
    implementation(Dependency.android.d4l.fhirHelper) {
        exclude("de.gesundheitscloud.sdk-util-multiplatform", "util-android")
    }

    releaseImplementation(Dependency.android.checkerRelease)
    androidTestImplementation(Dependency.android.chuckerDebug)



    testImplementation(Dependency.test.testJunit)


    androidTestImplementation(Dependency.test.testKotlin)
    androidTestImplementation(Dependency.test.testKotlinJunit)

    androidTestImplementation(Dependency.androidTest.androidXTestRunner)
    androidTestImplementation(Dependency.androidTest.androidXTestRules)
    androidTestImplementation(Dependency.androidTest.androidXTestOrchestrator)
    androidTestImplementation(Dependency.androidTest.androidXTestExtJUnit)

    androidTestImplementation(Dependency.androidTest.androidXTestEspressoCore)
    androidTestImplementation(Dependency.androidTest.androidXTestEspressoIntents)
    androidTestImplementation(Dependency.androidTest.androidXTestEspressoWeb)

    androidTestImplementation(Dependency.androidTest.androidXTestUiAutomator)

    androidTestImplementation(Dependency.androidTest.androidXTestKakao)

    androidTestImplementation(Dependency.android.okHttp)
    androidTestImplementation(Dependency.android.okHttpLoggingInterceptor)
    androidTestImplementation(Dependency.android.retrofit)
    androidTestImplementation(Dependency.android.gson)

}
