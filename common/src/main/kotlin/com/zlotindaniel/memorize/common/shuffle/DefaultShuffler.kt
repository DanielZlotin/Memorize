package com.zlotindaniel.memorize.common.shuffle


class DefaultShuffler : Shuffler {
	override fun <T> shuffle(list: MutableList<T>) = list.shuffle()
}
