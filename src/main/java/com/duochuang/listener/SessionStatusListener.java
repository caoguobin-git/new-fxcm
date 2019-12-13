/***********************************************
 * File Name: MySessionStatusListener
 * @author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 13 12 2019 上午 11:33
 ***********************************************/

package com.duochuang.listener;

import com.fxcore2.*;

public class SessionStatusListener implements IO2GSessionStatus {
	/** Connection , session and status variables*/
	private boolean mConnected = false;
	private boolean mDisconnected = false;
	private boolean mError = false;
	private String mDBName = "";
	private String mPin = "";
	private O2GSession mSession = null;
	private O2GSessionStatusCode mStatus = null;

	/** Constructor*/
	public SessionStatusListener(O2GSession session, String dbName, String pin) {
		mSession = session;
		mDBName = dbName;
		mPin = pin;
	}

	/** Shows if session is connected*/
	public final boolean isConnected() {
		return mConnected;
	}

	/** Shows if session is disconnected*/
	public final boolean isDisconnected() {
		return mDisconnected;
	}

	/** Shows if there was an error during the login process*/
	public final boolean hasError() {
		return mError;
	}

	/** Returns current session status*/
	public final O2GSessionStatusCode getStatus() {
		return mStatus;
	}

	/** Implementation of IO2GSessionStatus interface public method onSessionStatusChanged*/
	@Override
	public final void onSessionStatusChanged(O2GSessionStatusCode status) {
		mStatus = status;
		System.out.println("Status: " + mStatus.toString());
		if (mStatus == O2GSessionStatusCode.CONNECTED) {
			mConnected = true;
		}
		else {
			mConnected = false;
		}
		if (status == O2GSessionStatusCode.DISCONNECTED) {
			mDisconnected = true;
		}
		else {
			mDisconnected = false;
		}
		if (mStatus == O2GSessionStatusCode.TRADING_SESSION_REQUESTED) {
			O2GSessionDescriptorCollection descs = mSession.getTradingSessionDescriptors();
			System.out.println("\nSession descriptors");
			System.out.println("id, name, description, requires pin");
			for (O2GSessionDescriptor desc : descs) {
				System.out.println(desc.getId() + " " + desc.getName() + " " + desc.getDescription() + " " + desc.isPinRequired());
			}
			if (mDBName.equals("")) {
				System.out.println("Argument for trading session ID is missing");
			}
			else {
				mSession.setTradingSession(mDBName, mPin);
			}
		}
	}

	/** Implementation of IO2GSessionStatus interface public method onLoginFailed*/
	@Override
	public final void onLoginFailed(String error) {
		System.out.println("Login error: " + error);
		mError = true;
	}
}
