package zlotindaniel.memorize.data;

import org.assertj.core.util.*;
import org.json.*;
import org.junit.*;

import java.util.*;

import zlotindaniel.memorize.*;

import static com.google.common.collect.Lists.*;
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

	@Test
	public void toMap() throws Exception {
		JSONObject json = new JSONObject();
		JSONObject inner = new JSONObject();
		JSONArray list = new JSONArray();
		list.put(123);
		list.put(inner);
		list.put(Lists.newArrayList(1, 2, 3));
		inner.put("foo", true);
		json.put("a", 1);
		json.put("b", 2.3);
		json.put("c", "3");
		json.put("d", inner);
		json.put("e", list);
		Map<String, Object> actual = Utils.toMap(json);
		assertThat(actual.get("a")).isEqualTo(1);
		assertThat(actual.get("b")).isEqualTo(2.3);
		assertThat(actual.get("c")).isEqualTo("3");
		assertThat(actual.get("d")).isEqualTo(Maps.newHashMap("foo", true));
		assertThat(actual.get("e")).isEqualTo(newArrayList(123, Maps.newHashMap("foo", true), newArrayList(1, 2, 3)));
	}

	@Test
	public void capitalize() throws Exception {
		assertThat(Utils.capitalizeFully("")).isEqualTo("");
		assertThat(Utils.capitalizeFully("    ")).isEqualTo("");
		assertThat(Utils.capitalizeFully(" \t\n\r  \n   ")).isEqualTo("");
		assertThat(Utils.capitalizeFully(". 0 .")).isEqualTo(". 0 .");
		assertThat(Utils.capitalizeFully("abc")).isEqualTo("Abc");
		assertThat(Utils.capitalizeFully("abc cba")).isEqualTo("Abc Cba");
		assertThat(Utils.capitalizeFully("aXc c1a bBB")).isEqualTo("Axc C1a Bbb");
	}
}
