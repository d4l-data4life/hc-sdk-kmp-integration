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

        manifestPlaceholders.putAll(d4lClientConfig.toConfigMap(Environment.DEVELOPMENT, true))
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

            manifestPlaceholders.putAll(d4lClientConfig.toConfigMap(Environment.DEVELOPMENT))

            applicationIdSuffix = ".development"
            versionNameSuffix = "-development"
            matchingFallbacks += listOf("release", "debug")
        }
        create("staging") {
            dimension = "environment"

            manifestPlaceholders.putAll(d4lClientConfig.toConfigMap(Environment.STAGING))

            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
            matchingFallbacks += listOf("release", "debug")
        }
        create("sandbox") {
            dimension = "environment"

            manifestPlaceholders.putAll(d4lClientConfig.toConfigMap(Environment.SANDBOX))

            applicationIdSuffix = ".sandbox"
            versionNameSuffix = "-sandbox"
            matchingFallbacks += listOf("release", "debug")
        }
        create("production") {
            dimension = "environment"

            manifestPlaceholders.putAll(d4lClientConfig.toConfigMap(Environment.PRODUCTION))

            applicationIdSuffix = ".production"
            versionNameSuffix = "-production"
            matchingFallbacks += listOf("release", "debug")
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
    implementation(Dependency.Android.appAuth)


    testImplementation(Dependency.JvmTest.junit)
    testRuntimeOnly(Dependency.JvmTest.junit5EngineVintage)

    testImplementation(Dependency.JvmTest.junit5)
    testRuntimeOnly(Dependency.JvmTest.junit5Engine)

    testImplementation(Dependency.JvmTest.junit5Parameterized)

    testImplementation(Dependency.jvmTest.mockk)


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
    debugImplementation(Dependency.AndroidTest.composeUiManifest)

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
