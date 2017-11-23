package zlotindaniel.memorize;

import android.app.*;

import zlotindaniel.memorize.android.*;
import zlotindaniel.memorize.shuffle.*;

public class MemorizeApplication extends Application {

	public static MemorizeApplication context;
	private final Config config = createConfig();

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
	}

	public Config createConfig() {
		return new Config(
				BuildConfig.DEBUG,
				new FirebaseDatabaseAdapter(),
				new DefaultShuffler()
		);
	}

	public final Config getConfig() {
		return config;
	}
}
