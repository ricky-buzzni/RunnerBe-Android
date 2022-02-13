/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [MailTemplateItem.kt] created by Ji Sungbin on 22. 2. 13. 오후 9:48
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.domain.mail.model

data class MailTemplateItem(
    val htmlPart: String,
    val from: From,
    val to: List<To>,
    val subject: String,
)
