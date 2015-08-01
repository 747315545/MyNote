package com.example.mynote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义组件--实现自定义ListView中的每一行 自己处理触摸事件，实现滑动动画效果。
 * 
 * @author Administrator
 * 
 */
@SuppressLint("NewApi")
public class MyListViewItem extends LinearLayout {
	private static final String TAG = "MyListViewItem";
	//当前上下文
	Context context;
	// 用于实现动画效果
	private Scroller scroller;
	// 内容显示区域的宽度(dp单位),控制滑动距离
	private int content_width = 120;
	// 分别记录上次滑动的坐标
	private int mLastX = 0;
	private int mLastY = 0;
	// 用来判断触摸的滑动角度，仅当角度a满足如下条件才进行滑动：tan a = deltaX / deltaY > 2
	private static final int TAN = 2;

	public MyListViewItem(Context context) {
		super(context);
		initView();
	}

	public MyListViewItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public MyListViewItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		context = getContext();
		// 初始化弹性滑动对象
		scroller = new Scroller(context);
		// 设置方向为横向
		setOrientation(LinearLayout.HORIZONTAL);
		// 将slide_view_merge加载进来
		inflate(context, R.layout.view_listview_item, this);
		//设置内容布局的宽度
		content_width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, content_width,getResources().getDisplayMetrics()));
	}

	/**
	 * 处理触摸事件
	 * 分别处理触摸的三种状态：ACTION_DOWN，ACTION_MOVE，ACTION_UP
	 * 
	 * @param event
	 */
	public void disposeTouchEvent(MotionEvent event) {
		//获得当前触摸的坐标
		 int x = (int) event.getX();
	        int y = (int) event.getY();
	        //获得滑动控件的x坐标
	        int scrollX = getScrollX();

	        switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN: {
	            if (!scroller.isFinished()) {//如果滑动组件还在执行滑动动作，则终止动作
	                scroller.abortAnimation();
	            }
	            break;
	        }
	        case MotionEvent.ACTION_MOVE: {
	        	//定义两个增量,表示
	            int deltaX = x - mLastX;
	            int deltaY = y - mLastY;
	            if (Math.abs(deltaX) < Math.abs(deltaY) * TAN) {
	                break;
	            }

	            int newScrollX = scrollX - deltaX;
	            if (deltaX != 0) {
	                if (newScrollX < 0) {
	                    newScrollX = 0;
	                } else if (newScrollX > content_width) {
	                    newScrollX = content_width;
	                }
	                this.scrollTo(newScrollX, 0);//将控件滑动到手指移动的地方
	            }
	            break;
	        }
	        case MotionEvent.ACTION_UP: {
	            int newScrollX = 0;
	            if (scrollX - content_width * 0.75 > 0) {//根据当前位置计算是回到起点还是跑到终点
	                newScrollX = content_width;
	            }
	            this.smoothScrollTo(newScrollX, 0);
	        }
	        default:
	            break;
	        }

	        mLastX = x;
	        mLastY = y;

	}
	
	
	public void setButtonText(CharSequence text) {
        ((TextView)findViewById(R.id.delete)).setText(text);
    }

    
    /**
     * 缓慢平稳地滑动到指定位置
     * @param destX
     * @param destY
     */
    private void smoothScrollTo(int destX, int destY) {
        // 缓慢滚动到指定位置
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        scroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
        invalidate();
    }
    /**
     * 复位
     */
    public void shrink() {
        if (getScrollX() != 0) {
            this.smoothScrollTo(0, 0);
        }
    }

    //复写计算滑动的方法
    @Override
    public void computeScroll() {
    	 if (scroller.computeScrollOffset()) {
             scrollTo(scroller.getCurrX(), scroller.getCurrY());
             postInvalidate();
         }
    }
    
	public interface OnSlideListener {

		public static final int SLIDE_STATUS_OFF = 0;
		public static final int SLIDE_STATUS_START_SCROLL = 1;
		public static final int SLIDE_STATUS_ON = 2;

	
	}

}
