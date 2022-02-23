/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainRepository.kt] created by Ji Sungbin on 22. 2. 24. 오전 3:51
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.main.repository

import team.applemango.runnerbe.domain.main.model.RunningItem

interface MainRepository {
    suspend fun loadRunningItems(
        itemType: String,
        includeEndItems: Boolean,
        itemFilter: String,
        distance: String,
        gender: String,
        maxAge: String,
        minAge: String,
        job: String,
        longitude: Float,
        latitude: Float,
        keyword: String,
    ): List<RunningItem>
}
