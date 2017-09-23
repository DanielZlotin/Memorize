package zlotindaniel.memorize.topics;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import zlotindaniel.memorize.Config;
import zlotindaniel.memorize.MemorizeApplication;

public class TopicsActivity extends Activity {

	private Config config;
	private TopicsView view;
	private TopicsInteractor interactor;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		config = ((MemorizeApplication) getApplication()).getConfig();

		view = new TopicsView(this);
		interactor = new TopicsInteractor(view, config.dataLoader);

		setContentView(view);

		interactor.start();
	}
}
