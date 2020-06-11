/***********************************************
 * File Name: OffersListener
 * @author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 13 12 2019 下午 12:48
 ***********************************************/

package com.duochuang.listener;

import com.fxcore2.IO2GTableListener;
import com.fxcore2.O2GOfferTableRow;
import com.fxcore2.O2GRow;
import com.fxcore2.O2GTableStatus;

import java.util.HashSet;

public class OffersListener implements IO2GTableListener {
	private String mInstrument = null;

	private HashSet<String> symbols=new HashSet<>();

	public void SetInstrumentFilter(String instrument) {
		mInstrument = instrument;
	}

	@Override
	public void onAdded(String string, O2GRow o2grow) {

	}

	@Override
	public void onChanged(String string, O2GRow o2grow) {
		O2GOfferTableRow row = (O2GOfferTableRow)o2grow;
		symbols.add(row.getInstrument());
		if (mInstrument == null || mInstrument.isEmpty() || row.getInstrument().equals(mInstrument)) {

		}
	}

	@Override
	public void onDeleted(String string, O2GRow o2grow) {

	}

	@Override
	public void onStatusChanged(O2GTableStatus ogts) {

	}

}
