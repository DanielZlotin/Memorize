package zlotindaniel.memorize.usecase.topics;

import org.json.*;
import org.junit.*;
import org.junit.experimental.theories.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.data.*;

import static org.assertj.core.api.Assertions.*;

public class TopicTest extends BaseTest {

	@Test
	public void valueType() throws Exception {
		Topic topic = new Topic("theId", "the name");
		assertThat(topic.getId()).isEqualTo("theId");
		assertThat(topic.getName()).isEqualTo("The Name");

		assertThat(topic).isEqualTo(new Topic("theId", "the name"));
		assertThat(topic).isNotEqualTo(new Topic("x", "the name"));
		assertThat(topic).isNotEqualTo(new Topic("theId", "x"));

		assertThat(new Topic("", "the name").withId("newId")).isEqualTo(new Topic("newId", "The Name"));
		assertThrows(() -> new Topic("xxx", "yyy").withId("another"));
	}

	@Test
	public void withIdSameIdIgnored() throws Exception {
		Topic topic = new Topic("theId", "theName");
		assertThat(topic.withId("theId")).isSameAs(topic);
	}

	@Test
	public void requiredFields() throws Exception {
		assertThrows(() -> new Topic(null, null));
		assertThrows(() -> new Topic("some id", ""));
	}

	@Test
	public void nameAutoCapitalizesAndNormalizes() throws Exception {
		Topic topic = new Topic("theId", "    \n\t the\n NAME  ");
		assertThat(topic.getName()).isEqualTo("The Name");
	}

	@Test
	public void idIsOptional() throws Exception {
		assertThat(new Topic(null, "asd").hasId()).isFalse();
		assertThat(new Topic("", "asd").hasId()).isFalse();
		assertThat(new Topic("a", "asd").hasId()).isTrue();

		assertThat(new Topic(null, "asd").getId()).isNotEmpty();
		assertThat(new Topic("", "asd").getId()).isNotEmpty();
		assertThat(new Topic("a", "asd").getId()).isNotEmpty();

		assertThat(new Topic("", "asd").toString()).isEqualTo("{\"name\":\"Asd\"}");
	}

	@Test
	public void withName() throws Exception {
		assertThat(new Topic("asd", "zxc").withName("new name"))
				.isEqualTo(new Topic("asd", "New Name"));
	}

	@Test
	public void json() throws Exception {
		JSONObject json = new Topic("theId", "theName").toJson();
		assertThat(json).isNotNull();
		assertThat(Topic.parse(json)).isEqualTo(new Topic("theId", "theName"));
	}

	public static class TopicValueType extends EqualsHashCodeTheory {
		@DataPoints
		public static Object[] data = {
				new Topic("theId", "theName"),
				new Topic("a", "a"),
				new Topic("", "a")
		};
	}
}
