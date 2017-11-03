package zlotindaniel.memorize.topics;

import com.google.common.base.*;

import org.json.*;

import zlotindaniel.memorize.data.*;

public class Topic implements ValueType {
	private static final String NO_ID = "NO_ID";

	public static Topic parse(JSONObject json) {
		String id = json.optString("id");
		String name = json.optString("name");
		return new Topic(id, name);
	}

	private final String id;
	private final String name;

	public Topic(final String id, final String name) {
		this.id = MoreObjects.firstNonNull(Strings.emptyToNull(id), NO_ID);
		this.name = Preconditions.checkNotNull(Strings.emptyToNull(name));
	}

	public boolean hasId() {
		return !Objects.equal(id, NO_ID);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Topic topic = (Topic) o;

		return Objects.equal(id, topic.id) &&
				Objects.equal(name, topic.name);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, name);
	}

	@Override
	public String toString() {
		return toJson().toString();
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		if (hasId()) Utils.jsonPut(json, "id", id);
		Utils.jsonPut(json, "name", name);
		return json;
	}
}
