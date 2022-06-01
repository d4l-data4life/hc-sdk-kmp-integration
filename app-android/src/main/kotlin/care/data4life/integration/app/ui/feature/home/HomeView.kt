/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.feature.home

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import care.data4life.integration.app.di.Di
import care.data4life.sdk.SdkContract
import care.data4life.sdk.call.Fhir4Record
import care.data4life.sdk.fhir.Fhir3Resource
import care.data4life.sdk.fhir.Fhir4Resource
import care.data4life.sdk.lang.D4LException
import care.data4life.sdk.listener.ResultListener
import care.data4life.sdk.model.Record
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

@Composable
fun HomeView(
    openAuthentication: () -> Unit
) {
    val client = Di.data.d4lClient.getRaw()
    val context = LocalContext.current

    val onLoadFhir3Clicked = {
        client.fetchRecords(
            Fhir3Resource::class.java,
            SdkContract.CreationDateRange(
                LocalDate.now().minusYears(2),
                LocalDate.now().minusYears(1)
            ),
            SdkContract.UpdateDateTimeRange(
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now()
            ),
            false,
            50,
            0,
            object : ResultListener<List<Record<Fhir3Resource>>> {
                override fun onSuccess(t: List<Record<Fhir3Resource>>) {
                    Toast.makeText(context, "Success: ${t.size}", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: D4LException) {
                    Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
                }
            }
        )

        Unit
    }

    val onLoadFhir4Clicked = {
        client.fhir4.search(
            Fhir4Resource::class.java,
            emptyList(),
            SdkContract.CreationDateRange(
                LocalDate.now().minusYears(2),
                LocalDate.now().minusYears(1)
            ),
            SdkContract.UpdateDateTimeRange(
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now()
            ),
            false,
            50,
            0,
            object : care.data4life.sdk.call.Callback<List<Fhir4Record<Fhir4Resource>>> {
                override fun onSuccess(result: List<Fhir4Record<Fhir4Resource>>) {
                    Toast.makeText(context, "Success: ${result.size} items", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: D4LException) {
                    Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
                }
            }
        )

        Unit
    }

    val onLogoutClicked = {
        openAuthentication()
    }

    HomeContent(
        onLoadFhir3Click = onLoadFhir3Clicked,
        onLoadFhir4Click = onLoadFhir4Clicked,
        onLogoutClick = onLogoutClicked
    )
}
