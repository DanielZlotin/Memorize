package zlotindaniel.memorize;

import zlotindaniel.memorize.data.Card;
import zlotindaniel.memorize.data.DataLoader;
import zlotindaniel.memorize.shuffle.Shuffler;

public final class Config {
	public final DataLoader dataLoader;
	public final Shuffler<Card> shuffler;

	public Config(final DataLoader dataLoader, final Shuffler<Card> shuffler) {
		this.dataLoader = dataLoader;
		this.shuffler = shuffler;
	}
}
