/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import care.data4life.integration.app.MainViewModel
import care.data4life.integration.app.R
import care.data4life.sdk.lang.D4LException
import care.data4life.sdk.listener.Callback
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    private var model: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        home_logout_button.setOnClickListener {
            model?.client?.logout(object : Callback {
                override fun onSuccess() {
                    activity?.runOnUiThread { findNavController(this@HomeFragment).navigate(R.id.action_home_screen_to_welcome_screen) }
                }

                override fun onError(exception: D4LException) {

                }
            })
        }
    }
}
