package zlotindaniel.memorize;

import zlotindaniel.memorize.android.FirebaseNetwork;
import zlotindaniel.memorize.shuffle.TestShuffler;

public class MemorizeE2EApplication extends MemorizeApplication {

	@Override
	public Config createConfig() {
		return new Config(
				new FirebaseNetwork("test"),
				new TestShuffler()
		);
	}
}
