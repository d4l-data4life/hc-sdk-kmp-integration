plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
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
    coreLibraryDesugaring(Dependency.Android.androidDesugar)

    implementation(Dependency.Android.kotlinStdLib)
    implementation(Dependency.Android.kotlinCoroutinesCore)

    implementation(Dependency.Android.AndroidX.ktx)
    implementation(Dependency.Android.AndroidX.appCompat)
    implementation(Dependency.Android.AndroidX.browser)
    implementation(Dependency.Android.AndroidX.constraintLayout)

    implementation(Dependency.Android.AndroidX.lifecylceCommonJava8)
    implementation(Dependency.Android.AndroidX.lifecylceExtensions)

    implementation(Dependency.Android.AndroidX.navigationFragmentKtx)
    implementation(Dependency.Android.AndroidX.navigationUiKtx)

    implementation(Dependency.Android.material)

    implementation(Dependency.Android.D4L.hcSdk) {
        exclude(group = "org.threeten", module = "threetenbp")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "auth-jvm")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "crypto-jvm")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "sdk-jvm")
        exclude(group = "de.gesundheitscloud.hc-sdk-android", module = "securestore-jvm")
        exclude(group = "care.data4life.hc-sdk-android", module = "util-jvm")
    }
    implementation(Dependency.Android.threeTenABP)
    implementation(Dependency.Android.D4L.fhirSdk)
    implementation(Dependency.Android.D4L.fhirHelper) {
        exclude("de.gesundheitscloud.sdk-util-multiplatform", "util-android")
    }

    releaseImplementation(Dependency.Android.checkerRelease)
    androidTestImplementation(Dependency.Android.chuckerDebug)



    testImplementation(Dependency.Android.Test.junit)


    androidTestImplementation(Dependency.Android.Test.testKotlin)
    androidTestImplementation(Dependency.Android.Test.testKotlinJunit)

    androidTestImplementation(Dependency.Android.AndroidTest.androidXTestRunner)
    androidTestImplementation(Dependency.Android.AndroidTest.androidXTestRules)
    androidTestImplementation(Dependency.Android.AndroidTest.androidXTestOrchestrator)
    androidTestImplementation(Dependency.Android.AndroidTest.androidXTestExtJUnit)

    androidTestImplementation(Dependency.Android.AndroidTest.androidXTestEspressoCore)
    androidTestImplementation(Dependency.Android.AndroidTest.androidXTestEspressoIntents)
    androidTestImplementation(Dependency.Android.AndroidTest.androidXTestEspressoWeb)

    androidTestImplementation(Dependency.Android.AndroidTest.androidXTestUiAutomator)

    androidTestImplementation(Dependency.Android.AndroidTest.kakao)

    androidTestImplementation(Dependency.Android.okHttp)
    androidTestImplementation(Dependency.Android.okHttpLoggingInterceptor)
    androidTestImplementation(Dependency.Android.retrofit)
    androidTestImplementation(Dependency.Android.gson)

}
