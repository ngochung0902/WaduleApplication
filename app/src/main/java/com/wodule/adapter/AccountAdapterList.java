package com.wodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wodule.R;
import com.wodule.commonhelps.LakConst;
import com.wodule.commonhelps.LakRun;

public class AccountAdapterList extends BaseAdapter{
	Context context;
	LayoutInflater inflater;

	public AccountAdapterList(Context mContext) {
		this.context = mContext;
		inflater = LayoutInflater.from(context);
	}
	public class ViewHolder {
		TextView date;
		TextView examId;
		TextView score, status;
	}
	@Override
	public int getCount() {
		return 12;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.row_item_account, null);
			holder.date = (TextView)convertView.findViewById(R.id.row_date);
			holder.examId = (TextView)convertView.findViewById(R.id.row_examId);
			holder.score = (TextView)convertView.findViewById(R.id.row_score);
			holder.status = (TextView)convertView.findViewById(R.id.row_status);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		LakRun.setFontTV(context, holder.date, LakConst.FONT_HEV_MEDIUM);
		LakRun.setFontTV(context, holder.examId, LakConst.FONT_HEV_MEDIUM);
		LakRun.setFontTV(context, holder.score, LakConst.FONT_HEV_MEDIUM);
		LakRun.setFontTV(context, holder.status, LakConst.FONT_HEV_MEDIUM);
		return convertView;
	}
//	public void setData(List<ItemObject> itemList) {
//		this.itemList = itemList;
//		this.notifyDataSetChanged();
//	}
}
