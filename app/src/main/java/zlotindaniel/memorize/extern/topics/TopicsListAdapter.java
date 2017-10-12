package zlotindaniel.memorize.extern.topics;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.common.collect.Lists;

import java.util.List;

import zlotindaniel.memorize.data.Topic;

class TopicsListAdapter extends BaseAdapter {
	private List<Topic> data = Lists.newArrayList();

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Topic getItem(final int i) {
		return data.get(i);
	}

	@Override
	public long getItemId(final int i) {
		return i;
	}

	@Override
	public View getView(final int i, final View view, final ViewGroup viewGroup) {
		TopicCellView cell = view != null ? (TopicCellView) view : new TopicCellView(viewGroup.getContext());
		cell.bind(getItem(i));
		return cell;
	}

	public void bind(final List<Topic> topics) {
		data.clear();
		data.addAll(topics);
		notifyDataSetChanged();
	}
}
