package zlotindaniel.memorize.extern.topics;

import android.app.Activity;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import zlotindaniel.memorize.data.Topic;
import zlotindaniel.memorize.topics.TopicsDisplay;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class TopicsView extends RelativeLayout implements TopicsDisplay {
	public static final String TITLE = "Select A Topic";

	private ProgressBar progressBar;
	private ListView listview;
	private TopicsListAdapter listAdapter;

	public TopicsView(final Activity context) {
		super(context);
		context.setTitle(TITLE);
		initProgress();
		initList();
	}

	private void initProgress() {
		progressBar = new ProgressBar(getContext());
		progressBar.setIndeterminate(true);
		progressBar.setVisibility(VISIBLE);
		progressBar.setId(generateViewId());
		LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
		params.addRule(CENTER_IN_PARENT);
		addView(progressBar, params);
	}

	private void initList() {
		listview = new ListView(getContext());
		listview.setId(generateViewId());
		listview.setVisibility(GONE);
		LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
		addView(listview, params);

		listAdapter = new TopicsListAdapter();
		listview.setAdapter(listAdapter);
	}

	@Override
	public void bind(final List<Topic> topics) {
		if (topics.isEmpty()) {
			Toast.makeText(getContext(), "AGH! empty topics!", Toast.LENGTH_SHORT).show();
			return;
		}

		progressBar.setVisibility(GONE);
		listview.setVisibility(VISIBLE);
		listAdapter.bind(topics);
	}
}
