package com.yc.web.listeners;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.yc.utils.YcConstant;
import com.yc.utils.redisutil.fn1.userlogin.UserRedis;

import redis.clients.jedis.Jedis;


public class CleanRedisOnlineUserListen implements ServletContextListener {

	private Jedis jedis=new Jedis(YcConstant.REDIS_URL);
	private Timer t;

    public void contextInitialized(ServletContextEvent event)  { 
    	//启动定时器
    	//System.out.println("启动定时器");
    	//如果用户提出要重新设计定时器启动的时间，则可以考虑用这种方法
    	
    	
    	
    	
    	t=new Timer();
    	
    	Calendar c=Calendar.getInstance();
    	c.set(Calendar.HOUR,2);
    	c.set(Calendar.MINUTE, 0);
    	
    	
    	t.schedule(new TimerTask(){
    		 public void run() {
     	    	UserRedis.keepNDaysRecord(jedis, YcConstant.KEEPNDAYSFORLINEUSER);
     	    }
    	}, c.getTime(),24*60*60*1000);
    }
    
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	//销毁定时器
    	System.out.println("销毁定时器");
    	if(t!=null){
    		t.cancel();
    	}
    }
    
}





