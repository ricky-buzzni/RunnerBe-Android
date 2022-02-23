/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainService.kt] created by Ji Sungbin on 22. 2. 24. 오전 2:59
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.main.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.applemango.runnerbe.data.main.model.RunningItemResponse

interface MainService {
    @GET("/users/main/{runningTag}")
    suspend fun loadRunningItems(
        @Path("runningTag") itemType: String,
        @Query("whetherEnd") includeEndItems: Boolean,
        @Query("filter") itemFilter: String,
        @Query("distanceFilter") distance: String,
        @Query("genderFilter") gender: String,
        @Query("ageFilterMax") maxAge: String,
        @Query("ageFilterMin") minAge: String,
        @Query("jobFilter") job: String,
        @Query("userLongitude") longitude: Float,
        @Query("userLatitude") latitude: Float,
        @Query("keywordSearch") keyword: String,
    ): Response<RunningItemResponse>
}
