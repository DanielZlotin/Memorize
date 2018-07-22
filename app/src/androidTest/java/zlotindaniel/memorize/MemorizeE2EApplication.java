package zlotindaniel.memorize;

import zlotindaniel.memorize.android.*;
import zlotindaniel.memorize.data.shuffle.*;

public class MemorizeE2EApplication extends MemorizeApplication {

	@Override
	public Config createConfig() {
		return new Config(
				true,
				new FirebaseDatabaseAdapter(),
				new TestShuffler()
		);
	}
}
