package zlotindaniel.memorize.data;

import android.support.annotation.*;

import org.json.*;
import org.junit.*;

import zlotindaniel.memorize.*;

public class RequestTest extends BaseTest {

	private class TestRequest extends Request<Boolean> {
		public TestRequest(@NonNull final String path, @NonNull final OnSuccess<Boolean> onSuccess, @NonNull final OnFailure onFailure) {
			super(path, onSuccess, onFailure);
		}

		@Override
		public Boolean parseResponse(final JSONObject response) {
			return true;
		}
	}

	@Test
	public void neverNull() throws Exception {
		assertThrows(() -> new TestRequest(null, null, null));
		assertThrows(() -> new TestRequest("", null, null));
		assertThrows(() -> new TestRequest("path", null, null));
		assertThrows(() -> new TestRequest("path", (o) -> {}, null));
	}
}
