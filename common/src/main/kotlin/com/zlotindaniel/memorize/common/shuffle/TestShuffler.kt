package com.zlotindaniel.memorize.common.shuffle

class TestShuffler : Shuffler {
	override fun <T> shuffle(list: MutableList<T>) = list.sortByDescending { it.toString() }
}
