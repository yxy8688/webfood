package com.yc.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.bean.Active;
import com.yc.biz.IActive;
import com.yc.biz.impl.ActiveBizImpl;
import com.yc.web.model.JsonModel;


public class ActiveServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;
       
   private IActive active=new ActiveBizImpl();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op=request.getParameter("op");
		
		if("showActiveContent".equals(op)){
			showActiveContent(request,response);
		}
	}


	private void showActiveContent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		JsonModel jm=new JsonModel();
		
		List<Active> list;
		try {
			
			list = active.findAll();
			//code:1 -> obj:存数据
			//code:0 -> msg:错误的信息
			jm.setCode(1);
			jm.setObj(list);
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setErrorMsg(e.getMessage());
		}

		super.outJson(jm, response);
	}

}
