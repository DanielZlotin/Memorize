package zlotindaniel.memorize.data;

public interface Loader {
	<T> void load(Request<T> request);
}
