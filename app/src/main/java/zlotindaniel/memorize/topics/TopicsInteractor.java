package zlotindaniel.memorize.topics;

import com.google.common.util.concurrent.RateLimiter;

import java.util.List;

import zlotindaniel.memorize.data.Loader;
import zlotindaniel.memorize.data.OnFailure;
import zlotindaniel.memorize.data.OnSuccess;

public class TopicsInteractor implements TopicsDisplay.Listener {

	private final TopicsDisplay display;
	private final Loader loader;
	private final RateLimiter limiter;


	public TopicsInteractor(TopicsDisplay display, Loader loader) {
		this.display = display;
		this.loader = loader;
		this.limiter = RateLimiter.create(0.5);
	}

	public void start() {
		display.setListener(this);
		load();
	}

	private void load() {
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

	@Override
	public void onTopicClicked(final Topic topic) {
		if (limiter.tryAcquire()) {
			display.navigateShowTopic(topic.getId());
		}
	}

	@Override
	public void onRefresh() {
		load();
	}

	private void handleSucess(final List<Topic> topics) {
		display.bind(topics);
	}

	private void handleFailure(final Exception e) {
		display.bind(e.getMessage());
	}
}
