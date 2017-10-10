package zlotindaniel.memorize.data;

import org.json.JSONObject;
import org.junit.Test;

import zlotindaniel.memorize.BaseTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

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
}
