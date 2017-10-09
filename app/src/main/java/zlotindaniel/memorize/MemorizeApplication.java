package zlotindaniel.memorize;

import android.app.Application;
import android.content.Context;

import zlotindaniel.memorize.extern.FirebaseDataLoader;
import zlotindaniel.memorize.shuffle.DefaultShuffler;

public class MemorizeApplication extends Application {

	private final Config config = createConfig();

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public Config createConfig() {
		return new Config(
				new FirebaseDataLoader("Production", "RT"),
				new DefaultShuffler()
		);
	}

	public final Config getConfig() {
		return config;
	}

	public static int dp(Context context, float px) {
		return (int) (context.getResources().getDisplayMetrics().density * px);
	}
}
