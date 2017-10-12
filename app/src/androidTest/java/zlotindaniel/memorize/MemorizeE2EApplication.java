package zlotindaniel.memorize;

import zlotindaniel.memorize.extern.FirebaseDataLoader;
import zlotindaniel.memorize.shuffle.TestShuffler;

public class MemorizeE2EApplication extends MemorizeApplication {

	@Override
	public Config createConfig() {
		return new Config(
				new FirebaseDataLoader("test", "-KwBDgfCOiGiqgduikxF"),
				new TestShuffler()
		);
	}
}
