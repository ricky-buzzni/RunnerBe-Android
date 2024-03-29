/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [NicknameWrapper.kt] created by Ji Sungbin on 22. 3. 1. 오후 3:03
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [NickName.kt] created by Ji Sungbin on 22. 2. 28. 오후 9:56
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.user.model.wrapper

/**
 * API Call 에 Body 로 필요함
 *
 * value class 로 하면 당연히 inline 되면서 그냥 String 으로 들어감
 */
data class NicknameWrapper(val nickName: String)
