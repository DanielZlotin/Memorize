package zlotindaniel.memorize.topics;

import java.util.List;
import java.util.Map;

import zlotindaniel.memorize.data.DataLoader;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;

public class TopicsInteractor {

	public interface Display {
		void bind(List<Topic> topics);
	}

	private final Display display;
	private final DataLoader dataLoader;

	public TopicsInteractor(Display display, DataLoader dataLoader) {
		this.display = display;
		this.dataLoader = dataLoader;
	}

	public void start() {
		dataLoader.load(new OnSuccess<Map<String, Object>>() {
			@Override
			public void success(final Map<String, Object> stringObjectMap) {

			}
		}, new OnFailure() {
			@Override
			public void failure(final Exception e) {

			}
		});
	}
}
