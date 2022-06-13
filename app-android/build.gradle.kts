import care.data4life.gradle.integration.dependency.Dependency
import care.data4life.gradle.integration.dependency.Version

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("de.mannodermaus.android-junit5")
}

val d4lClientConfig = D4LConfigHelper.loadClientConfigAndroid("$rootDir")
val d4LTestConfig = D4LConfigHelper.loadTestConfigAndroid("$rootDir")

android {
    compileSdk = AppConfig.androidConfig.compileSdkVersion
    namespace = "care.data4life.integration.app"

    defaultConfig {
        applicationId = AppConfig.androidConfig.applicationId

        minSdk = AppConfig.androidConfig.minSdkVersion
        targetSdk = AppConfig.androidConfig.targetSdkVersion

        versionCode = AppConfig.androidConfig.versionCode
        versionName = AppConfig.androidConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments += mapOf(
            "runnerBuilder" to "de.mannodermaus.junit5.AndroidJUnit5Builder",
            "clearPackageData" to "true",
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
            matchingFallbacks += listOf("release", "debug")
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            matchingFallbacks += listOf("release")
        }
    }

    flavorDimensions += listOf("environment")

    productFlavors {
        create("development") {
            dimension = "environment"

            manifestPlaceholders["clientId"] = d4lClientConfig[Environment.DEVELOPMENT].id
            manifestPlaceholders["clientSecret"] = d4lClientConfig[Environment.DEVELOPMENT].secret
            manifestPlaceholders["redirectScheme"] = d4lClientConfig[Environment.DEVELOPMENT].redirectScheme
            manifestPlaceholders["environment"] = "${Environment.DEVELOPMENT}"
            manifestPlaceholders["platform"] = d4lClientConfig.platform

            applicationIdSuffix = ".development"
            versionNameSuffix = "-development"
            matchingFallbacks += listOf("release", "debu")
        }
        create("staging") {
            dimension = "environment"

            manifestPlaceholders["clientId"] = d4lClientConfig[Environment.STAGING].id
            manifestPlaceholders["clientSecret"] = d4lClientConfig[Environment.STAGING].secret
            manifestPlaceholders["redirectScheme"] = d4lClientConfig[Environment.STAGING].redirectScheme
            manifestPlaceholders["environment"] = "${Environment.STAGING}"
            manifestPlaceholders["platform"] = d4lClientConfig.platform

            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            matchingFallbacks += listOf("release", "debu")
        }
        create("sandbox") {
            dimension = "environment"

            manifestPlaceholders["clientId"] = d4lClientConfig[Environment.SANDBOX].id
            manifestPlaceholders["clientSecret"] = d4lClientConfig[Environment.SANDBOX].secret
            manifestPlaceholders["redirectScheme"] = d4lClientConfig[Environment.SANDBOX].redirectScheme
            manifestPlaceholders["environment"] = "${Environment.SANDBOX}"
            manifestPlaceholders["platform"] = d4lClientConfig.platform

            applicationIdSuffix = ".sandbox"
            versionNameSuffix = "-sandbox"
            matchingFallbacks += listOf("release", "debu")
        }
        create("production") {
            dimension = "environment"

            manifestPlaceholders["clientId"] = d4lClientConfig[Environment.PRODUCTION].id
            manifestPlaceholders["clientSecret"] = d4lClientConfig[Environment.PRODUCTION].secret
            manifestPlaceholders["redirectScheme"] = d4lClientConfig[Environment.PRODUCTION].redirectScheme
            manifestPlaceholders["environment"] = "${Environment.PRODUCTION}"
            manifestPlaceholders["platform"] = d4lClientConfig.platform

            applicationIdSuffix = ".production"
            versionNameSuffix = "-production"
            matchingFallbacks += listOf("release", "debu")
        }
    }

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
        kotlinCompilerExtensionVersion = Version.android.compose.core
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
    coreLibraryDesugaring(Dependency.android.desugar)

    implementation(Dependency.Android.kotlinStdLib)
    implementation(Dependency.Android.kotlinCoroutinesCore)

    implementation(Dependency.Android.AndroidX.ktx)
    implementation(Dependency.Android.AndroidX.appCompat)
    implementation(Dependency.Android.AndroidX.browser)
    implementation(Dependency.Android.AndroidX.constraintLayout)

    implementation(Dependency.Android.AndroidX.lifecylceViewModelKtx)
    implementation(Dependency.Android.AndroidX.lifecylceCommonJava8)

    implementation(Dependency.Android.AndroidX.navigationFragmentKtx)
    implementation(Dependency.Android.AndroidX.navigationUiKtx)

    implementation(Dependency.Android.material)

    // Compose
    implementation(Dependency.Android.AndroidX.Compose.compiler)
    implementation(Dependency.Android.AndroidX.Compose.runtime)
    implementation(Dependency.Android.AndroidX.Compose.ui)
    implementation(Dependency.Android.AndroidX.Compose.uiTooling)
    implementation(Dependency.Android.AndroidX.Compose.foundation)
    implementation(Dependency.Android.AndroidX.Compose.material)
    implementation(Dependency.Android.AndroidX.Compose.materialIconsCore)
    implementation(Dependency.Android.AndroidX.Compose.materialIconsExtended)
    implementation(Dependency.Android.AndroidX.Compose.activity)
    implementation(Dependency.Android.AndroidX.Compose.lifecycle)
    implementation(Dependency.Android.AndroidX.Compose.liveData)
    implementation(Dependency.Android.AndroidX.Compose.navigation)

    implementation(Dependency.Multiplatform.D4L.hcSdk) {
        exclude(group = "org.threeten", module = "threetenbp")
    }
    implementation(Dependency.Android.threeTenABP)
    implementation(Dependency.Android.D4L.fhirSdk)
    implementation(Dependency.Android.D4L.fhirHelper)
    implementation(Dependency.Android.D4L.authSdk)
    implementation(Dependency.Android.appAuth)


    testImplementation(Dependency.JvmTest.junit)
    testRuntimeOnly(Dependency.JvmTest.junit5EngineVintage)

    testImplementation(Dependency.JvmTest.junit5)
    testRuntimeOnly(Dependency.JvmTest.junit5Engine)

    testImplementation(Dependency.JvmTest.junit5Parameterized)

    testImplementation(Dependency.AndroidTest.mockk)


    androidTestUtil(Dependency.AndroidTest.orchestrator)
    androidTestImplementation(Dependency.AndroidTest.core)
    androidTestImplementation(Dependency.AndroidTest.runner)
    androidTestImplementation(Dependency.AndroidTest.rules)
    androidTestImplementation(Dependency.AndroidTest.junitExt)

    androidTestImplementation(Dependency.JvmTest.junit5)
    androidTestImplementation(Dependency.AndroidTest.junit5AndroidInstrumentation)
    androidTestRuntimeOnly(Dependency.AndroidTest.junit5AndroidInstrumentationRuntime)
    androidTestImplementation(Dependency.JvmTest.testKotlin)
    androidTestImplementation(Dependency.JvmTest.testKotlinJunit)

    androidTestImplementation(Dependency.AndroidTest.espressoCore)
    androidTestImplementation(Dependency.AndroidTest.espressoIntents)
    androidTestImplementation(Dependency.AndroidTest.espressoWeb)

    androidTestImplementation(Dependency.AndroidTest.uiAutomator)

    androidTestImplementation(Dependency.AndroidTest.mockk)

    androidTestImplementation(Dependency.AndroidTest.kakaoCompose)

    androidTestImplementation(Dependency.AndroidTest.composeUi)

    androidTestImplementation(Dependency.Android.okHttp)
    androidTestImplementation(Dependency.Android.okHttpLoggingInterceptor)
    androidTestImplementation(Dependency.Android.retrofit)
    androidTestImplementation(Dependency.Android.gson)
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
