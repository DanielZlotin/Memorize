package com.zlotindaniel.memorize.common.shuffle

import org.assertj.core.api.Assertions.*
import kotlin.test.Test

class TestShufflerTest {
	@Test
	fun `sort descending by a toString comparison`() {
		val uut = TestShuffler()

		val intList = mutableListOf(1, 2, 3)
		uut.shuffle(intList)
		assertThat(intList).containsExactly(3, 2, 1)

		val stringList = mutableListOf("a", "b", "c")
		uut.shuffle(stringList)
		assertThat(stringList).containsExactly("c", "b", "a")
	}
}
