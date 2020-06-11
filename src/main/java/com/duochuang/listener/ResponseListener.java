/***********************************************
 * File Name: ResponseListener
 * @author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 30 12 2019 下午 1:23
 ***********************************************/

package com.duochuang.listener;

import com.fxcore2.IO2GResponseListener;
import com.fxcore2.O2GResponse;
import com.fxcore2.O2GResponseType;

public class ResponseListener implements IO2GResponseListener {
	@Override
	public void onRequestCompleted(String s, O2GResponse o2GResponse) {

	}

	@Override
	public void onRequestFailed(String s, String s1) {

	}

	@Override
	public void onTablesUpdates(O2GResponse o2GResponse) {

			System.out.println(1);
			System.out.println(o2GResponse.toString());
			System.out.println(2);
	}
}
