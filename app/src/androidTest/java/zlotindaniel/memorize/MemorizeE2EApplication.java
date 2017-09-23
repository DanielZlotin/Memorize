package zlotindaniel.memorize;

import zlotindaniel.memorize.data.NonCardsStackShuffler;
import zlotindaniel.memorize.mocks.E2EFirebaseDataLoader;

public class MemorizeE2EApplication extends MemorizeApplication {

	@Override
	public Config getConfig() {
		return new Config(new E2EFirebaseDataLoader(), new NonCardsStackShuffler());
	}
}
