/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2018, HPS Gesundheitscloud gGmbH
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
                println(documentsCount)
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