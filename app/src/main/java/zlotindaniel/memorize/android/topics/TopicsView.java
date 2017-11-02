package zlotindaniel.memorize.android.topics;

import android.app.*;
import android.content.*;
import android.support.design.widget.*;
import android.support.v4.widget.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;

import java.util.*;

import zlotindaniel.memorize.*;
import zlotindaniel.memorize.android.ViewUtils;
import zlotindaniel.memorize.android.cards.*;
import zlotindaniel.memorize.android.edit.*;
import zlotindaniel.memorize.topics.*;

import static android.view.ViewGroup.LayoutParams.*;

public class TopicsView extends FrameLayout implements TopicsDisplay {
	public static final String title = "Select A Topic";
	public static final String menuBtnNewTopicName = "Create New Topic";
	public static final int idInputCreateNewTopic = View.generateViewId();
	public static final int idBtnMenuCreateNewTopic = View.generateViewId();

	private ListView listview;
	private TopicsListAdapter listAdapter;
	private SwipeRefreshLayout pullToRefresh;
	private Listener listener;

	public TopicsView(final Activity context) {
		super(context);
		context.setTitle(title);
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
		MenuItem menuItem = menu.add(Menu.NONE, idBtnMenuCreateNewTopic, Menu.NONE, menuBtnNewTopicName);
		menuItem.setIcon(R.drawable.ic_add_white_24dp);
		menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}

	public void onMenuItemClicked(final MenuItem item) {
		if (item.getItemId() == idBtnMenuCreateNewTopic) {
			askNewTopic();
		}
	}

	private void askNewTopic() {
		TextInputLayout layout = new TextInputLayout(getContext());
		int p = ViewUtils.dp(16);
		layout.setPadding(p, p, p, p);

		final TextInputEditText input = new TextInputEditText(getContext());
		input.setInputType(EditorInfo.TYPE_CLASS_TEXT
				| EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE
				| EditorInfo.TYPE_TEXT_FLAG_AUTO_CORRECT
				| EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS);
		input.setSingleLine();
		input.setHint("Name");
		input.setId(idInputCreateNewTopic);

		layout.addView(input);

		new AlertDialog.Builder(getContext())
				.setTitle("New Topic")
				.setView(layout)
				.setPositiveButton("Create", (dialog, which) -> {
			String name = input.getText().toString();
			listener.createTopic(name);
		}).show();
	}

	@Override
	public void setListener(final Listener listener) {
		this.listener = listener;
		pullToRefresh.setOnRefreshListener(listener::refresh);
		listview.setOnItemClickListener((parent, view, position, id) -> navigateShowTopic(listAdapter.getItem(position).getId()));
		listview.setOnItemLongClickListener((parent, view, position, id) -> {
			navigateEditTopic(listAdapter.getItem(position));
			return true;
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

	private void navigateShowTopic(final String topicId) {
		Intent intent = new Intent(getContext(), CardsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra(CardsActivity.INTENT_TOPIC_ID, topicId);
		getContext().startActivity(intent);
	}

	private void navigateEditTopic(final Topic topic) {
		Intent intent = new Intent(getContext(), EditTopicActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		MemorizeApplication.context.acitivityStore(topic);
		getContext().startActivity(intent);
	}
}
