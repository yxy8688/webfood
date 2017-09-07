package com.yc.web.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.SortedSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.bean.Resuser;
import com.yc.biz.HistoryBiz;
import com.yc.biz.impl.HistoryBizImpl;
import com.yc.utils.YcConstant;
import com.yc.web.model.JsonModel;

import redis.clients.jedis.Jedis;

public class HistoryServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;
	
	private Jedis jedis=new Jedis(YcConstant.REDIS_URL);
	
	HistoryBizImpl hb=new HistoryBizImpl();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			if("".equals(op)){
				//request.getRequestDispatcher("index.jsp").forward(request, response);
			}else if("saveHistory".equals(op)){
				saveHistoryOp(request,response);
			}else if("getHistory".equals(op)){
				getHistoryOp(request,response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	protected void getHistoryOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session=request.getSession();
		Resuser resuser=(Resuser) session.getAttribute(YcConstant.LOGIN_USER);
		
		HistoryBiz hb=new HistoryBizImpl();
	
		Object[] history=hb.getHistory(resuser.getUserid());
		JsonModel jm=new JsonModel();
		jm.setCode(1);
		jm.setObj(history);
		
		super.outJson(jm, response);
	}

	protected void saveHistoryOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String historyData=request.getParameter("data");
		//取出最新的十条记录
		HttpSession session=request.getSession();
		Resuser resuser=(Resuser) session.getAttribute(YcConstant.LOGIN_USER);
		HistoryBiz hb=new HistoryBizImpl();
		hb.saveHistory(resuser.getUserid(), historyData);
		
		Object[] history=hb.getHistory(resuser.getUserid());
		JsonModel jm=new JsonModel();
		jm.setCode(1);
		jm.setObj(history);
		
		super.outJson(jm, response);
	}

}
