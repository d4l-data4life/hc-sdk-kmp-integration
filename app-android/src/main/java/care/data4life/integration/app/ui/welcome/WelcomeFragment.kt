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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import care.data4life.integration.app.MainViewModel
import care.data4life.integration.app.R
import care.data4life.integration.app.databinding.WelcomeFragmentBinding
import care.data4life.sdk.Data4LifeClient
import care.data4life.sdk.Data4LifeClient.D4L_AUTH
import care.data4life.sdk.lang.D4LException
import care.data4life.sdk.listener.ResultListener
import com.google.android.material.snackbar.Snackbar

class WelcomeFragment : Fragment() {

    private var _binding: WelcomeFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WelcomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.welcomeLoginButton.setOnClickListener {
            val intent = Data4LifeClient.getInstance().getLoginIntent(context, null)
            startActivityForResult(intent, D4L_AUTH)
        }
    }

    override fun onResume() {
        super.onResume()

        model.client.isUserLoggedIn(object : ResultListener<Boolean> {
            override fun onSuccess(t: Boolean) {
                if (t) {
                    findNavController(this@WelcomeFragment).navigate(R.id.action_welcome_screen_to_home_screen)
                }
            }

            override fun onError(exception: D4LException) {
                Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == D4L_AUTH) {
            if (resultCode == RESULT_OK) {
                findNavController(this).navigate(R.id.action_welcome_screen_to_home_screen)
            } else {
                this.view?.let {
                    Snackbar.make(it, "Failed to login with D4L", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
