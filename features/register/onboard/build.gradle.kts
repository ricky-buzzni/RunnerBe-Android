/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [build.gradle.kts] created by Ji Sungbin on 22. 1. 31. 오후 9:40
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

plugins {
    installLibraryDfmHiltTest(excludeHilt = true)
}

android {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.Main
    }
}

dependencies {
    implementation(Dependencies.EachUi.Browser)
    implementationProject(ProjectConstants.Data)
    implementationProject(ProjectConstants.HomeBoard)
    implementationProject(ProjectConstants.XmlSuperWheelPicker)
    installSharedComposeOrbitHiltTest(excludeHilt = true)
}
