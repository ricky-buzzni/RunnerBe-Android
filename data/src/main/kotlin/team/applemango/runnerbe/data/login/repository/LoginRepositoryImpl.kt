/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [LoginRepositoryImpl.kt] created by Ji Sungbin on 22. 2. 7. 오후 7:16
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.login.repository

import team.applemango.runnerbe.data.login.mapper.toDomain
import team.applemango.runnerbe.data.util.extension.requireResponse
import team.applemango.runnerbe.data.util.loginApi
import team.applemango.runnerbe.domain.login.model.AccessToken
import team.applemango.runnerbe.domain.login.model.User
import team.applemango.runnerbe.domain.login.repository.LoginRepository

class LoginRepositoryImpl : LoginRepository {
    override suspend fun request(platformName: String, accessToken: AccessToken): User {
        val request = loginApi.request(
            platformName = platformName,
            accessToken = accessToken
        )
        return request.requireResponse(platformName).toDomain()
    }
}