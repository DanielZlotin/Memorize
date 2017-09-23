package zlotindaniel.memorize;

import zlotindaniel.memorize.cards.NonCardsStackShuffler;
import zlotindaniel.memorize.data.FirebaseDataLoader;

public class MemorizeE2EApplication extends MemorizeApplication {

	@Override
	public Config getConfig() {
		return new Config(new FirebaseDataLoader("Test"), new NonCardsStackShuffler());
	}
}
