package com.yc.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yc.biz.ResfoodBiz;
import com.yc.biz.impl.ResfoodBizImpl;


public class ResfoodServlet extends BasicServlet{
	private static final long serialVersionUID = 1L;

	private ResfoodBiz resfoodBiz=new ResfoodBizImpl();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if("".equals(op)){
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}/*else if("findAll".equals(op)){
				findAll(request,response);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
/*
	private void findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonModel jm=new JsonModel();
		
		List<Resfood> list;
		try {
			
			list = resfoodBiz.findAll();
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
	}*/

}
