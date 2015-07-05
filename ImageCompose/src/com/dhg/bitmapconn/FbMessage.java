package com.dhg.bitmapconn;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FbMessage extends Activity implements OnClickListener{
	private TextView return_btn;
	private Button sure;
	private EditText edit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bmob.initialize(this, "a5dac1c2c2862e3fd56c1d1ae08a55ad");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.feedback_layout);
		return_btn=(TextView) findViewById(R.id.return_btn);
		sure=(Button) findViewById(R.id.sure);
		edit=(EditText) findViewById(R.id.myfeedback);
		return_btn.setOnClickListener(this);
		sure.setOnClickListener(this);
		edit.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.myfeedback:
			edit.setHint("");
			
			break;
		case R.id.return_btn:
			finish();
			
			
			break;

		case R.id.sure:
			String fbtext=edit.getText().toString();
			if(fbtext.equals("")){
				Toast.makeText(this, "请填写内容", Toast.LENGTH_SHORT).show();
				return;
			}
			FeedBack fb=new FeedBack();
			
			fb.setAdvice(fbtext);
			fb.save(FbMessage.this, new SaveListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(FbMessage.this, "反馈成功，谢谢你的建议~", Toast.LENGTH_LONG).show();
					FbMessage.this.finish();
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					
					Toast.makeText(FbMessage.this, "网络出小差了哦", Toast.LENGTH_LONG).show();
					
				}
			});
			break;
		}
		
	}

}
