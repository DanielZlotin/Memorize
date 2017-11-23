package zlotindaniel.memorize.android.edit;

import android.os.*;
import android.support.annotation.*;
import android.view.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.edit.*;
import zlotindaniel.memorize.topics.*;

public class EditTopicActivity extends BaseActivity {

	private EditTopicView view;
	private EditTopicInteractor interactor;

	public static final String INTENT_TOPIC_ID = "INTENT_TOPIC_ID";
	public static final String INTENT_TOPIC_NAME = "INTENT_TOPIC_NAME";

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String topicId = getIntent().getStringExtra(INTENT_TOPIC_ID);
		String topicName = getIntent().getStringExtra(INTENT_TOPIC_NAME);
		if (topicId == null || topicName == null) {
			finish();
			return;
		}
		Topic topic = new Topic(topicId, topicName);

		view = new EditTopicView(this);
		setContentView(view);
		interactor = new EditTopicInteractor(topic, view, new DatabaseService(getUserId(), config.database));
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
