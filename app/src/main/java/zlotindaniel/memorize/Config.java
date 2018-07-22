package zlotindaniel.memorize;

import zlotindaniel.memorize.data.*;
import zlotindaniel.memorize.data.shuffle.*;

public final class Config {
	public final boolean debug;
	public final Database database;
	public final Shuffler shuffler;

	public Config(boolean debug, Database database, final Shuffler shuffler) {
		this.debug = debug;
		this.database = database;
		this.shuffler = shuffler;
	}
}
