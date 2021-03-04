/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import care.data4life.fhir.r4.model.DomainResource
import care.data4life.integration.app.MainViewModel
import care.data4life.integration.app.R
import care.data4life.sdk.call.Fhir4Record
import care.data4life.sdk.fhir.Fhir3Resource
import care.data4life.sdk.fhir.Fhir4Resource
import care.data4life.sdk.lang.D4LException
import care.data4life.sdk.listener.Callback
import care.data4life.sdk.listener.ResultListener
import care.data4life.sdk.model.Record
import kotlinx.android.synthetic.main.home_fragment.*
import org.threeten.bp.LocalDate

class HomeFragment : Fragment() {

    private lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        home_fhir3_load_all_button.setOnClickListener {
            model.client.fetchRecords(Fhir3Resource::class.java, LocalDate.now().minusYears(1), LocalDate.now(), 50, 0, object : ResultListener<List<Record<Fhir3Resource>>> {
                override fun onSuccess(result: List<Record<Fhir3Resource>>) {
                    val succcess = result

                    // TODO
                }


                override fun onError(exception: D4LException) {
                    Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
                }
            })
        }

        home_fhir4_load_all_button.setOnClickListener {
            model.client.fhir4.search(Fhir4Resource::class.java, emptyList(), LocalDate.now().minusYears(1), LocalDate.now(), 50, 0, object : care.data4life.sdk.call.Callback<List<Fhir4Record<Fhir4Resource>>> {
                override fun onSuccess(result: List<Fhir4Record<Fhir4Resource>>) {

                    val succcess = result

                    // TODO
                }

                override fun onError(exception: D4LException) {
                    Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
                }
            })
        }

        home_logout_button.setOnClickListener {
            model.client.logout(object : Callback {
                override fun onSuccess() {
                    activity?.runOnUiThread { findNavController(this@HomeFragment).navigate(R.id.action_home_screen_to_welcome_screen) }
                }

                override fun onError(exception: D4LException) {
                    Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
