package zlotindaniel.memorize;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.shuffle.*;

public final class Config {
	public final Database database;
	public final Shuffler shuffler;

	public Config(Database database, final Shuffler shuffler) {
		this.database = database;
		this.shuffler = shuffler;
	}
}
