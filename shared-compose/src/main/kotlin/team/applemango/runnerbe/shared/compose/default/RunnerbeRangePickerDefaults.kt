/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunnerbeRangePickerDefaults.kt] created by Ji Sungbin on 22. 3. 19. 오전 9:33
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.shared.compose.default

import androidx.compose.ui.graphics.Color
import team.applemango.runnerbe.shared.domain.unit.Dp
import team.applemango.runnerbe.xml.rangepicker.RangePickerThumb
import team.applemango.runnerbe.xml.rangepicker.RangePickerTick
import team.applemango.runnerbe.xml.rangepicker.RangePickerTrack

object RunnerbeRangePickerDefaults {
    fun thumb() = RangePickerThumb(
        color =,
        haloColor = Color.Transparent,
        haloRadius = Dp(value = 0.0f)
    )

    fun track() = RangePickerTrack(
        colorActive =,
        colorInactive =,
        step = 5f
    )

    fun tick() = RangePickerTick(color =)
}
