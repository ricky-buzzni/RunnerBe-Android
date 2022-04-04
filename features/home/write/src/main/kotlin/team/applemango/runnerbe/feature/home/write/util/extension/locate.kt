/*
 * RunnerBe © 2022 Team AppleMango. all rights reserved.
 * RunnerBe license is under the MIT.
 *
 * [locate.kt] created by Ji Sungbin on 22. 3. 19. 오전 3:38
 *
 * Please see: https://github.com/applemango-runnerbe/RunnerBe-Android/blob/main/LICENSE.
 */

package team.applemango.runnerbe.feature.home.write.util.extension

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import team.applemango.runnerbe.domain.runningitem.model.common.Locate
import java.util.Locale

internal fun Locate.toLatLng() = LatLng(latitude, longitude)

internal fun LatLng.toAddress(context: Context): String {
    val geoCoder = Geocoder(context, Locale.KOREA)
    val addressList = geoCoder.getFromLocation(latitude, longitude, 2)
    return addressList[1].getAddressLine(0).replace("대한민국 ", "")
}
