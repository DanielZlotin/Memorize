package zlotindaniel.memorize.android.edit;

import android.os.*;
import android.support.annotation.*;
import android.view.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.edit.*;
import zlotindaniel.memorize.topics.*;

public class EditTopicActivity extends BaseActivity {

	private EditTopicView view;
	private EditTopicInteractor interactor;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Topic topic = MemorizeApplication.context.activityLoad();

		view = new EditTopicView(this, topic.getName());
		setContentView(view);
		interactor = new EditTopicInteractor(topic, view, config.network);
		interactor.start();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		view.onCreateMenu(menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		view.onClickMenu(item);
		return super.onOptionsItemSelected(item);
	}
}
