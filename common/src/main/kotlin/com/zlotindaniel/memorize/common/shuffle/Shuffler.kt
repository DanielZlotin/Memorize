package com.zlotindaniel.memorize.common.shuffle

interface Shuffler {
	fun <T> shuffle(list: MutableList<T>)
}
