/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [SharedData.kt] created by Ji Sungbin on 22. 3. 24. 오후 8:35
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.android.datastore

import team.applemango.runnerbe.domain.register.runnerbe.model.UserToken
import team.applemango.runnerbe.domain.runningitem.model.common.Locate

object Me {
    // 37.5284197, 126.9327389
    var locate = Locate(
        address = "여의도 한강공원",
        latitude = 126.9327389,
        longitude = 37.5284197
    )

    var token = UserToken()
}