package zlotindaniel.memorize;

import android.app.*;

import java.util.*;

import zlotindaniel.memorize.android.*;
import zlotindaniel.memorize.shuffle.*;

public class MemorizeApplication extends Application {

	public static MemorizeApplication context;
	private Stack<Object> activityStore = new Stack<>();
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

	public <T> void acitivityStore(T obj) {
		activityStore.push(obj);
	}


	public <T> T activityLoad() {
		return (T) activityStore.pop();
	}
}
