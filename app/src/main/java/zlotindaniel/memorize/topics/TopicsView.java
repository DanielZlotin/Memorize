package zlotindaniel.memorize.topics;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TopicsView extends LinearLayout {

	private ListView listView;
	private TopicsAdapter adapter;
	private List<Topic> topics;

	public TopicsView(final Activity activity) {
		super(activity);

		topics = new ArrayList<>();
		topics.add(new Topic("Topic1"));
		topics.add(new Topic("Topic2"));

		setOrientation(VERTICAL);
		int pad = (int) (getResources().getDisplayMetrics().density * 8);
		setPadding(pad, pad, pad, pad);
		initList();
	}

	private void initList() {
		listView = new ListView(getContext());
		adapter = new TopicsAdapter();
		listView.setAdapter(adapter);
		addView(listView);
	}

	private class TopicsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return topics.size();
		}

		@Override
		public Topic getItem(final int i) {
			return topics.get(i);
		}

		@Override
		public long getItemId(final int i) {
			return topics.get(i).title.hashCode();
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getView(final int i, final View view, final ViewGroup viewGroup) {
			TopicCellView cell;
			if (view == null)
				cell = new TopicCellView(getContext());
			else
				cell = (TopicCellView) view;
			cell.bind(getItem(i));
			return cell;
		}
	}
}
