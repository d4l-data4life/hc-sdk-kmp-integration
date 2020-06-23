plugins {
    androidApp()
    kotlinAndroid()
    kotlinAndroidExtensions()
}

val d4lClientConfig : D4LClientConfig by rootProject.extra

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

        manifestPlaceholders = mapOf<String, Any>(
                "clientId" to d4lClientConfig.id,
                "clientSecret" to d4lClientConfig.secret,
                "redirectScheme" to d4lClientConfig.redirectScheme,
                "environment" to "development",
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
    coreLibraryDesugaring(Dependencies.Android.androidDesugar)

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
    androidTestImplementation(Dependencies.Android.chuckerDebug)



    testImplementation(Dependencies.Android.Test.junit)


    androidTestImplementation(Dependencies.Android.Test.testKotlin)
    androidTestImplementation(Dependencies.Android.Test.testKotlinJunit)

    androidTestImplementation(Dependencies.Android.AndroidTest.androidXTestRunner)
    androidTestImplementation(Dependencies.Android.AndroidTest.androidXTestRules)
    androidTestImplementation(Dependencies.Android.AndroidTest.androidXTestOrchestrator)
    androidTestImplementation(Dependencies.Android.AndroidTest.androidXTestExtJUnit)

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
}

val d4lTestConfig : D4LTestConfig by rootProject.extra

val copyTestAssets by tasks.creating {
    dependsOn("assemble")
    doLast {
        val assetsDir = file("${projectDir}/src/androidTest/assets")
        val configJson = com.google.gson.Gson().toJson(d4lTestConfig)

        val file = File(assetsDir, "test_config.json").writeText(configJson)
    }
}

