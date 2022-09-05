/*
 * Copyright (c) 2022 D4L data4life gGmbH - All rights reserved.
 */

package care.data4life.integration.app.di

import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test

class DiTest {

    @Test
    fun di_implements_contract() {
        assertInstanceOf(DiContract::class.java, Di)
    }
}
