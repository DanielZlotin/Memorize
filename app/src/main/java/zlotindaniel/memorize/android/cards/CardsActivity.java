package zlotindaniel.memorize.android.cards;

import android.os.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.cards.*;

public class CardsActivity extends BaseActivity {

	public static final String INTENT_TOPIC_ID = "topicId";

	private CardsView view;
	private CardsInteractor interactor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String topicId = getIntent().getStringExtra(INTENT_TOPIC_ID);

		view = new CardsView(this);
		setContentView(view);
		interactor = new CardsInteractor(topicId, view, config.network, config.shuffler);
		interactor.start();
	}
}
