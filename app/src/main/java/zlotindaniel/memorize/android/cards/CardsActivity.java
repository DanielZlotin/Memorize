package zlotindaniel.memorize.android.cards;

import android.os.Bundle;
import android.view.View;

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
		interactor = new CardsInteractor(topicId, view, config.loader, config.shuffler);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				interactor.onClick();
			}
		});

		setContentView(view);

		interactor.start();
	}
}
