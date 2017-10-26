package zlotindaniel.memorize;

import android.app.Application;

import zlotindaniel.memorize.android.FirebaseLoader;
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
				new FirebaseLoader("production"),
				new DefaultShuffler()
		);
	}

	public final Config getConfig() {
		return config;
	}
}
