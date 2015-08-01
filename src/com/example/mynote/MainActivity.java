package com.example.mynote;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynote.MyListViewItem.OnSlideListener;

public class MainActivity extends ActionBarActivity implements OnClickListener, OnSlideListener {

	MyListView listView;
	// 最后一次操作的item
	MyListViewItem lastChangeListViteItem;
	ArrayList<Model_Item> models = new ArrayList<Model_Item>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		listView = (MyListView) findViewById(R.id.mylistview);
		// 添加伪数据
		for (int i = 0; i < 5; i++) {
			Model_Item item = new Model_Item();
			models.add(item);
		}

		listView.setAdapter(new MyListViewAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					Toast.makeText(MainActivity.this, "click listview item", Toast.LENGTH_SHORT).show();
			}
		});
	}

	class MyListViewAdapter extends BaseAdapter{
		 private LayoutInflater mInflater;
		 
		public MyListViewAdapter() {
			super();
			mInflater = getLayoutInflater();
		}

		@Override
		public int getCount() {
			return models.size();
		}

		@Override
		public Object getItem(int position) {
			return models.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
	            MyListViewItem imteView = (MyListViewItem) convertView;
	            if (imteView == null) {
	                imteView = new MyListViewItem(MainActivity.this);
	            } 
	            Model_Item item = models.get(position);
	            item.listItem = imteView;
	            item.listItem.shrink();

	            return imteView;

		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onClick(View v) {

	}

	
	
}
