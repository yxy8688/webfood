package com.yc.biz.impl;

import java.io.IOException;
import java.util.Date;


import com.yc.biz.HistoryBiz;
import com.yc.utils.YcConstant;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class HistoryBizImpl implements HistoryBiz  {
	
	private Jedis jedis=new Jedis(YcConstant.REDIS_URL);
	
	public static final String REDIS_USER_HISTORY="uid:";
	
	public void saveHistory(int uid,String data) throws IOException{
	
		//求出时间
		Date d=new Date();
		Pipeline pl=jedis.pipelined();    //管道
		pl.zadd(REDIS_USER_HISTORY+uid, d.getTime(),data);
		
		pl.expire(REDIS_USER_HISTORY+uid, 30*24*60*60);  //有效性
		
		Response<Long> res=pl.zcard(REDIS_USER_HISTORY+uid); //读取所有 ？？
		
		pl.close();
		
	}
	
	public Object[] getHistory(int uid) throws IOException {
		return jedis.zrevrange(REDIS_USER_HISTORY+uid, 0, 9).toArray();
	}
	
	
	
	
	

}
