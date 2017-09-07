package com.yc.web.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.bean.Resuser;
import com.yc.biz.ResuserBiz;
import com.yc.biz.impl.ResuserBizImpl;
import com.yc.utils.YcConstant;
import com.yc.web.model.JsonModel;

public class ResuserServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;
  
	private ResuserBiz rb=new ResuserBizImpl();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if("".equals(op)){
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}else if("login".equals(op)){
				loginOp(request,response);
			}else if("logout".equals(op)){
				logoutOp(request,response);
			}/*else if("islogin".equals(op)){
				isloginOp(request,response);
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
	private void isloginOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		JsonModel jm=new JsonModel();
		if(session.getAttribute(YcConstant.LOGIN_USER)!=null){
			Resuser resuser=(Resuser) session.getAttribute(YcConstant.LOGIN_USER);
			jm.setCode(1);
			resuser.setPwd(null);
			jm.setObj(resuser);
		}else{
			jm.setCode(0);
		}
		super.outJson(jm, response);
	}*/

		//退出
		private void logoutOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
			//退出时，将session中的登陆用户信息删除
			HttpSession session=request.getSession();
			session.removeAttribute(YcConstant.LOGIN_USER);
			System.out.println("进入退出界面");
			//问题： 是否需要将这个用户在session 的购物车删除
			// session  生命周期 session。invalide（）  ；  关闭服务器   session  超过过期时间
			JsonModel jm=new JsonModel();
			jm.setCode(1);
			super.outJson(jm, response);
			
		}
	private void loginOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Resuser resuser=(Resuser) super.parseRequest(request, Resuser.class);
		JsonModel jm=new JsonModel();
		if(resuser.getUsername()==null || "".equals(resuser.getUsername())){
			jm.setCode(0);
			jm.setErrorMsg("username should not be empty");
			super.outJson(jm, response);
			return;
		}
		if(resuser.getPwd()==null || "".equals(resuser.getPwd())){
			jm.setCode(0);
			jm.setErrorMsg("password should not be empty");
			super.outJson(jm, response);
			return;
		}
		HttpSession session=request.getSession();
		String validateCode=(String) session.getAttribute("validateCode");
		System.out.println(validateCode);
		if(!validateCode.equalsIgnoreCase(resuser.getValcode())){
			jm.setCode(0);
			jm.setErrorMsg("valide code is not right");
			super.outJson(jm, response);
			return;
		}
		try {
			resuser=rb.login(resuser);
			
			if(resuser!=null){
				session.setAttribute(YcConstant.LOGIN_USER, resuser);
				jm.setCode(1);
				resuser.setPwd("");
				jm.setObj(resuser);
				super.outJson(jm, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jm.setCode(0);
		jm.setErrorMsg("error user");
		super.outJson(jm, response);
	}

}
