package zlotindaniel.memorize.android.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import zlotindaniel.memorize.BaseActivity;

public class EditTopicActivity extends BaseActivity {

	public static final String INTENT_TOPIC_ID = "INTENT_TOPIC_ID";

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String topicId = getIntent().getStringExtra(INTENT_TOPIC_ID);


		View view = new EditTopicView(this);
		setContentView(view);
	}

}
