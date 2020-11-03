plugins {
    androidApp()
    kotlinAndroid()
    kotlinAndroidExtensions()
}

val d4lClientConfig = D4LConfigHelper.loadClientConfigAndroid("$rootDir")
val d4LTestConfig = D4LConfigHelper.loadTestConfigAndroid("$rootDir")

android {
    compileSdkVersion(AppConfig.androidConfig.compileSdkVersion)

    defaultConfig {
        applicationId = AppConfig.androidConfig.applicationId

        minSdkVersion(AppConfig.androidConfig.minSdkVersion)
        targetSdkVersion(AppConfig.androidConfig.targetSdkVersion)

        versionCode = AppConfig.androidConfig.versionCode
        versionName = AppConfig.androidConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments(mapOf(
                "clearPackageData" to "true"
        ))

        manifestPlaceholders(mapOf(
                "clientId" to d4lClientConfig[Environment.DEVELOPMENT].id,
                "clientSecret" to d4lClientConfig[Environment.DEVELOPMENT].secret,
                "redirectScheme" to d4lClientConfig[Environment.DEVELOPMENT].redirectScheme,
                "environment" to "${Environment.DEVELOPMENT}",
                "debug" to "true"
        ))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions("environment")

    productFlavors {
        val development by creating {
            dimension("environment")
            manifestPlaceholders(mapOf(
                    "clientId" to d4lClientConfig[Environment.DEVELOPMENT].id,
                    "clientSecret" to d4lClientConfig[Environment.DEVELOPMENT].secret,
                    "redirectScheme" to d4lClientConfig[Environment.DEVELOPMENT].redirectScheme,
                    "environment" to "${Environment.DEVELOPMENT}"
            ))
            applicationIdSuffix = ".development"
            versionNameSuffix = "-development"
        }
        val staging by creating {
            dimension("environment")
            manifestPlaceholders(mapOf(
                    "clientId" to d4lClientConfig[Environment.STAGING].id,
                    "clientSecret" to d4lClientConfig[Environment.STAGING].secret,
                    "redirectScheme" to d4lClientConfig[Environment.STAGING].redirectScheme,
                    "environment" to "${Environment.STAGING}"
            ))
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
        }
        val sandbox by creating {
            dimension("environment")
            manifestPlaceholders(mapOf(
                    "clientId" to d4lClientConfig[Environment.SANDBOX].id,
                    "clientSecret" to d4lClientConfig[Environment.SANDBOX].secret,
                    "redirectScheme" to d4lClientConfig[Environment.SANDBOX].redirectScheme,
                    "environment" to "${Environment.SANDBOX}"
            ))
            applicationIdSuffix = ".sandbox"
            versionNameSuffix = "-sandbox"
        }
        val production by creating {
            dimension("environment")
            manifestPlaceholders(mapOf(
                    "clientId" to d4lClientConfig[Environment.PRODUCTION].id,
                    "clientSecret" to d4lClientConfig[Environment.PRODUCTION].secret,
                    "redirectScheme" to d4lClientConfig[Environment.PRODUCTION].redirectScheme,
                    "environment" to "${Environment.PRODUCTION}"
            ))
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
        isCoreLibraryDesugaringEnabled = false

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

    useLibrary("android.test.runner")
    useLibrary("android.test.base")
    useLibrary("android.test.mock")

    testOptions {
        animationsDisabled = true

        unitTests.all {
            it.testLogging {
                events("passed", "skipped", "failed", "standardOut", "standardError")
            }
        }

        // FIXME Test Orchestrator is currently broken and results in no tests found
        // execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
}


dependencies {
    coreLibraryDesugaring(Dependencies.Android.desugar)

    implementation(Dependencies.Android.kotlinStdLib)
    implementation(Dependencies.Android.kotlinCoroutinesCore)

    implementation(Dependencies.Android.AndroidX.ktx)
    implementation(Dependencies.Android.AndroidX.appCompat)
    implementation(Dependencies.Android.AndroidX.browser)
    implementation(Dependencies.Android.AndroidX.constraintLayout)

    implementation(Dependencies.Android.AndroidX.lifecylceCommonJava8)
    implementation(Dependencies.Android.AndroidX.lifecylceExtensions)

    implementation(Dependencies.Android.AndroidX.navigationFragmentKtx)
    implementation(Dependencies.Android.AndroidX.navigationUiKtx)

    implementation(Dependencies.Android.material)

    implementation(Dependencies.Android.D4L.hcSdk) {
        exclude(group = "org.threeten", module = "threetenbp")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "auth-jvm")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "crypto-jvm")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "sdk-jvm")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "securestore-jvm")
        exclude(group = "care.data4life.hc-sdk-android", module = "util-jvm")
    }
    implementation(Dependencies.Android.threeTenABP)
    implementation(Dependencies.Android.D4L.fhirSdk)
    implementation(Dependencies.Android.D4L.fhirHelper) {
        exclude("de.gesundheitscloud.sdk-util-multiplatform", "util-android")
    }

    releaseImplementation(Dependencies.Android.checkerRelease)


    testImplementation(Dependencies.Android.Test.junit)


    androidTestUtil(Dependencies.Android.AndroidTest.androidXTestOrchestrator)
    androidTestImplementation(Dependencies.Android.AndroidTest.androidXTestCore)
    androidTestImplementation(Dependencies.Android.AndroidTest.androidXTestRunner)
    androidTestImplementation(Dependencies.Android.AndroidTest.androidXTestRules)
    androidTestImplementation(Dependencies.Android.AndroidTest.androidXTestExtJUnit)

    androidTestImplementation(Dependencies.Android.Test.testKotlin)
    androidTestImplementation(Dependencies.Android.Test.testKotlinJunit)

    androidTestImplementation(Dependencies.Android.AndroidTest.androidXTestEspressoCore)
    androidTestImplementation(Dependencies.Android.AndroidTest.androidXTestEspressoIntents)
    androidTestImplementation(Dependencies.Android.AndroidTest.androidXTestEspressoWeb)

    androidTestImplementation(Dependencies.Android.AndroidTest.androidXTestUiAutomator)

    androidTestImplementation(Dependencies.Android.AndroidTest.kakao)
    androidTestImplementation(Dependencies.Android.AndroidTest.kaspresso)


    androidTestImplementation(Dependencies.Android.okHttp)
    androidTestImplementation(Dependencies.Android.okHttpLoggingInterceptor)
    androidTestImplementation(Dependencies.Android.retrofit)
    androidTestImplementation(Dependencies.Android.gson)
    androidTestImplementation(Dependencies.Android.chuckerDebug)
}

val androidTestAssetsPath = "${projectDir}/src/androidTest/assets"

val provideAndroidTestConfig: Task by tasks.creating {
    doLast {
        File(androidTestAssetsPath, "test_config.json").writeText(D4LConfigHelper.toJson(d4LTestConfig))
    }
}

tasks.named("clean") {
    doLast {
        delete("${androidTestAssetsPath}/test_config.json")
    }
}
