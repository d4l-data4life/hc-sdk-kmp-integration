/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app

import androidx.lifecycle.ViewModel
import care.data4life.sdk.Data4LifeClient

class MainViewModel : ViewModel() {

    val client: Data4LifeClient = Data4LifeClient.getInstance()

}
