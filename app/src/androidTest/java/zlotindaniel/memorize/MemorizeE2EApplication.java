package zlotindaniel.memorize;

import zlotindaniel.memorize.data.FirebaseDataLoader;
import zlotindaniel.memorize.shuffle.ReverseSortingCardShuffler;

public class MemorizeE2EApplication extends MemorizeApplication {

	@Override
	public Config createConfig() {
		return new Config(
				new FirebaseDataLoader("Test", "Topic1"),
				new ReverseSortingCardShuffler()
		);
	}
}
