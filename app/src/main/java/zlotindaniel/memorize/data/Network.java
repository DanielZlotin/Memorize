package zlotindaniel.memorize.data;

public interface Network {
	<T> void load(Request<T> request);

	void save(Payload request);

	void delete(DeleteRequest request);
}
