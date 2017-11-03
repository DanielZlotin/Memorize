package zlotindaniel.memorize.data;

import zlotindaniel.memorize.data.request.*;

public interface Network {
	void create(CreateRequest request);

	<T> void read(ReadRequest<T> request);

	void update(UpdateRequest request);

	void delete(DeleteRequest request);
}
