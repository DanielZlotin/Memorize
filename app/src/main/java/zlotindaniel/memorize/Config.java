package zlotindaniel.memorize;

import zlotindaniel.memorize.data.DataLoader;
import zlotindaniel.memorize.shuffle.Shuffler;

public final class Config {
	public final DataLoader dataLoader;
	public final Shuffler shuffler;

	public Config(final DataLoader dataLoader, final Shuffler shuffler) {
		this.dataLoader = dataLoader;
		this.shuffler = shuffler;
	}
}
