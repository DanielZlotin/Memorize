package zlotindaniel.memorize;

import zlotindaniel.memorize.data.FirebaseDataLoader;
import zlotindaniel.memorize.data.NonCardsStackShuffler;

public class MemorizeE2EApplication extends MemorizeApplication {

	@Override
	public Config getConfig() {
		return new Config(new FirebaseDataLoader("Test", "Topic1"), new NonCardsStackShuffler());
	}
}
