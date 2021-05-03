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
    id("scripts.dependency-updates")
    id("scripts.download-scripts")
    id("scripts.versioning")
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/d4l-data4life/hc-util-sdk-kmp")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("PACKAGE_REGISTRY_USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("PACKAGE_REGISTRY_TOKEN")
            }
        }
        maven {
            url = uri("https://maven.pkg.github.com/d4l-data4life/hc-fhir-sdk-java")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("PACKAGE_REGISTRY_USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("PACKAGE_REGISTRY_TOKEN")
            }
        }
        maven {
            url = uri("https://maven.pkg.github.com/d4l-data4life/hc-fhir-helper-sdk-kmp")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("PACKAGE_REGISTRY_USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("PACKAGE_REGISTRY_TOKEN")
            }
        }
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
    gradleVersion = "6.8.3"
    distributionType = Wrapper.DistributionType.ALL
}
