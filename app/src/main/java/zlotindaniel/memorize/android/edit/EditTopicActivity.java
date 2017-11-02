package zlotindaniel.memorize.android.edit;

import android.os.*;
import android.support.annotation.*;
import android.view.*;

import com.google.common.base.*;

import zlotindaniel.memorize.*;

public class EditTopicActivity extends BaseActivity {

	public static final String INTENT_TOPIC_ID = "INTENT_TOPIC_ID";
	public static final String INTENT_TOPIC_NAME = "INTENT_TOPIC_NAME";

	private EditTopicView view;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String topicId = Preconditions.checkNotNull(getIntent().getStringExtra(INTENT_TOPIC_ID));
		String topicName = Preconditions.checkNotNull(getIntent().getStringExtra(INTENT_TOPIC_NAME));

		view = new EditTopicView(this, topicName);
		setContentView(view);
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
