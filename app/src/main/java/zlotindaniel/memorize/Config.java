package zlotindaniel.memorize;

import zlotindaniel.memorize.data.Network;
import zlotindaniel.memorize.shuffle.Shuffler;

public final class Config {
	public final Network network;
	public final Shuffler shuffler;

	public Config(Network network, final Shuffler shuffler) {
		this.network = network;
		this.shuffler = shuffler;
	}
}
