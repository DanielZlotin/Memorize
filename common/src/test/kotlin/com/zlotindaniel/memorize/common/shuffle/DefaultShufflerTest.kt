package com.zlotindaniel.memorize.common.shuffle

import org.assertj.core.api.Assertions.*
import kotlin.test.Test

class DefaultShufflerTest {
	@Test
	fun `shuffle randomly`() {
		val uut = DefaultShuffler()
		val seen = mutableListOf<String>()
		val all = mutableListOf("a", "b", "c")

		repeat(10000) {
			uut.shuffle(all)
			seen.add(all[0])
			if (seen.containsAll(all)) return
		}
		fail("Can't find all elements in 10,000 iterations")
	}
}
