plugins {
    androidApp()
    kotlinAndroid()
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
                "platform" to d4lClientConfig.platform,
                "debug" to "true"
        ))
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            setMatchingFallbacks("release", "debug")
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            setMatchingFallbacks("release", "debug")
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
                    "environment" to "${Environment.DEVELOPMENT}",
                    "platform" to d4lClientConfig.platform
            ))
            applicationIdSuffix = ".development"
            versionNameSuffix = "-development"
            setMatchingFallbacks("release", "debug")
        }
        val staging by creating {
            dimension("environment")
            manifestPlaceholders(mapOf(
                    "clientId" to d4lClientConfig[Environment.STAGING].id,
                    "clientSecret" to d4lClientConfig[Environment.STAGING].secret,
                    "redirectScheme" to d4lClientConfig[Environment.STAGING].redirectScheme,
                    "environment" to "${Environment.STAGING}",
                    "platform" to d4lClientConfig.platform
            ))
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            setMatchingFallbacks("release", "debug")
        }
        val sandbox by creating {
            dimension("environment")
            manifestPlaceholders(mapOf(
                    "clientId" to d4lClientConfig[Environment.SANDBOX].id,
                    "clientSecret" to d4lClientConfig[Environment.SANDBOX].secret,
                    "redirectScheme" to d4lClientConfig[Environment.SANDBOX].redirectScheme,
                    "environment" to "${Environment.SANDBOX}",
                    "platform" to d4lClientConfig.platform
            ))
            applicationIdSuffix = ".sandbox"
            versionNameSuffix = "-sandbox"
            setMatchingFallbacks("release", "debug")
        }
        val production by creating {
            dimension("environment")
            manifestPlaceholders(mapOf(
                    "clientId" to d4lClientConfig[Environment.PRODUCTION].id,
                    "clientSecret" to d4lClientConfig[Environment.PRODUCTION].secret,
                    "redirectScheme" to d4lClientConfig[Environment.PRODUCTION].redirectScheme,
                    "environment" to "${Environment.PRODUCTION}",
                    "platform" to d4lClientConfig.platform
            ))
            applicationIdSuffix = ".production"
            versionNameSuffix = "-production"
            setMatchingFallbacks("release", "debug")
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
        isCoreLibraryDesugaringEnabled = true

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

        execution = "ANDROIDX_TEST_ORCHESTRATOR"
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

    implementation(Dependencies.Android.AndroidX.lifecylceViewModelKtx)
    implementation(Dependencies.Android.AndroidX.lifecylceCommonJava8)

    implementation(Dependencies.Android.AndroidX.navigationFragmentKtx)
    implementation(Dependencies.Android.AndroidX.navigationUiKtx)

    implementation(Dependencies.Android.material)

    implementation(Dependencies.Android.D4L.hcSdk) {
        exclude(group = "org.threeten", module = "threetenbp")
    }
    implementation(Dependencies.Android.threeTenABP)

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
