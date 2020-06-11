/***********************************************
 * File Name: OpensListener
 * @author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 08 01 2020 下午 2:02
 ***********************************************/

package com.duochuang.listener;

import com.fxcore2.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class OpensListener implements IO2GTableListener {

	public static HashMap<String,O2GRow> opens=new HashMap<>();

	@Override
	public void onAdded(String s, O2GRow o2GRow) {

	}

	@Override
	public void onChanged(String s, O2GRow o2GRow) {
		O2GTradeTableRow row = (O2GTradeTableRow) o2GRow;
		opens.put(row.getTradeID(),row);
		System.out.println("开仓：");
		System.out.print("accountId:"+row.getAccountID()+"  ");
		System.out.print("accountKind:"+row.getAccountKind()+"  ");
		System.out.print("accountName:"+row.getAccountName()+"  ");
		System.out.print("amount:"+row.getAmount()+"  ");
		System.out.print("side:"+row.getBuySell()+"  ");
		System.out.print("cell:"+row.getCell(3)+"  ");
		System.out.print("columns:"+row.getColumns()+"  ");
		System.out.print("account: "+row.getDividends()+"  ");
		System.out.print("account: "+row.getOfferID()+"  ");
		System.out.print("account: "+row.getOpenOrderID()+"  ");
		System.out.print("account: "+row.getOpenOrderReqID()+"  ");
		System.out.print("account: "+row.getOpenOrderRequestTXT()+"  ");
		System.out.print("account: "+row.getOpenQuoteID()+"  ");
		System.out.print("account: "+row.getOpenRate()+"  ");
		System.out.print("account: "+row.getOpenTime()+"  ");
		System.out.print("account: "+row.getParties()+"  ");
		System.out.print("account: "+row.getRolloverInterest()+"  ");
		System.out.print("account: "+row.getTableType()+"  ");
		System.out.print("account: "+row.getTradeID()+"  ");
		System.out.print("account: "+row.getTradeIDOrigin()+"  ");
		System.out.print("account: "+row.getValueDate()+"  ");
		System.out.print("account: "+row.isCellChanged(3)+"  ");
		System.out.print("account: "+row.getNativePointer());
		System.out.print("account: "+row.getCommission());
	}

	@Override
	public void onDeleted(String s, O2GRow o2GRow) {

	}

	@Override
	public void onStatusChanged(O2GTableStatus o2GTableStatus) {

	}
}
