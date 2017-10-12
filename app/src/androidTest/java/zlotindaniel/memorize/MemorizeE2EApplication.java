package zlotindaniel.memorize;

import zlotindaniel.memorize.android.FirebaseLoader;
import zlotindaniel.memorize.shuffle.TestShuffler;

public class MemorizeE2EApplication extends MemorizeApplication {

	@Override
	public Config createConfig() {
		return new Config(
				new FirebaseLoader("test"),
				new TestShuffler()
		);
	}
}
