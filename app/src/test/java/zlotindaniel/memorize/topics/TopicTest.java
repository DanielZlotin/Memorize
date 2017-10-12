package zlotindaniel.memorize.topics;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;

import zlotindaniel.memorize.BaseTest;
import zlotindaniel.memorize.EqualsHashCodeTheory;
import zlotindaniel.memorize.topics.Topic;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TopicTest extends BaseTest {

	@Test
	public void valueType() throws Exception {
		Topic topic = Topic.create("theId", "the name");
		assertThat(topic.getId()).isEqualTo("theId");
		assertThat(topic.getName()).isEqualTo("the name");

		assertThat(topic).isEqualTo(Topic.create("theId", "the name"));
		assertThat(topic).isNotEqualTo(Topic.create("x", "the name"));
		assertThat(topic).isNotEqualTo(Topic.create("theId", "x"));
	}

	@Test
	public void requiredFields() throws Exception {
		assertThrows(new Runnable() {
			@Override
			public void run() {
				Topic.create(null, null);
			}
		});
		assertThrows(new Runnable() {
			@Override
			public void run() {
				Topic.create(null, "");
			}
		});
	}

	@Test
	public void idIsOptional() throws Exception {
		assertThat(Topic.create(null, "asd").hasId()).isFalse();
		assertThat(Topic.create("", "asd").hasId()).isFalse();
		assertThat(Topic.create("a", "asd").hasId()).isTrue();

		assertThat(Topic.create(null, "asd").getId()).isNotEmpty();
		assertThat(Topic.create("", "asd").getId()).isNotEmpty();
		assertThat(Topic.create("a", "asd").getId()).isNotEmpty();
	}

	public static class TopicValueType extends EqualsHashCodeTheory {
		@DataPoints
		public static Object[] data = {
				Topic.create("theId", "theName"),
				Topic.create("a", "a"),
				Topic.create("", "a")
		};
	}
}
