package zlotindaniel.memorize.shuffle;

import com.google.common.collect.Lists;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import zlotindaniel.memorize.BaseTest;

import static org.assertj.core.api.Assertions.fail;

public class DefaultShufflerTest extends BaseTest {
	@Test
	public void shufflesRandomly() throws Exception {
		DefaultShuffler uut = new DefaultShuffler();
		List<String> seen = Lists.newArrayList();
		List<String> all = Arrays.asList("a", "b", "c");
		for (int i = 0; i < 1e4; i++) {
			uut.shuffle(all);
			seen.add(all.get(0));
			if (seen.containsAll(all)) {
				return;
			}
		}
		fail("cant find all elements in 10,000 retries");
	}

}
