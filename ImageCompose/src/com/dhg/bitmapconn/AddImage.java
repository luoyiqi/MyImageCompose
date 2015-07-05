package com.dhg.bitmapconn;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddImage extends Activity implements OnClickListener {
	private List<Uri> list;
	private Button add, end;
	private MyGridViewAdapter adapter;
	private Button feedback;
	private ImageView emyptyimg;
	private GridView mygridview;
	private int i = 1;
	private List<HashMap<String, Object>> source;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_layout);
		

		emyptyimg = (ImageView) findViewById(R.id.emptyimg);
		mygridview = (GridView) findViewById(R.id.mygridview);
		feedback=(Button) findViewById(R.id.feedback);
		list = new ArrayList<Uri>();
		source = new ArrayList<HashMap<String, Object>>();
		adapter = new MyGridViewAdapter();
		mygridview.setAdapter(adapter);
		add = (Button) findViewById(R.id.add);
		end = (Button) findViewById(R.id.end);
		add.setOnClickListener(this);
		end.setOnClickListener(this);
		feedback.setOnClickListener(this);
		CheckEmpty();

	}

	private void CheckEmpty() {
		// TODO Auto-generated method stub
		if (list.size() == 0) {
			emyptyimg.setImageResource(R.drawable.startmake);
			emyptyimg.setVisibility(View.VISIBLE);
		} else {
			emyptyimg.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) { 
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add:
			Intent i = new Intent();
			i.setAction(Intent.ACTION_PICK);
			i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, 20);
			break;
		case R.id.feedback:
			Intent fi=new Intent();
			fi.setClass(this, FbMessage.class);
			startActivity(fi);
			
			break;

		case R.id.end:
			if(list.size()==0){
				Toast.makeText(this, "亲，还没有添加图片哦~", Toast.LENGTH_SHORT).show();
			}else{
				Intent intent=new Intent();
				MyApplication.setLsit(list);
				setResult(RESULT_OK, intent);
				finish();
			}
			
			break;
		}

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 20 && resultCode == RESULT_OK) {
			Uri uri = data.getData();
			list.add(uri);
			ContentResolver cr = this.getContentResolver();
			BitmapFactory.Options op = new BitmapFactory.Options();
			op.inJustDecodeBounds = true;
			try {
				InputStream is = cr.openInputStream(uri);
				Bitmap b = BitmapFactory.decodeStream(is, null, op);
				int scale=(op.outWidth/100)>1?(op.outWidth/100):1;
				op.inJustDecodeBounds = false;
				op.inSampleSize = scale;
				Bitmap d = BitmapFactory.decodeStream(cr.openInputStream(uri), null, op);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("num", "第" + i + "张图");
				/*******  debug    ****/
				
				
				
				/*******      ****/
				map.put("img", d);
				i++;
				source.add(map);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		adapter.notifyDataSetChanged();
		CheckEmpty();

	}

	class MyGridViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return source.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return source.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder=null;
			if(convertView==null){
				
				convertView=LayoutInflater.from(AddImage.this).inflate(R.layout.item, null);
				holder=new ViewHolder();
				holder.image=(ImageView) convertView.findViewById(R.id.item_image);
				holder.text=(TextView) convertView.findViewById(R.id.item_text);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			
			holder.image.setImageBitmap((Bitmap) source.get(position).get("img"));
			holder.text.setText((CharSequence) source.get(position).get("num"));
			return convertView;
			
		}

		class ViewHolder {
			TextView text;
			ImageView image;

		}

	}

}
