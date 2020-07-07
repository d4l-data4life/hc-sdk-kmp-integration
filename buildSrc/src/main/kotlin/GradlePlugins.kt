/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2020, HPS Gesundheitscloud gGmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

object GradlePlugins {
    const val android = "com.android.tools.build:gradle:${Versions.GradlePlugin.android}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.GradlePlugin.kotlin}"
}

fun PluginDependenciesSpec.dependencyUpdates(): PluginDependencySpec =
        id("com.github.ben-manes.versions").version(Versions.GradlePlugin.dependencyUpdates)


fun PluginDependenciesSpec.androidApp(): PluginDependencySpec =
        id("com.android.application")

fun PluginDependenciesSpec.kotlinAndroid(): PluginDependencySpec =
        id("kotlin-android")

fun PluginDependenciesSpec.kotlinAndroidExtensions(): PluginDependencySpec =
        id("kotlin-android-extensions")
