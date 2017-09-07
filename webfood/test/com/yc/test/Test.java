package com.yc.test;

import java.sql.Connection;

import com.yc.dao.DBHelper;
import com.yc.utils.YcConstant;

import redis.clients.jedis.Jedis;

public class Test {
	public static void main(String[] args) {
		DBHelper db=new DBHelper();
		 Jedis jedis=new Jedis(YcConstant.REDIS_URL);
		 jedis.connect();
		System.out.println("---------------------" );
		Connection con=db.getConn();
		
		System.out.println(con);
		
		String str="123";
		
		int a=Integer.valueOf(str);
		System.out.println(a);
		//String b=String.valueOf(a);
		//String b=Integer.toString(a);
		String b=a+"";
		
		System.out.println(b.getClass().getSimpleName());
	}
}
