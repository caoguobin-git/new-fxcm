/***********************************************
 * File Name: GetMarginTest
 * @author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 30 12 2019 下午 1:22
 ***********************************************/

package com.duochuang.test;

import com.duochuang.listener.ResponseListener;
import com.duochuang.listener.SessionStatusListener;
import com.fxcore2.*;

import java.util.Scanner;

public class GetMarginTest {
	static O2GSession mSession=null;
	public static void main(String[] args) {

		ResponseListener responseListener = new ResponseListener();
		System.out.println("Please unsubscribe from " + "XAU/USD");
		// ...
		mSession = O2GTransport.createSession();

		SessionStatusListener statusListener = new SessionStatusListener(mSession, "dbNameoginTest", "mPin");
		mSession.subscribeSessionStatus(statusListener);
		mSession.subscribeResponse(responseListener);
		mSession.useTableManager(O2GTableManagerMode.YES,null);
		mSession.login("701116547", "890128", "http://www.fxcorporate.com/Hosts.jsp", "Demo");

		while (!statusListener.isConnected() && !statusListener.hasError()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// ...
		String offerInstrument = "XAU/USD";
		if (!offerInstrument.isEmpty()) {
			subscribeInstrument(offerInstrument, "XAU/USD", responseListener);
			showMarginRequirements("XAU/USD");
//			updateMarginRequirements(responseListener);
//			showMarginRequirements("XAU/USD");
		}
		new Scanner(System.in).nextLine();


	}


	public static void subscribeInstrument(String offerInstrument, String instrument, ResponseListener responseListener) {
		O2GRequestFactory requestFactory = mSession.getRequestFactory();
		if (requestFactory != null) {
			O2GValueMap valueMap = requestFactory.createValueMap();
			valueMap.setString(O2GRequestParamsEnum.COMMAND, com.fxcore2.Constants.Commands.SetSubscriptionStatus);
			valueMap.setString(O2GRequestParamsEnum.SUBSCRIPTION_STATUS, "T");
			valueMap.setString(O2GRequestParamsEnum.OFFER_ID, offerInstrument);
			O2GRequest request = requestFactory.createOrderRequest(valueMap);
			if (request == null) {
				System.out.println("Order's request can not be created: " + requestFactory.getLastError());
			} else {
				mSession.sendRequest(request);
			}
			// ...
		}
	}

	public static void updateMarginRequirements(ResponseListener responseListener) {
		O2GRequestFactory requestFactory = mSession.getRequestFactory();
		if (requestFactory != null) {
			O2GValueMap valueMap = requestFactory.createValueMap();
			valueMap.setString(O2GRequestParamsEnum.COMMAND, com.fxcore2.Constants.Commands.UpdateMarginRequirements);
			O2GRequest request = requestFactory.createOrderRequest(valueMap);
			mSession.sendRequest(request);
			//...
		}
	}

	public static void showMarginRequirements(String instrument) {
		O2GLoginRules loginRules = mSession.getLoginRules();
		O2GTradingSettingsProvider tradingSetting = loginRules.getTradingSettingsProvider();
		O2GResponse accountsResponse = loginRules.getTableRefreshResponse(O2GTableType.ACCOUNTS);
		O2GResponseReaderFactory responseReaderFactory = mSession.getResponseReaderFactory();
		if (responseReaderFactory == null) {
			return;
		}
		O2GAccountsTableResponseReader accounts = responseReaderFactory.createAccountsTableReader(accountsResponse);
		O2GAccountRow accountRow = accounts.getRow(0);
		O2GMargin margin = tradingSetting.getMargins(instrument, accountRow);
		O2GMargin margin1 = tradingSetting.getMargins("XAG/USD", accountRow);
		System.out.println("mmr=" + margin.getMMR() + "; emr=" + margin.getEMR() + "; lmr=" + margin.getLMR());
		System.out.println("mmr=" + margin1.getMMR() + "; emr=" + margin1.getEMR() + "; lmr=" + margin1.getLMR());
	}


	public void onRequestCompleted(String requestID, O2GResponse response) {

	}
}
