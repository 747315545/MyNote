package com.example.mynote;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 自定义listview来实现滑动过程中的事件分发
 * 
 * @author Administrator
 * 
 */
public class MyListView extends ListView {
	private static final String TAG = "MyListView";
	//当前选中行
	private MyListViewItem click_ListItem;

	public MyListView(Context context) {
		super(context);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	/**
	 * 重点在次方法，实现事件分发
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		 switch (ev.getAction()) {
	        case MotionEvent.ACTION_DOWN: {
	            int x = (int) ev.getX();
	            int y = (int) ev.getY();
	            //通过父类方法得到当前选中的行数
	            int position = pointToPosition(x, y);
	            if (position != INVALID_POSITION) {
	            	//通过父类方法取得选中行的数据
	                Model_Item data = (Model_Item) getItemAtPosition(position);
	                click_ListItem = data.listItem;
	            }
	        }
	        default:
	            break;
	        }
		 	
	        if (click_ListItem != null) {
	        	//将触摸时间交给选中行自己处理
	        	click_ListItem.disposeTouchEvent(ev);
		}
		return super.onTouchEvent(ev);
	}
	
	

}
