/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [settings.gradle.kts] created by Ji Sungbin on 22. 1. 31. 오후 9:40
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

rootProject.name = "RunnerBe"
include(
    ":data",
    ":theme",
    ":domain",
    ":shared",
    ":presentation",
    ":features:mail",
    ":features:mypage",
    ":features:home:write",
    ":features:home:board",
    ":features:home:notification",
    ":features:register:snslogin",
    ":features:register:information"
)
