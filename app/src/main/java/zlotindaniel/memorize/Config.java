package zlotindaniel.memorize;

import zlotindaniel.memorize.shuffle.Shuffler;
import zlotindaniel.memorize.data.DataLoader;

public final class Config {
	public final DataLoader dataLoader;
	public final Shuffler shuffler;

	public Config(final DataLoader dataLoader, final Shuffler shuffler) {
		this.dataLoader = dataLoader;
		this.shuffler = shuffler;
	}
}
