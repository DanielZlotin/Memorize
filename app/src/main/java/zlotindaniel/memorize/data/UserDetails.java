package zlotindaniel.memorize.data;

import com.google.common.base.*;

import org.json.*;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.*;
import static zlotindaniel.memorize.data.Utils.*;

public class UserDetails implements ValueType {

	private static final String ID = "id";
	private static final String EMAIL = "email";
	private static final String NAME = "name";
	private static final String PHOTO = "photo";

	public static UserDetails parse(JSONObject object) {
		String id = object.optString(ID);
		String email = object.optString(EMAIL);
		String displayName = object.optString(NAME);
		String photo = object.optString(PHOTO);
		return new UserDetails(id, email, displayName, photo);
	}

	private final String id;
	private final String email;
	private final String displayName;
	private final String photoUrl;

	public UserDetails(String id, final String email, String displayName, String photoUrl) {
		this.id = checkNotNull(emptyToNull(normalize(id)));
		this.email = checkNotNull(emptyToNull(normalize(email)));
		this.displayName = normalize(Strings.nullToEmpty(displayName));
		this.photoUrl = normalize(Strings.nullToEmpty(photoUrl));
	}

	public String getEmail() {
		return email;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDetails that = (UserDetails) o;

		return Objects.equal(id, that.id)
				&& Objects.equal(email, that.email)
				&& Objects.equal(displayName, that.displayName)
				&& Objects.equal(photoUrl, that.photoUrl);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, email, displayName, photoUrl);
	}

	@Override
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		Utils.jsonPut(json, ID, id);
		Utils.jsonPut(json, EMAIL, email);
		Utils.jsonPut(json, NAME, displayName);
		Utils.jsonPut(json, PHOTO, photoUrl);
		return json;
	}

	@Override
	public ValueType withId(final String id) {
		if (id.equals(this.id)) return this;
		throw new IllegalArgumentException("already has id");
	}

	@Override
	public boolean hasId() {
		return true;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return toJson().toString();
	}
}
