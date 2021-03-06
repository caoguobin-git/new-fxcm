/***********************************************
 * File Name: TestController
 * @author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 13 12 2019 下午 1:52
 ***********************************************/

package com.duochuang.controller;

import com.candleworks.pricehistorymgr.IPriceHistoryCommunicator;
import com.candleworks.pricehistorymgr.PriceHistoryCommunicatorFactory;
import com.duochuang.listener.OffersListener;
import com.duochuang.listener.OpensListener;
import com.duochuang.listener.SessionStatusListener;
import com.duochuang.test.LoginTest;
import com.fxcore2.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@Controller
@RequestMapping(value = "/test")
public class TestController {
	private O2GSession mSession = null;
	private SessionStatusListener statusListener;

	@GetMapping(value = "/login")
	public void login(){
		LoginTest loginTest=new LoginTest();
		loginTest.run();
	}


	@ResponseBody
	@PostMapping("/user")
	public Object login(String username,String password){

		// Create a session, subscribe to session listener, login, get accounts, logout
		try {
			mSession = O2GTransport.createSession();

//			IPriceHistoryCommunicator communicator = PriceHistoryCommunicatorFactory.createCommunicator();

			statusListener = new SessionStatusListener(mSession, "dbNameoginTest", "mPin");
			mSession.subscribeSessionStatus(statusListener);
			mSession.useTableManager(O2GTableManagerMode.YES,null);
			mSession.login("701116547", "890128", "http://www.fxcorporate.com/Hosts.jsp", "Demo");
			while (!statusListener.isConnected() && !statusListener.hasError()) {
				Thread.sleep(50);
			}
			if (!statusListener.hasError()) {

				O2GTableManager manager = mSession.getTableManager();
				while (manager.getStatus() == O2GTableManagerStatus.TABLES_LOADING) {
					Thread.sleep(50);
				}

				O2GOffersTable offers = null;
				OffersListener listener = null;
				if (manager.getStatus() == O2GTableManagerStatus.TABLES_LOADED) {
					offers = (O2GOffersTable)manager.getTable(O2GTableType.OFFERS);
					listener = new OffersListener();
					listener.SetInstrumentFilter("XAU/USD");
					offers.subscribeUpdate(O2GTableUpdateType.UPDATE, listener);
				}


				if (offers != null) {
					offers.unsubscribeUpdate(O2GTableUpdateType.UPDATE, listener);
				}


//				while (!statusListener.isDisconnected()) {
//					Thread.sleep(50);
//				}


			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "ok";
	}


	@GetMapping("/history")
	@ResponseBody
	public Object history(int year,int month,int date,String account) throws IOException {
		Calendar start= GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		start.set(Calendar.YEAR, year);
		start.set(Calendar.MONTH, month);
		start.set(Calendar.DATE, date);
		Calendar end=GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		System.out.println(mSession.getUserName());
		String reportURL = mSession.getReportURL(account, start, end, "HTML", "chs");
		System.out.println(reportURL);


//		Map<String, String> cookies = null;
//		Connection.Response res = Jsoup.connect(reportURL).timeout(30000).execute();
//		cookies = res.cookies();

//		Document document = Jsoup.parse(new URL(reportURL), 5000);
//		System.out.println(document);
////		System.out.println(document);

		return reportURL;
	}

	@GetMapping(value = "/open")
	@ResponseBody
	public Object getOpen(){
		return OpensListener.opens;
	}

	@DeleteMapping("/user")
	public void logout(){
		mSession.logout();
		mSession.unsubscribeSessionStatus(statusListener);
		mSession.dispose();
	}
}
