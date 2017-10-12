package zlotindaniel.memorize.android.topics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import zlotindaniel.memorize.BaseActivity;
import zlotindaniel.memorize.android.cards.CardsActivity;
import zlotindaniel.memorize.data.OnSuccess;
import zlotindaniel.memorize.topics.Topic;
import zlotindaniel.memorize.topics.TopicsInteractor;

public class TopicsActivity extends BaseActivity {
	private TopicsView view;
	private TopicsInteractor interactor;

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new TopicsView(this);
		interactor = new TopicsInteractor(view, config.loader);
		view.setOnTopicClick(new OnSuccess<Topic>() {
			@Override
			public void success(final Topic topic) {
				pushTopicsCards(topic.getId());
			}
		});

		setContentView(view);

		interactor.start();
	}

	public void pushTopicsCards(final String topicId) {
		Intent intent = new Intent(this, CardsActivity.class);
		intent.putExtra(CardsActivity.INTENT_TOPIC_ID, topicId);
		startActivity(intent);
	}
}
