package de.gesundheitscloud.sdk.integration.screen

import com.agoda.kakao.KButton
import com.agoda.kakao.Screen
import de.gesundheitscloud.sdk.integration.R

class HomeScreen : Screen<HomeScreen>() {

    val logoutButton = KButton { withId(R.id.home_logout_button) }
}
