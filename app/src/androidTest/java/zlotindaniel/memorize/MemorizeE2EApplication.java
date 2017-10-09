package zlotindaniel.memorize;

import zlotindaniel.memorize.data.Card;
import zlotindaniel.memorize.data.FirebaseDataLoader;
import zlotindaniel.memorize.shuffle.TestShuffler;

public class MemorizeE2EApplication extends MemorizeApplication {

	@Override
	public Config createConfig() {
		return new Config(
				new FirebaseDataLoader("Test", "Topic1"),
				new TestShuffler<Card>()
		);
	}
}
