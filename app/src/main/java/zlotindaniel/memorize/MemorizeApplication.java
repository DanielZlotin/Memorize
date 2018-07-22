package zlotindaniel.memorize;

import android.app.*;

import zlotindaniel.memorize.android.*;
import zlotindaniel.memorize.data.shuffle.*;

public class MemorizeApplication extends Application {

	public static MemorizeApplication context;
	private Config config;

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		config = createConfig();
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
