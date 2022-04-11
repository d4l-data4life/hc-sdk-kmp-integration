/*
 * Copyright (c) 2020 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.testUtils

import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object NetworkUtil {
    private const val GOOGLE_PUB_DNS = "8.8.8.8"
    private const val DNS_PORT = 53

    fun isOnline(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockaddr = InetSocketAddress(GOOGLE_PUB_DNS, DNS_PORT)

            sock.connect(sockaddr, timeoutMs)
            sock.close()

            true
        } catch (e: IOException) {
            false
        }
    }
}
