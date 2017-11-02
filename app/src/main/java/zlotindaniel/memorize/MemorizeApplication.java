package zlotindaniel.memorize;

import android.app.Application;

import zlotindaniel.memorize.android.FirebaseNetwork;
import zlotindaniel.memorize.shuffle.DefaultShuffler;

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
				new FirebaseNetwork("production"),
				new DefaultShuffler()
		);
	}

	public final Config getConfig() {
		return config;
	}
}
