package zlotindaniel.memorize.android.usecase.cards;

import android.os.*;
import android.view.Menu;
import android.view.MenuItem;

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
