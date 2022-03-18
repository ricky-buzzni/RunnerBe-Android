/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [RunningItemWriteLevelOne.kt] created by Ji Sungbin on 22. 3. 18. 오전 7:14
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.component.step

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import java.util.Calendar
import java.util.Date
import team.applemango.runnerbe.domain.runningitem.common.RunningItemType
import team.applemango.runnerbe.feature.home.write.R
import team.applemango.runnerbe.feature.home.write.component.util.DateCache.plusDayAndCaching
import team.applemango.runnerbe.feature.home.write.constant.TimeType
import team.applemango.runnerbe.feature.home.write.model.RunningDate
import team.applemango.runnerbe.feature.home.write.model.toDateString
import team.applemango.runnerbe.shared.compose.component.RunnerbeDialog
import team.applemango.runnerbe.shared.compose.default.RunnerbeSuperWheelPickerColors
import team.applemango.runnerbe.shared.compose.default.RunnerbeSuperWheelPickerTextStyle
import team.applemango.runnerbe.shared.compose.theme.ColorAsset
import team.applemango.runnerbe.shared.compose.theme.Typography
import team.applemango.runnerbe.shared.domain.extension.toCalendar
import team.applemango.runnerbe.shared.domain.unit.em
import team.applemango.runnerbe.shared.domain.unit.px
import team.applemango.runnerbe.shared.extension.collectWithLifecycle
import team.applemango.runnerbe.xml.superwheelpicker.integration.SuperWheelPicker

private val DefaultFieldShape = RoundedCornerShape(6.dp)

private val now = Date()

