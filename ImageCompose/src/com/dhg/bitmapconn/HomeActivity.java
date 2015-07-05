package com.dhg.bitmapconn;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity implements OnClickListener,OnItemClickListener {
	private ListView mylistview;
	private List<ImageInfo> data;
	private MyAdapter adapter;
	private ImageView tqqj;
	private ImageButton add_btn;
	private ExifInterface e;
	private float mTempHeight = 0.000000f;
	private static final float WIDTH = 600;
	private float total_height;
	private Canvas canvas;
	private List<Float> scale_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		initView();
		check();
	}
	
	private void check() {
		// TODO Auto-generated method stub
		if(data.size()<1){
			tqqj.setVisibility(View.VISIBLE);
			
		}else{
			tqqj.setVisibility(View.GONE);
		}
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mylistview.setOnItemClickListener(this);
		
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		mylistview = (ListView) findViewById(R.id.mylistview);
		add_btn = (ImageButton) findViewById(R.id.add_btn);
		add_btn.setOnClickListener(this);
		scale_list = new ArrayList<Float>();
		data = new ArrayList<HomeActivity.ImageInfo>();
		adapter = new MyAdapter();
		tqqj=(ImageView) findViewById(R.id.tqqj);
		mylistview.setAdapter(adapter);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File root = Environment.getExternalStorageDirectory();
			File mydir = new File(root.getAbsolutePath() + File.separator
					+ "长图生成器");
			if (!mydir.exists()) {
				mydir.mkdirs();
				
				
			}
			if (mydir.listFiles().length > 0) {
				selectImage(mydir.getAbsolutePath());
				

			} else {
				
				
				
				
				
				Toast.makeText(HomeActivity.this, "欢迎使用哦~", Toast.LENGTH_SHORT)
						.show();
			}

		} else {
			Toast.makeText(HomeActivity.this, "没有找到存储设备！", Toast.LENGTH_LONG)
					.show();
			return;

		}
	}

	class MyAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(data.size()-1-position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return data.size()-1-position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(HomeActivity.this).inflate(
						R.layout.imageitem, null);
				holder = new ViewHolder();
				holder.image_image = (ImageView) convertView
						.findViewById(R.id.image_item);
				holder.image_name = (TextView) convertView
						.findViewById(R.id.image_name);
				holder.image_path = (TextView) convertView
						.findViewById(R.id.image_path);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ImageInfo info = data.get(data.size()-1-position);
			holder.image_image.setImageBitmap(info.getImage());
			holder.image_name.setText(info.getImg_name());
			holder.image_path.setText(info.getImg_path());
			return convertView;
		}

	}

	static class ViewHolder {
		private TextView image_name;
		private ImageView image_image;
		private TextView image_path;

	}

	class ImageInfo {
		private Bitmap image;
		private String img_name;
		private String img_path;

		public Bitmap getImage() {
			return image;
		}

		public void setImage(Bitmap image) {
			this.image = image;
		}


		public String getImg_name() {
			return img_name;
		}

		public void setImg_name(String img_name) {
			this.img_name = img_name;
		}

		public String getImg_path() {
			return img_path;
		}

		public void setImg_path(String img_path) {
			this.img_path = img_path;
		}
	}

	public void judgeFile(File file) {
		if (file.getName().toLowerCase().contains(".jpg")) {
			BitmapFactory.Options op = new BitmapFactory.Options();
			op.inJustDecodeBounds = true;
			Bitmap c = BitmapFactory.decodeFile(file.getAbsolutePath(), op);
			int scale = (op.outWidth / 100) > 1 ? (op.outWidth / 100) : 1;
			op.inJustDecodeBounds = false;
			op.inSampleSize = scale;
			Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath(), op);
			ImageInfo i = new ImageInfo();
			i.setImage(b);
			String t = "unknown";
			try {
				e = new ExifInterface(file.getPath());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			t = e.getAttribute(ExifInterface.TAG_DATETIME);
			i.setImg_name("图片名称： "+file.getName());
			i.setImg_path("存储路径： "+file.getPath());
			data.add(i);
		}
	}

	public void selectImage(String filepath) {
		File file = new File(filepath);
		if (file.listFiles().length > 0) {
			File[] subfiles = file.listFiles();
			for (File myfile : subfiles) {
				if (myfile.isDirectory()) {
					selectImage(myfile.getAbsolutePath());
				} else {

					judgeFile(myfile);
				}

			}
		} else {
			judgeFile(file);

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.add_btn) {

			Intent intent = new Intent(HomeActivity.this, AddImage.class);
			startActivityForResult(intent, 30);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if ( resultCode == RESULT_OK) {
			List<Uri> list = MyApplication.getLsit();
			if(list!=null){
				LinkImage(list);
			}
			

		}
		check();

	}

	private void LinkImage(List<Uri> list) {
		// TODO Auto-generated method stub
		ContentResolver cr=getContentResolver();
		BitmapFactory.Options op=new BitmapFactory.Options();
		op.inJustDecodeBounds=true;
		for(Uri uri:list){
			
			try {
				Bitmap b=BitmapFactory.decodeStream(cr.openInputStream(uri), null, op);
				float scale=op.outWidth/WIDTH;
				scale_list.add(scale);
				total_height+=op.outHeight/scale;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		Bitmap final_bitmap=Bitmap.createBitmap((int)WIDTH, (int)total_height, Config.ARGB_8888);
		
		canvas=new Canvas(final_bitmap);
		
		
		
		
		
	 for(int i=0; i<list.size();i++){
		 
		 Matrix matrix=new Matrix();
		 float scale=scale_list.get(i);
		 matrix.setScale(1/scale,1/scale);
		 try {
			Bitmap b=BitmapFactory.decodeStream(cr.openInputStream(list.get(i)));
			Bitmap use2draw=Bitmap.createBitmap(b, 0,0, b.getWidth(), b.getHeight(), matrix, false);
			canvas.drawBitmap(use2draw, 0, mTempHeight	, null);
			mTempHeight+=use2draw.getHeight();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//			op.inSampleSize=(int)scale_list.get(i).floatValue();
//			try {
//				
//				Bitmap b=BitmapFactory.decodeStream(cr.openInputStream(list.get(i)), null, op);
//				canvas.drawBitmap(b, 0,mTempHeight,null);
//				mTempHeight+=b.getHeight();
//			
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
		}
		canvas.save(Canvas.ALL_SAVE_FLAG);
		mTempHeight=0;
		scale_list.clear();
		total_height=0;
		
		write2SD(final_bitmap);
		
		
		
		
		
		
		
	}

	private void write2SD(Bitmap bitmap) {
		// TODO Auto-generated method stub
		File file = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "长图生成器");
		String timerightnow = System.currentTimeMillis()
				+ new Random().nextInt(100) + ".jpg";
		File imageview = new File(file.getAbsolutePath() + File.separator
				+ timerightnow);
		if (!imageview.exists()) {
			try {
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(imageview));
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				bos.flush();
				bos.close();

				Toast.makeText(this, "长图制作成功", Toast.LENGTH_SHORT).show();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		ImageInfo info=new ImageInfo();
		info.setImage(bitmap);
		info.setImg_name("图片名称 "+imageview.getName());
		info.setImg_path("存储路径： "+imageview.getAbsolutePath());
		data.add(info);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		ImageInfo d=(ImageInfo) arg0.getItemAtPosition(arg2);
		String ss=d.getImg_path();
		String path=ss.substring(6);
		Intent i=new  Intent();
		i.setAction(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.fromFile(new File(path)), "image/*");
		startActivity(i);
		
		
	}

}
