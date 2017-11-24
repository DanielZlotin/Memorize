package zlotindaniel.memorize.data;

import com.google.common.base.*;

import org.json.*;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.*;
import static zlotindaniel.memorize.data.Utils.*;

public class Topic implements ValueType {
	private static final String NO_ID = "NO_ID";
	private static final String ID = "id";
	private static final String NAME = "name";

	public static Topic parse(JSONObject json) {
		String id = json.optString(ID);
		String name = json.optString(NAME);
		return new Topic(id, name);
	}

	private final String id;
	private final String name;

	public Topic(final String id, final String name) {
		this.id = MoreObjects.firstNonNull(emptyToNull(normalize(id)), NO_ID);
		this.name = checkNotNull(emptyToNull(capitalizeFully(normalize(name))));
	}

	@Override
	public boolean hasId() {
		return !Objects.equal(id, NO_ID);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Topic withId(final String id) {
		if (id.equals(this.id)) return this;
		Preconditions.checkArgument(!hasId(), "already has id");
		return new Topic(id, name);
	}

	public Topic withName(String newName) {
		return new Topic(id, newName);
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Topic topic = (Topic) o;

		return Objects.equal(id, topic.id)
				&& Objects.equal(name, topic.name);
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
		if (hasId()) Utils.jsonPut(json, ID, id);
		Utils.jsonPut(json, NAME, name);
		return json;
	}
}
