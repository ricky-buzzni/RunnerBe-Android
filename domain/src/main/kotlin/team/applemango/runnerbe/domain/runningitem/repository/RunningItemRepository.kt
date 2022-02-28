/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MainRepository.kt] created by Ji Sungbin on 22. 2. 24. 오전 3:51
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.runningitem.repository

import team.applemango.runnerbe.domain.runningitem.common.BaseResult
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItem
import team.applemango.runnerbe.domain.runningitem.model.runningitem.RunningItemApiBodyData
import team.applemango.runnerbe.domain.runningitem.model.runningitem.information.RunningItemInformation

interface RunningItemRepository {
    /**
     * 러닝 아이템 작성 (6번 API)
     *
     * @return 인증 전 유저도 아이템 작성은 요청할 수 있으니
     * `아직 인증되지 않음` 상태를 포함한 [BaseResult] 를 리턴함
     */
    suspend fun write(
        jwt: String,
        userId: Int,
        item: RunningItemApiBodyData,
    ): BaseResult

    /**
     * 러닝 아이템 리스트 조회 (7번 API)
     */
    suspend fun loadItems(
        itemType: String,
        includeEndItems: Boolean,
        itemFilter: String,
        distance: String,
        gender: String,
        minAge: String,
        maxAge: String,
        job: String,
        latitude: Float,
        longitude: Float,
        keyword: String,
    ): List<RunningItem>

    /**
     * 러닝 아이템 상세 정보 조회 (8번 API)
     *
     * @return 사원증 인증이 된 유저라면 [RunningItemInformation] 을 반환하고,
     * 그렇지 않은 유저라면 null 을 반환함
     */
    suspend fun loadInformation(
        jwt: String,
        userId: Int,
        postId: Int,
    ): RunningItemInformation?

    /**
     * 러너 모집 마감 (러닝 아이템 작성자 전용, 10번 API)
     *
     * @return 러너 모집을 마감하기 위해선 러닝 아이템을 작성해야 함
     * 러닝 아이템을 작성하는건 인증된 회원만 가능하므로 이 API(러너 모집 마감)을
     * 호출할 수 있는 상태는 무조건 유저가 인증이 된 상태임
     * 따라서 마감 성공 여부를 나타내는 [Boolean] 값만 리턴함
     */
    suspend fun finish(
        jwt: String,
        postId: Int,
    ): Boolean
}
