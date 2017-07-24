
package com.wodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.wodule.R;
import com.wodule.commonhelps.LakConst;
import com.wodule.commonhelps.LakRun;

import java.util.Calendar;

public class CalendarAdapter extends BaseAdapter {
	static final int FIRST_DAY_OF_WEEK =0; // Sunday = 0, Monday = 1
	
	
	private Context mContext;
	private View v;
    private Calendar month;
    private Calendar selectedDate;
    private int pos = 0;
    public CalendarAdapter(Context c, Calendar monthCalendar, int pos_choose) {
    	month = monthCalendar;
    	selectedDate = (Calendar)monthCalendar.clone();
    	mContext = c;
        month.set(Calendar.DAY_OF_MONTH, 1);
        pos= pos_choose;
        refreshDays();
    }
    
    public int getCount() {
        return days.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(final int position, View convertView, final ViewGroup parent) {
       v = convertView;
    	final TextView dayView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_item, null);
            v.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, LakRun.GetWidthDevice(mContext)/7));

        }
        dayView = (TextView)v.findViewById(R.id.date);

        LakRun.setLayoutView(dayView,LakRun.GetWidthDevice(mContext)/7 - (int)mContext.getResources().getDimension(R.dimen.margin5),(int)mContext.getResources().getDimension(R.dimen.margin40));
        LakRun.setFontTV(mContext, dayView, LakConst.FONT_HEV_REGULAR);
        if(days[position].equals("")) {
        	dayView.setClickable(false);
        	dayView.setFocusable(false);
        }
        else {
        	// mark current day as focused
        	if (pos != -1) {
            		if(pos == position){
        				v.setBackgroundResource(R.drawable.calendar_bg_orange);
        			}
            	else {
            		v.setBackgroundResource(R.drawable.list_item_background);
            	}
        		
			}else{
				if(month.get(Calendar.YEAR)== selectedDate.get(Calendar.YEAR) && month.get(Calendar.MONTH)== selectedDate.get(Calendar.MONTH) && days[position].equals(""+selectedDate.get(Calendar.DAY_OF_MONTH))) {
            		v.setBackgroundResource(R.drawable.calendar_bg_orange);
				}else {
            		v.setBackgroundResource(R.drawable.list_item_background);
            	}
			}
        	
        }
        
        dayView.setText(days[position]);
        
        // create date string for comparison
        String date = days[position];
    	
        if(date.length()==1) {
    		date = "0"+date;
    	}
    	String monthStr = ""+(month.get(Calendar.MONTH)+1);
    	if(monthStr.length()==1) {
    		monthStr = "0"+monthStr;
    	}

        // show icon if date is not empty and it exists in the items array
//        ImageView iw = (ImageView)v.findViewById(R.id.date_icon);
//        if(date.length()>0 && items!=null && items.contains(date)) {
//        	iw.setVisibility(View.VISIBLE);
//        }
//        else {
//        	iw.setVisibility(View.INVISIBLE);
//        }
//        ImageView iw1 = (ImageView)v.findViewById(R.id.date_icon1);
//        if(date.length()>0 && items1!=null && items1.contains(date)) {
//        	iw1.setVisibility(View.VISIBLE);
//        }
//        else {
//        	iw1.setVisibility(View.INVISIBLE);
//        }
        return v;
    }
    
    public void refreshDays()
    {
//    	// clear items
//    	items.clear();
//    	items1.clear();
    	int lastDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDay = (int)month.get(Calendar.DAY_OF_WEEK);
        
        // figure size of the array
        if(firstDay==1){
        	days = new String[lastDay+(FIRST_DAY_OF_WEEK*6)];
        }
        else {
        	days = new String[lastDay+firstDay-(FIRST_DAY_OF_WEEK+1)];
        }
        
        int j=FIRST_DAY_OF_WEEK;
        
        // populate empty days before first real day
        if(firstDay>1) {
	        for(j=0;j<firstDay-FIRST_DAY_OF_WEEK;j++) {
	        	days[j] = "";
	        }
        }
	    else {
	    	for(j=0;j<FIRST_DAY_OF_WEEK*6;j++) {
	        	days[j] = "";
	        }
	    	j=FIRST_DAY_OF_WEEK*6+1; // sunday => 1, monday => 7
	    }
        
        // populate days
        int dayNumber = 1;
        for(int i=j-1;i<days.length;i++) {
        	days[i] = ""+dayNumber;
        	dayNumber++;
        }
    }

    // references to our items
    public String[] days;
}