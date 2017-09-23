package zlotindaniel.memorize;

import zlotindaniel.memorize.data.CardsStackShuffler;
import zlotindaniel.memorize.data.DataLoader;

public final class Config {
	public final DataLoader dataLoader;
	public final CardsStackShuffler cardsStackShuffler;

	public Config(final DataLoader dataLoader, final CardsStackShuffler cardsStackShuffler) {
		this.dataLoader = dataLoader;
		this.cardsStackShuffler = cardsStackShuffler;
	}
}
