package com.yc.web.listeners;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBiz;
import com.yc.biz.impl.ResfoodBizImpl;

public class InitListener implements ServletContextListener {

    public InitListener() {
    }

    /**
     * 监听 application  创建
     */
    public void contextInitialized(ServletContextEvent event)  { //event 就是application
    		ResfoodBiz rb=new ResfoodBizImpl();
    		try {
				List<Resfood> list=rb.findAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
    		//利用application 存 -> 内存太小，无法扩展， 2，当服务器关闭时
		//	ServletContext application=event.getServletContext();
		//	application.setAttribute("list", list);
    		
    }

    public void contextDestroyed(ServletContextEvent arg0)  { 
    }
	
}
