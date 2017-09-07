package com.yc.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.bean.Memberfee;
import com.yc.dao.DBHelper;
import com.yc.web.model.JsonModel;
import com.yc.web.servlets.BasicServlet;



public class MemberfeeByYear extends BasicServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DBHelper dbHelper=new DBHelper();
		JsonModel jm=new JsonModel();
	
		
		List<Memberfee> list;
		try {
			String sql="select * from memberfee";
			list = dbHelper.find(sql,Memberfee.class);
			
			jm.setCode(1);
			jm.setObj(list);
			super.outJson(jm, response);
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setErrorMsg(e.getMessage());
		}
	}

}
