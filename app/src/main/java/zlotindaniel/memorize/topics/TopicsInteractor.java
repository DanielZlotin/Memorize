package zlotindaniel.memorize.topics;

import java.util.Collections;
import java.util.List;

import zlotindaniel.memorize.data.Loader;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;
import zlotindaniel.memorize.data.Topic;

public class TopicsInteractor {

	private final TopicsDisplay display;
	private final Loader loader;

	public TopicsInteractor(TopicsDisplay display, Loader loader) {
		this.display = display;
		this.loader = loader;
	}

	public void start() {
		loader.load(new TopicsRequest(new OnSuccess<List<Topic>>() {
			@Override
			public void success(final List<Topic> topics) {
				handleSucess(topics);
			}
		}, new OnFailure() {
			@Override
			public void failure(final Exception e) {
				handleFailure(e);
			}
		}));
	}

	private void handleSucess(final List<Topic> topics) {
		display.bind(topics);
	}

	private void handleFailure(final Exception e) {
		display.bind(Collections.<Topic>emptyList());
	}
}
