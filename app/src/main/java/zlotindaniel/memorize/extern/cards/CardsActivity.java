package zlotindaniel.memorize.extern.cards;

import android.os.Bundle;

import zlotindaniel.memorize.BaseActivity;
import zlotindaniel.memorize.cards.CardsInteractor;

public class CardsActivity extends BaseActivity {

	private CardsView view;
	private CardsInteractor interactor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		view = new CardsView(this);
//		interactor = new CardsInteractor(view, config.dataLoader, config.shuffler);
//		view.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				interactor.onClick();
//			}
//		});
//
//		setContentView(view);
//
//		interactor.start();
	}
}
