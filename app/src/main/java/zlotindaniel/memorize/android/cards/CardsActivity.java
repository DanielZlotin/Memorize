package zlotindaniel.memorize.android.cards;

import android.os.Bundle;

import zlotindaniel.memorize.BaseActivity;
import zlotindaniel.memorize.cards.CardsInteractor;

public class CardsActivity extends BaseActivity {

	public static final String INTENT_TOPIC_ID = "topicId";

	private CardsView view;
	private CardsInteractor interactor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String topicId = getIntent().getStringExtra(INTENT_TOPIC_ID);

		view = new CardsView(this);
		interactor = new CardsInteractor(topicId, view, config.network, config.shuffler);
		view.setOnClickListener(v -> interactor.onClick());

		setContentView(view);

		interactor.start();
	}
}
