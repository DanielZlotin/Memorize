package zlotindaniel.memorize.android.usecase.cards;

import android.os.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.usecase.cards.*;
import zlotindaniel.memorize.data.*;

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
		interactor = new CardsInteractor(topicId, view, new DatabaseService(config.debug, getUserDetails().getId(), config.database), config.shuffler);
		interactor.start();
	}
}