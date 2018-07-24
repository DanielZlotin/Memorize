package zlotindaniel.memorize.data;

import org.json.*;
import org.junit.*;
import org.junit.experimental.theories.*;

import zlotindaniel.memorize.*;

import static org.assertj.core.api.Assertions.*;

public class UserDetailsTest extends BaseTest {
	@Test
	public void valueType() throws Exception {
		UserDetails uut = new UserDetails("theId", "theEmail", "theName", "thePhoto");
		assertThat(uut).isEqualTo(new UserDetails("theId", "theEmail", "theName", "thePhoto"));
		assertThat(uut.hasId()).isTrue();
		assertThat(uut.getEmail()).isEqualTo("theEmail");
		assertThat(new UserDetails("theId", "theEmail", "theName", "thePhoto").hasId()).isTrue();

		assertThat(uut).isNotEqualTo(new UserDetails("theId", "theEmail2", "theName", "thePhoto"));
	}

	@Test
	public void idHolder() throws Exception {
		UserDetails uut = new UserDetails("theId", "theEmail2", "theName", "thePhoto");
		assertThrows(() -> uut.withId("theNewId"));
	}


	@Test
	public void withIdSameIdIgnored() throws Exception {
		UserDetails uut = new UserDetails("theId", "theEmail2", "theName", "thePhoto");
		assertThat(uut.withId("theId")).isSameAs(uut);
	}

	@Test
	public void requiredFields() throws Exception {
		assertThrows(() -> new UserDetails("", "theEmail", "", ""));
		assertThrows(() -> new UserDetails(null, "theEmail", "", ""));
		assertThrows(() -> new UserDetails("theId", "", "", ""));
		assertThrows(() -> new UserDetails("theId", null, "", ""));
	}

	@Test
	public void normalizes() throws Exception {
		UserDetails uut = new UserDetails("the     Id", "    \n\t the\n EMAIL  ", "  the NAME\n\n", "\nthe\nphoto");
		assertThat(uut.getId()).isEqualTo("the Id");
		assertThat(uut.getEmail()).isEqualTo("the EMAIL");
		assertThat(uut.getDisplayName()).isEqualTo("the NAME");
		assertThat(uut.getPhotoUrl()).isEqualTo("the photo");
	}

	@Test
	public void json() throws Exception {
		JSONObject json = new UserDetails("theId", "theEmail", "theName", "thePhoto").toJson();
		assertThat(json).isNotNull();
		assertThat(UserDetails.parse(json)).isEqualTo(new UserDetails("theId", "theEmail", "theName", "thePhoto"));
	}

	public static class UserDetailsDTO extends EqualsHashCodeTheory {
		@DataPoints
		public static Object[] data = {
				new UserDetails("theId", "theEmail", "theName", "thePhoto"),
				new UserDetails("theId2", "theEmail", "theName", "thePhoto"),
				new UserDetails("theId", "theEmail2", "theName", "thePhoto"),
				new UserDetails("theId", "theEmail", "theName2", "thePhoto"),
				new UserDetails("theId", "theEmail", "theName", "thePhoto2")
		};
	}
}