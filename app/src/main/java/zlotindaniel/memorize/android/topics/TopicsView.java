package zlotindaniel.memorize.android.topics;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import zlotindaniel.memorize.data.OnSuccess;
import zlotindaniel.memorize.topics.Topic;
import zlotindaniel.memorize.topics.TopicsDisplay;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class TopicsView extends FrameLayout implements TopicsDisplay {
	public static final String TITLE = "Select A Topic";

	private ListView listview;
	private TopicsListAdapter listAdapter;
	private SwipeRefreshLayout pullToRefresh;

	public TopicsView(final Activity context) {
		super(context);
		context.setTitle(TITLE);
		initList();
	}

	private void initList() {
		pullToRefresh = new SwipeRefreshLayout(getContext());
		pullToRefresh.setRefreshing(true);

		listview = new ListView(getContext());
		listview.setId(generateViewId());
		listview.setVisibility(GONE);
		LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);

		listAdapter = new TopicsListAdapter();
		listview.setAdapter(listAdapter);

		pullToRefresh.addView(listview);
		addView(pullToRefresh, params);
	}

	@Override
	public void bind(final List<Topic> topics) {
		pullToRefresh.setRefreshing(false);
		listview.setVisibility(VISIBLE);
		listAdapter.bind(topics);
	}

	@Override
	public void bind(final String error) {
		Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
		pullToRefresh.setRefreshing(false);
	}

	public void setOnTopicClick(final OnSuccess<Topic> onSuccess) {
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
				onSuccess.success(listAdapter.getItem(i));
			}
		});
	}

	public void setOnRefresh(final Runnable onRefresh) {
		pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				onRefresh.run();
			}
		});
	}
}
