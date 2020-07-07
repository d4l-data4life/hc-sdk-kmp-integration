/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

/**
 * An activity that inflates a layout that has a NavHostFragment.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun finish() {
        //ignore so that Android test runner can't kill activity after each test
    }

    fun explicitFinish() {
        super.finish()
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.navigation_host_fragment).navigateUp()

}
