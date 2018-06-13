package de.gesundheitscloud.sdk.integration.ui.count

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.gesundheitscloud.sdk.HCException
import de.gesundheitscloud.sdk.integration.MainViewModel
import de.gesundheitscloud.sdk.integration.R.layout.count_fragment
import de.gesundheitscloud.sdk.listener.ResultListener
import kotlinx.android.synthetic.main.count_fragment.*


class CountFragment : Fragment() {
    private var model: MainViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(count_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model!!.client.countDocuments(object : ResultListener<Int> {
            override fun onSuccess(documentsCount: Int?) {
                activity?.runOnUiThread {
                    count_documents_number.text = documentsCount.toString()
                }
            }

            override fun onError(error: HCException?) {

            }
        })

        model!!.client.countReports(object : ResultListener<Int> {
            override fun onSuccess(reportsCount: Int?) {
                activity?.runOnUiThread {
                    count_reports_number.text = reportsCount.toString()
                }
            }

            override fun onError(error: HCException?) {

            }
        })

        model!!.client.countObservations(object : ResultListener<Int> {
            override fun onSuccess(observationsCount: Int?) {
                activity?.runOnUiThread {
                    count_observations_number.text = observationsCount.toString()
                }
            }

            override fun onError(error: HCException?) {

            }
        })

        model!!.client.countAll(object : ResultListener<Int> {
            override fun onSuccess(totalCount: Int?) {
                activity?.runOnUiThread {
                    count_total_number.text = totalCount.toString()
                }
            }

            override fun onError(error: HCException?) {

            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
}