package zlotindaniel.memorize.cards;

import android.os.Bundle;
import android.view.View;

import zlotindaniel.memorize.BaseActivity;

public class CardsActivity extends BaseActivity {

	private CardsView view;
	private CardsInteractor interactor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new CardsView(this);
		interactor = new CardsInteractor(view, config.dataLoader, config.shuffler);
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
