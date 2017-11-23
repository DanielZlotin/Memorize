package zlotindaniel.memorize.data;

import zlotindaniel.memorize.data.parser.*;

public interface Database {
	void create(final String path, final ValueType payload, final OnSuccess<String> onSuccess, final OnFailure onFailure);

	<T> void read(String path, JsonParser<T> jsonParser, OnSuccess<T> onSuccess, OnFailure onFailure);

	void update(final String path, final ValueType payload, final Runnable onSuccess, final OnFailure onFailure);

	void delete(final String path, final Runnable onSuccess, final OnFailure onFailure);
}
