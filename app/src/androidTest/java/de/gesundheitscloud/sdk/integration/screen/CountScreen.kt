package de.gesundheitscloud.sdk.integration.screen

import com.agoda.kakao.KButton
import com.agoda.kakao.KTextView
import com.agoda.kakao.Screen
import de.gesundheitscloud.sdk.integration.R

class CountScreen : Screen<CountScreen>() {

    val countButton = KButton {withId(R.id.home_count_button)}
    val documentsCount = KTextView { withId(R.id.count_documents_number) }
    val reportsCount = KTextView { withId(R.id.count_reports_number) }
    val observationsCount = KTextView { withId(R.id.count_observations_number) }
    val totalCount = KTextView { withId(R.id.count_total_number) }


}