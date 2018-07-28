package com.zlotindaniel.memorize.common

import org.assertj.core.api.Assertions.*
import kotlin.test.Test

class EnvTest {
	@Test
	fun `it just works`() {
		assertThat(1+1).isNotNull()
				.isNotZero()
				.isNotNegative()
				.isPositive()
				.isEqualTo(2)
	}
}
