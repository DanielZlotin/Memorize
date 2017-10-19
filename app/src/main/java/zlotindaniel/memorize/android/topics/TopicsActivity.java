package zlotindaniel.memorize.android.topics;

import android.os.Bundle;
import android.support.annotation.Nullable;

import zlotindaniel.memorize.BaseActivity;
import zlotindaniel.memorize.topics.TopicsInteractor;

public class TopicsActivity extends BaseActivity {
	private TopicsView view;
	private TopicsInteractor interactor;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new TopicsView(this);
		setContentView(view);
		interactor = new TopicsInteractor(view, config.loader);

		interactor.start();
	}
}