@Composable
internal fun RunningItemWriteLevelOne(
    runningItemType: RunningItemType,
    fieldsAllInputStateChange: (state: Boolean) -> Unit,
) {
    val runningDate = remember { RunningDate() }
    val lifecycleOwner = LocalLifecycleOwner.current

    var titleField by remember { mutableStateOf(TextFieldValue()) }
    var runningDateStringState by remember { mutableStateOf<String?>(null) }
    val runningDateStringDefaultState by remember {
        mutableStateOf(RunningDate.getDefault(runningItemType).toString())
    }
    var runningTimeStringState by remember { mutableStateOf("0 시간 20 분") }
    val fieldsFillState = remember { mutableStateListOf(false, false, false) }

    var runningDatePickerDialogVisible by remember { mutableStateOf(false) }
    var runningTimePickerDialogVisible by remember { mutableStateOf(false) }
    var titleErrorVisible by remember { mutableStateOf(false) }

    LaunchedEffect(fieldsFillState) {
        snapshotFlow { fieldsFillState }
            .collectWithLifecycle(lifecycleOwner) {
                fieldsAllInputStateChange(fieldsFillState.all { true })
            }
    }

    RunningDatePickerDialog(
        visible = runningDatePickerDialogVisible,
        onDismissRequest = { runningDatePickerDialogVisible = false },
        onRunningDateChange = { field ->
            with(runningDate) {
                when (field) {
                    is RunningDate.Companion.Field.Date -> {
                        setDate(field.value)
                    }
                    is RunningDate.Companion.Field.TimeType -> {
                        setTimeType(field.value)
                    }
                    is RunningDate.Companion.Field.Hour -> {
                        setHour(field.value)
                    }
                    is RunningDate.Companion.Field.Minute -> {
                        setMinute(field.value)
                    }
                }
            }
        }
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.runningitemwrite_label_title),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
        TextField(
            modifier = Modifier.padding(top = 12.dp),
            value = titleField,
            shape = DefaultFieldShape,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = ColorAsset.G5_5),
            onValueChange = { newTitleValue ->
                if (newTitleValue.text.isNotEmpty()) {
                    fieldsFillState[0] = true
                }
                if (newTitleValue.text.length <= 30) {
                    titleField = newTitleValue
                    titleErrorVisible = false
                } else {
                    titleErrorVisible = true
                }
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.runningitemwrite_placeholder_title),
                    style = Typography.Body14R.copy(color = ColorAsset.G3_5)
                )
            },
            textStyle = Typography.Body14R.copy(color = ColorAsset.G1)
        )
        AnimatedVisibility(
            modifier = Modifier.padding(top = 8.dp),
            visible = titleErrorVisible
        ) {
            Text(
                text = stringResource(R.string.runningitemwrite_error_title_length),
                style = Typography.Body12R.copy(color = ColorAsset.ErrorLight)
            )
        }
        Divider(modifier = Modifier.padding(vertical = 20.dp), color = ColorAsset.G6)
        Text(
            text = stringResource(R.string.runningitemwrite_label_date),
            style = Typography.Body14R.copy(color = ColorAsset.G3_5)
        )
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxSize()
                .clip(DefaultFieldShape)
                .background(color = ColorAsset.G5_5)
                .clickable {
                    runningDatePickerDialogVisible = true
                }
                .padding(horizontal = 32.dp)
        ) {
            val (calendarIcon, dateString, arrowIcon) = createRefs()
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .constrainAs(calendarIcon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                painter = painterResource(R.drawable.ic_round_schedule_24),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier
                    .constrainAs(dateString) {
                        start.linkTo(calendarIcon.end, 16.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .background(color = ColorAsset.G5_5),
                text = runningDateStringState ?: runningDateStringDefaultState,
                style = Typography.Body14R.copy(color = ColorAsset.G1)
            )
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .constrainAs(arrowIcon) {
                        start.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                painter = painterResource(R.drawable.ic_round_chevron_right),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Divider(modifier = Modifier.padding(vertical = 20.dp), color = ColorAsset.G6)
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxSize()
                .clip(DefaultFieldShape)
                .background(color = ColorAsset.G5_5)
                .clickable {
                    runningTimePickerDialogVisible = true
                }
                .padding(horizontal = 32.dp)
        ) {
            val (clockIcon, dateString, arrowIcon) = createRefs()
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .constrainAs(clockIcon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                painter = painterResource(R.drawable.ic_round_time_24),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier
                    .constrainAs(dateString) {
                        start.linkTo(clockIcon.end, 16.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .background(color = ColorAsset.G5_5),
                text = runningTimeStringState,
                style = Typography.Body14R.copy(color = ColorAsset.G1)
            )
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .constrainAs(arrowIcon) {
                        start.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                painter = painterResource(R.drawable.ic_round_chevron_right),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Divider(modifier = Modifier.padding(vertical = 20.dp), color = ColorAsset.G6)
    }
}

@Composable
private fun RunningDatePickerDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onRunningDateChange: (field: RunningDate.Companion.Field) -> Unit,
) {
    RunnerbeDialog(
        visible = visible,
        onDismissRequest = onDismissRequest,
        positiveButton = {
            textBuilder = {
                stringResource(R.string.runningitemwrite_dialog_button_decision)
            }
            onClick = {
                onDismissRequest()
            }
        },
        content = {
            Row(
                modifier = Modifier.matchParentSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DateStringPicker { dateString ->
                    onRunningDateChange(RunningDate.Companion.Field.Date(dateString))
                }
                TimeTypePicker { timeType ->
                    onRunningDateChange(RunningDate.Companion.Field.TimeType(timeType))
                }
                Row {
                    HourPicker { hour ->
                        onRunningDateChange(RunningDate.Companion.Field.Hour(hour))
                    }
                    Text(text = ":", style = Typography.Custom.SuperWheelPicker)
                    MinutePicker { minute ->
                        onRunningDateChange(RunningDate.Companion.Field.Minute(minute))
                    }
                }
            }
        }
    )
}

@Composable
private fun DateStringPicker(
    onDateStringSelectChange: (dateString: String) -> Unit,
) {
    SuperWheelPicker(
        colors = RunnerbeSuperWheelPickerColors,
        textStyle = RunnerbeSuperWheelPickerTextStyle.copy(
            textSize = 18.px,
            letterSpacing = (-0.18).em
        ),
        wheelItemCount = 5,
        range = 0..6,
        value = 0,
        onValueChange = { _, dayUpper ->
            val newDateString = now.plusDayAndCaching(dayUpper).toDateString()
            onDateStringSelectChange(newDateString)
        },
        onTextRender = { value ->
            now.plusDayAndCaching(value).toDateString()
        }
    )
}

@Composable
private fun TimeTypePicker(
    onTimeTypeSelectChange: (timeType: TimeType) -> Unit,
) {
    SuperWheelPicker(
        colors = RunnerbeSuperWheelPickerColors,
        textStyle = RunnerbeSuperWheelPickerTextStyle.copy(
            textSize = 18.px,
            letterSpacing = (-0.18).em
        ),
        wheelItemCount = 2,
        range = 0..1,
        value = 0,
        onValueChange = { _, timeTypeIndex ->
            onTimeTypeSelectChange(TimeType.values()[timeTypeIndex])
        },
        onTextRender = { value ->
            TimeType.values()[value].string
        }
    )
}

@Composable
private fun HourPicker(
    onHourSelectChange: (hour: Int) -> Unit,
) {
    SuperWheelPicker(
        colors = RunnerbeSuperWheelPickerColors,
        textStyle = RunnerbeSuperWheelPickerTextStyle.copy(
            textSize = 18.px,
            letterSpacing = (-0.18).em
        ),
        wheelItemCount = 5,
        range = 1..12,
        value = now.toCalendar().get(Calendar.HOUR_OF_DAY),
        onValueChange = { _, hour ->
            onHourSelectChange(hour)
        },
    )
}

@Composable
private fun MinutePicker(
    onMinuteSelectChange: (hour: Int) -> Unit,
) {
    SuperWheelPicker(
        colors = RunnerbeSuperWheelPickerColors,
        textStyle = RunnerbeSuperWheelPickerTextStyle.copy(
            textSize = 18.px,
            letterSpacing = (-0.18).em
        ),
        wheelItemCount = 5,
        range = 0..12,
        value = now.toCalendar().get(Calendar.MINUTE),
        onValueChange = { _, minute ->
            onMinuteSelectChange(minute)
        },
        onTextRender = { value ->
            val minute = value * 5
            if (minute == 60) {
                "0"
            } else {
                minute.toString()
            }
        }
    )
}
