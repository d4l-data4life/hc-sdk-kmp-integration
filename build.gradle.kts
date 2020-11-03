buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(GradlePlugins.android)
        classpath(GradlePlugins.kotlin)
    }
}

plugins {
    dependencyUpdates()
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        jcenter()
    }

    // FIXME remove if dependency conflict is solved
    configurations.all {
        resolutionStrategy {
            force(Dependencies.Android.okHttp)
            force(Dependencies.Android.okHttpLoggingInterceptor)
            force(Dependencies.Android.retrofit)
        }
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "6.7"
    distributionType = Wrapper.DistributionType.ALL
}
