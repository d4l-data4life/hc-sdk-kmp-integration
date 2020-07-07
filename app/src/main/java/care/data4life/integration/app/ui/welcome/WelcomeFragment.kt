/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.welcome

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import care.data4life.integration.app.R
import care.data4life.sdk.Data4LifeClient
import care.data4life.sdk.Data4LifeClient.D4L_AUTH
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.welcome_fragment.*

class WelcomeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.welcome_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        welcome_login_button.setOnClickListener {
            val intent = Data4LifeClient.getInstance().getLoginIntent(context, null)
            startActivityForResult(intent, D4L_AUTH)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == D4L_AUTH) {
            if (resultCode == RESULT_OK) {
                findNavController(this).navigate(R.id.action_welcome_screen_to_home_screen)
            } else {
                this.view?.let { Snackbar.make(it, "Failed to login with D4L", Snackbar.LENGTH_LONG).show() }
            }
        }
    }

}
