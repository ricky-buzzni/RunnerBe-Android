/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [KakaoLogin.kt] created by Ji Sungbin on 22. 2. 5. 오후 10:36
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.data.login.repository

import android.app.Activity
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import kotlinx.coroutines.suspendCancellableCoroutine
import team.applemango.runnerbe.data.util.extension.failure
import team.applemango.runnerbe.data.util.extension.success
import team.applemango.runnerbe.domain.login.repository.AccessTokenRepository
import kotlin.coroutines.resume

private const val NAVER_ACCESS_TOKEN_NULL = "Naver access token is null."
private const val RESPONSE_NOTHING = "Kakao API response is nothing."

// must be activity context
class AccessTokenRepositoryImpl(private val activityContext: Activity) :
    AccessTokenRepository {
    override suspend fun getKakao(): String {
        return if (UserApiClient.instance.isKakaoTalkLoginAvailable(activityContext)) {
            loginWithKakaoTalk(activityContext)
        } else {
            loginWithWebView(activityContext)
        }
    }

    private suspend fun loginWithKakaoTalk(activityContext: Activity): String {
        return suspendCancellableCoroutine<Result<String>> { continuation ->
            UserApiClient.instance.loginWithKakaoTalk(activityContext) { token, error ->
                logeukes { listOf("카카오", token, error) }
                continuation.resume(
                    when {
                        error != null -> failure(error)
                        token != null -> success(token.accessToken)
                        else -> failure(RESPONSE_NOTHING)
                    }
                )
            }
        }.getOrThrow()
    }

    private suspend fun loginWithWebView(activityContext: Activity): String {
        return suspendCancellableCoroutine<Result<String>> { continuation ->
            UserApiClient.instance.loginWithKakaoAccount(activityContext) { token, error ->
                logeukes { listOf("카카오", token, error) }
                continuation.resume(
                    when {
                        error != null -> failure(error)
                        token != null -> success(token.accessToken)
                        else -> failure(RESPONSE_NOTHING)
                    }
                )
            }
        }.getOrThrow()
    }

    override suspend fun getNaver(): String {
        return suspendCancellableCoroutine<Result<String>> { continuation ->
            NaverIdLoginSDK.authenticate(
                activityContext,
                object : OAuthLoginCallback {
                    override fun onSuccess() {
                        val token = NaverIdLoginSDK.getAccessToken()
                        logeukes { listOf("네이버", token) }
                        continuation.resume(
                            token?.let { success(it) }
                                ?: failure(NAVER_ACCESS_TOKEN_NULL)
                        )
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        logeukes(type = LoggerType.E) { listOf(httpStatus, message) }
                        continuation.resume(failure("$httpStatus ($message)"))
                    }

                    override fun onError(errorCode: Int, message: String) {
                        onFailure(errorCode, message)
                    }
                }
            )
        }.getOrThrow()
    }
}
