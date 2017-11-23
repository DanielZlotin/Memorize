package zlotindaniel.memorize;

import zlotindaniel.memorize.android.FirebaseDatabaseAdapter;
import zlotindaniel.memorize.shuffle.TestShuffler;

public class MemorizeE2EApplication extends MemorizeApplication {

	@Override
	public Config createConfig() {
		return new Config(
				new FirebaseDatabaseAdapter("test"),
				new TestShuffler()
		);
	}
}
