package zlotindaniel.memorize.android.topics;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import zlotindaniel.memorize.android.cards.CardsActivity;
import zlotindaniel.memorize.android.edit.EditTopicActivity;
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
		pullToRefresh.addView(listview);
		addView(pullToRefresh, params);

		listAdapter = new TopicsListAdapter();
		listview.setAdapter(listAdapter);
	}

	public void onCreateMenu(final Menu menu) {
		menu.add("Create New Topic");
	}

	public void onMenuItemClicked(final MenuItem item) {

	}

	@Override
	public void setListener(final Listener listener) {
		pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				listener.onRefresh();
			}
		});
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
				listener.onTopicClicked(listAdapter.getItem(position));
			}
		});
		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
				listener.onTopicEditClicked(listAdapter.getItem(position));
				return true;
			}
		});
	}

	@Override
	public void bind(final List<Topic> topics) {
		pullToRefresh.setRefreshing(false);
		listview.setVisibility(VISIBLE);
		listAdapter.bind(topics);
	}

	@Override
	public void bind(final String error) {
		pullToRefresh.setRefreshing(false);
		Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
	}

	@Override
	public void navigateShowTopic(final String topicId) {
		Intent intent = new Intent(getContext(), CardsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra(CardsActivity.INTENT_TOPIC_ID, topicId);
		getContext().startActivity(intent);
	}

	@Override
	public void navigateEditTopic(final String topicId) {
		Intent intent = new Intent(getContext(), EditTopicActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra(EditTopicActivity.INTENT_TOPIC_ID, topicId);
		getContext().startActivity(intent);
	}
}
