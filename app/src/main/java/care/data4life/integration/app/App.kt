/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app

import android.app.Application
import care.data4life.sdk.Data4LifeClient
import com.jakewharton.threetenabp.AndroidThreeTen

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Data4LifeClient.init(this)
        AndroidThreeTen.init(this)
    }
}
