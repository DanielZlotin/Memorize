package zlotindaniel.memorize;

import android.app.Application;

import zlotindaniel.memorize.data.CardsStackShuffler;
import zlotindaniel.memorize.data.CardsStackShufflerImpl;
import zlotindaniel.memorize.data.DataLoader;
import zlotindaniel.memorize.data.FirebaseDataLoader;

public class MemorizeApplication extends Application {
	public static MemorizeApplication instance;
	private DataLoader dataLoader;
	private CardsStackShuffler cardsStackShuffler;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		dataLoader = new FirebaseDataLoader();
		cardsStackShuffler = new CardsStackShufflerImpl();
	}

	public DataLoader getDataLoader() {
		return dataLoader;
	}

	public CardsStackShuffler getCardsStackShuffler() {
		return cardsStackShuffler;
	}
}
