package zlotindaniel.memorize.topics;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public class Topic {
	private static final String NO_ID = "NO_ID";

	public static Topic create(final String id, final String name) {
		return new Topic(
				MoreObjects.firstNonNull(Strings.emptyToNull(id), NO_ID),
				Preconditions.checkNotNull(Strings.emptyToNull(name)));
	}

	private final String id;
	private final String name;

	private Topic(final String id, final String name) {
		this.id = id;
		this.name = name;
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
		return MoreObjects.toStringHelper(this).addValue(name).toString();
	}
}
