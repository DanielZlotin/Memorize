package zlotindaniel.memorize.data;

import org.json.*;
import org.junit.*;

import zlotindaniel.memorize.*;

import static org.assertj.core.api.Assertions.*;

public class UtilsTest extends BaseTest {
	@Test
	public void utils() throws Exception {
		assertThat(new Utils()).isNotNull();
	}

	@Test
	public void jsonPut() throws Exception {
		JSONObject o = new JSONObject();
		Utils.jsonPut(o, "key", "value");
		assertThat(o.keys()).containsExactly("key");
		assertThat(o.getString("key")).isEqualTo("value");
	}

	@Test(expected = RuntimeException.class)
	public void jsonPutUncheckedExceptions() throws Exception {
		Utils.jsonPut(null, null, null);
	}

	@Test
	public void normalize() throws Exception {
		assertThat(Utils.normalize(null)).isEqualTo("");
		assertThat(Utils.normalize("")).isEqualTo("");
		assertThat(Utils.normalize(" ")).isEqualTo("");
		assertThat(Utils.normalize("x")).isEqualTo("x");
		assertThat(Utils.normalize("x x")).isEqualTo("x x");
		assertThat(Utils.normalize("x  x")).isEqualTo("x x");
		assertThat(Utils.normalize("x \t x")).isEqualTo("x x");
		assertThat(Utils.normalize("x \b\nx")).isEqualTo("x x");
		assertThat(Utils.normalize("       \n\n\n      \n\nx \t\n\r\b x  \n\r\b\t")).isEqualTo("x x");
	}
}
