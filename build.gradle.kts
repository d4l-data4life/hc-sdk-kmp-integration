buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(GradlePlugins.android)
        classpath(GradlePlugins.kotlin)
    }
}

plugins {
    id("scripts.dependency-updates")
    id("scripts.download-scripts")
    id("scripts.quality-spotless")
    id("scripts.versioning")
}

allprojects {
    repositories {
        google()
        mavenCentral()

        gitHub(project)

        d4l()

        jitPack()
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

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "6.8.3"
    distributionType = Wrapper.DistributionType.ALL
}
