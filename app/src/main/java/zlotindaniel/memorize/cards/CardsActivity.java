package zlotindaniel.memorize.cards;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import zlotindaniel.memorize.Config;
import zlotindaniel.memorize.MemorizeApplication;

public class CardsActivity extends Activity {

	private CardsView view;
	private CardsInteractor interactor;
	private Config config;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		config = ((MemorizeApplication) getApplication()).getConfig();

		view = new CardsView(this);
		interactor = new CardsInteractor(view, config.dataLoader, config.cardsStackShuffler);
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
