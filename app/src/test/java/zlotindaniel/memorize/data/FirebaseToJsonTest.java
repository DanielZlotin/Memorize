package zlotindaniel.memorize.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;

import org.assertj.core.util.Maps;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Map;

import zlotindaniel.memorize.BaseTest;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class FirebaseToJsonTest extends BaseTest {

	private FirebaseToJson uut;
	private OnSuccess<JSONObject> onSuccess;
	private OnFailure onFailure;
	private DataSnapshot dataSnapshot;

	@SuppressWarnings("unchecked")
	@Override
	public void beforeEach() {
		super.beforeEach();
		onSuccess = mock(OnSuccess.class);
		onFailure = mock(OnFailure.class);
		dataSnapshot = mock(DataSnapshot.class);
		uut = new FirebaseToJson(onSuccess, onFailure);
	}

	@Test
	public void reportCancelledAsFailure() throws Exception {
		DatabaseError databaseError = mock(DatabaseError.class);
		DatabaseException databaseException = new DatabaseException("cancelled");
		when(databaseError.toException()).thenReturn(databaseException);

		uut.onCancelled(databaseError);

		verifyZeroInteractions(onSuccess);
		verify(onFailure, times(1)).failure(eq(databaseException));
	}

	@Test
	public void onDataChangeReportErrorOnException() throws Exception {
		RuntimeException exception = new RuntimeException("some error");
		when(dataSnapshot.getValue()).thenThrow(exception);

		uut.onDataChange(dataSnapshot);

		verifyZeroInteractions(onSuccess);
		verify(onFailure, times(1)).failure(eq(exception));
	}

	@Test
	public void nullReportsError() throws Exception {
		when(dataSnapshot.getValue()).thenReturn(null);
		uut.onDataChange(dataSnapshot);

		verifyZeroInteractions(onSuccess);
		verify(onFailure, times(1)).failure(any(RuntimeException.class));
	}

	@Test
	public void returnsSuccessfulResult() throws Exception {
		Map<String, String> raw = Maps.newHashMap("key", "value");

		when(dataSnapshot.getValue()).thenReturn(raw);
		uut.onDataChange(dataSnapshot);

		verifyZeroInteractions(onFailure);

		ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(JSONObject.class);

		verify(onSuccess, times(1)).success((JSONObject) captor.capture());
		JSONObject value = (JSONObject) captor.getValue();
		assertThat(value.keys()).containsExactly("key");
		assertThat(value.getString("key")).isEqualTo("value");
	}
}
