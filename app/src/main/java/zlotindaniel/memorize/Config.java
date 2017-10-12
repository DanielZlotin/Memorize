package zlotindaniel.memorize;

import zlotindaniel.memorize.data.Loader;
import zlotindaniel.memorize.shuffle.Shuffler;

public final class Config {
	public final Loader loader;
	public final Shuffler shuffler;

	public Config(Loader loader, final Shuffler shuffler) {
		this.loader = loader;
		this.shuffler = shuffler;
	}
}
