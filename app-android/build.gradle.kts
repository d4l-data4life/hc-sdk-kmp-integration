plugins {
    id("com.android.application")
    id("kotlin-android")
}

val d4lClientConfig = D4LConfigHelper.loadClientConfigAndroid("$rootDir")
val d4LTestConfig = D4LConfigHelper.loadTestConfigAndroid("$rootDir")

android {
    compileSdk = AppConfig.androidConfig.compileSdkVersion

    defaultConfig {
        applicationId = AppConfig.androidConfig.applicationId

        minSdk = AppConfig.androidConfig.minSdkVersion
        targetSdk = AppConfig.androidConfig.targetSdkVersion

        versionCode = AppConfig.androidConfig.versionCode
        versionName = AppConfig.androidConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments += mapOf(
            "clearPackageData" to "true"
        )

        manifestPlaceholders["clientId"] = d4lClientConfig[Environment.DEVELOPMENT].id
        manifestPlaceholders["clientSecret"] = d4lClientConfig[Environment.DEVELOPMENT].secret
        manifestPlaceholders["redirectScheme"] = d4lClientConfig[Environment.DEVELOPMENT].redirectScheme
        manifestPlaceholders["environment"] = "${Environment.DEVELOPMENT}"
        manifestPlaceholders["platform"] = d4lClientConfig.platform
        manifestPlaceholders["debug"] = "true"
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
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            setMatchingFallbacks("release", "debug")
        }
    }

    flavorDimensions += listOf("environment")

    productFlavors {
        val development by creating {
            dimension = "environment"

            manifestPlaceholders["clientId"] = d4lClientConfig[Environment.DEVELOPMENT].id
            manifestPlaceholders["clientSecret"] = d4lClientConfig[Environment.DEVELOPMENT].secret
            manifestPlaceholders["redirectScheme"] = d4lClientConfig[Environment.DEVELOPMENT].redirectScheme
            manifestPlaceholders["environment"] = "${Environment.DEVELOPMENT}"
            manifestPlaceholders["platform"] = d4lClientConfig.platform

            applicationIdSuffix = ".development"
            versionNameSuffix = "-development"
            setMatchingFallbacks("release", "debug")
        }
        val staging by creating {
            dimension = "environment"

            manifestPlaceholders["clientId"] = d4lClientConfig[Environment.STAGING].id
            manifestPlaceholders["clientSecret"] = d4lClientConfig[Environment.STAGING].secret
            manifestPlaceholders["redirectScheme"] = d4lClientConfig[Environment.STAGING].redirectScheme
            manifestPlaceholders["environment"] = "${Environment.STAGING}"
            manifestPlaceholders["platform"] = d4lClientConfig.platform

            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            setMatchingFallbacks("release", "debug")
        }
        val sandbox by creating {
            dimension = "environment"

            manifestPlaceholders["clientId"] = d4lClientConfig[Environment.SANDBOX].id
            manifestPlaceholders["clientSecret"] = d4lClientConfig[Environment.SANDBOX].secret
            manifestPlaceholders["redirectScheme"] = d4lClientConfig[Environment.SANDBOX].redirectScheme
            manifestPlaceholders["environment"] = "${Environment.SANDBOX}"
            manifestPlaceholders["platform"] = d4lClientConfig.platform

            applicationIdSuffix = ".sandbox"
            versionNameSuffix = "-sandbox"
            setMatchingFallbacks("release", "debug")
        }
        val production by creating {
            dimension = "environment"

            manifestPlaceholders["clientId"] = d4lClientConfig[Environment.PRODUCTION].id
            manifestPlaceholders["clientSecret"] = d4lClientConfig[Environment.PRODUCTION].secret
            manifestPlaceholders["redirectScheme"] = d4lClientConfig[Environment.PRODUCTION].redirectScheme
            manifestPlaceholders["environment"] = "${Environment.PRODUCTION}"
            manifestPlaceholders["platform"] = d4lClientConfig.platform

            applicationIdSuffix = ".production"
            versionNameSuffix = "-production"
            setMatchingFallbacks("release", "debug")
        }
    }

    defaultPublishConfig = "developmentDebug"

    sourceSets {
        getByName("main") {
            res.setSrcDirs(
                setOf(
                    "src/main/res",

                    // feature resources
                    "src/main/res_feature/home",
                    "src/main/res_feature/document",
                    "src/main/res_feature/welcome"
                )
            )
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.androidXCompose
    }

    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    lint {
        abortOnError = false
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

    // Compose
    implementation(Dependencies.Android.AndroidX.Compose.compiler)
    implementation(Dependencies.Android.AndroidX.Compose.runtime)
    implementation(Dependencies.Android.AndroidX.Compose.ui)
    implementation(Dependencies.Android.AndroidX.Compose.uiTooling)
    implementation(Dependencies.Android.AndroidX.Compose.foundation)
    implementation(Dependencies.Android.AndroidX.Compose.material)
    implementation(Dependencies.Android.AndroidX.Compose.materialIconsCore)
    implementation(Dependencies.Android.AndroidX.Compose.materialIconsExtended)
    implementation(Dependencies.Android.AndroidX.Compose.activity)
    implementation(Dependencies.Android.AndroidX.Compose.lifecycle)
    implementation(Dependencies.Android.AndroidX.Compose.liveData)
    implementation(Dependencies.Android.AndroidX.Compose.navigation)

    implementation(Dependencies.Android.D4L.hcSdk) {
        exclude(group = "org.threeten", module = "threetenbp")
    }
    implementation(Dependencies.Android.threeTenABP)
    implementation(Dependencies.Android.D4L.fhirSdk)
    implementation(Dependencies.Android.D4L.fhirHelper)
    implementation(Dependencies.Android.D4L.authSdk)
    implementation(Dependencies.Android.appAuth)


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

    androidTestImplementation(Dependencies.Android.AndroidTest.androidXTestComposeUi)

    androidTestImplementation(Dependencies.Android.okHttp)
    androidTestImplementation(Dependencies.Android.okHttpLoggingInterceptor)
    androidTestImplementation(Dependencies.Android.retrofit)
    androidTestImplementation(Dependencies.Android.gson)
}

val androidTestAssetsPath = "${projectDir}/src/androidTest/assets"

val provideTestConfig: Task by tasks.creating {
    doLast {
        File(androidTestAssetsPath, "test_config.json").writeText(D4LConfigHelper.toJson(d4LTestConfig))
    }
}

tasks.named("clean") {
    doLast {
        delete("${androidTestAssetsPath}/test_config.json")
    }
}
