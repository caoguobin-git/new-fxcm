/***********************************************
 * File Name: LoginTest
 * @author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 13 12 2019 上午 11:33
 ***********************************************/

package com.duochuang.test;

import com.duochuang.listener.OffersListener;
import com.duochuang.listener.OpensListener;
import com.duochuang.listener.SessionStatusListener;
import com.fxcore2.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class LoginTest {
	public void run() {

		String mInstrument = "";
		O2GSession mSession = null;

		// Create a session, subscribe to session listener, login, get accounts, logout
		try {
			mSession = O2GTransport.createSession();
			SessionStatusListener statusListener = new SessionStatusListener(mSession, "dbNameoginTest", "mPin");
			mSession.subscribeSessionStatus(statusListener);
			mSession.useTableManager(O2GTableManagerMode.YES,null);
			mSession.login("701172924", "7723", "http://www.fxcorporate.com/Hosts.jsp", "Demo");
			while (!statusListener.isConnected() && !statusListener.hasError()) {
				Thread.sleep(50);
			}
			if (!statusListener.hasError()) {

				O2GTableManager manager = mSession.getTableManager();
				while (manager.getStatus() == O2GTableManagerStatus.TABLES_LOADING) {
					Thread.sleep(50);
				}

				O2GOffersTable offersTable = null;
				O2GTradesTable opensTable = null;
				OffersListener offersListener = null;
				OpensListener opensListener = null;
				if (manager.getStatus() == O2GTableManagerStatus.TABLES_LOADED) {
					offersTable = (O2GOffersTable)manager.getTable(O2GTableType.OFFERS);
					opensTable= (O2GTradesTable) manager.getTable(O2GTableType.TRADES);
					offersListener = new OffersListener();
					opensListener=new OpensListener();
					offersListener.SetInstrumentFilter("XAU/USD");
					offersTable.subscribeUpdate(O2GTableUpdateType.UPDATE, offersListener);
					opensTable.subscribeUpdate(O2GTableUpdateType.UPDATE, opensListener);
				}

				System.out.println("Press enter to stop!");
				System.in.read();

				if (offersTable != null) {
					offersTable.unsubscribeUpdate(O2GTableUpdateType.UPDATE, offersListener);
				}

				Calendar start=GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
				start.set(Calendar.MONTH, start.get(Calendar.MONTH)-1);
				Calendar end=GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));

				String reportURL = mSession.getReportURL("701116547", start, end, "XML", "chs");
				System.out.println(reportURL);
				mSession.logout();
				while (!statusListener.isDisconnected()) {
					Thread.sleep(50);
				}


			}
			mSession.unsubscribeSessionStatus(statusListener);
			mSession.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
