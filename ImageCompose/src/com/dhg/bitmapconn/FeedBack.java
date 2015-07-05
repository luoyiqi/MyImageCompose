package com.dhg.bitmapconn;

import cn.bmob.v3.BmobObject;

public class FeedBack extends BmobObject{
	private String qq;
	private String advice;
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	
	

}
