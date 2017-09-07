package com.yc.biz;

import java.io.IOException;

public interface HistoryBiz {
	
	public void saveHistory(int uid,String data) throws IOException;
	public Object[] getHistory(int uid) throws IOException ;
}
