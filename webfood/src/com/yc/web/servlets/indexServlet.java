package com.yc.web.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.bean.Resfood;
import com.yc.bean.Resuser;
import com.yc.biz.ResfoodBiz;
import com.yc.biz.ResuserBiz;
import com.yc.biz.impl.ResfoodBizImpl;
import com.yc.biz.impl.ResuserBizImpl;
import com.yc.utils.YcConstant;
import com.yc.web.model.JsonModel;

public class indexServlet extends BasicServlet {

	private static final long serialVersionUID = 1L;

	private ResfoodBiz resfoodBiz=new ResfoodBizImpl();
	private ResuserBiz rb=new ResuserBizImpl();

	JsonModel jm=new JsonModel();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		try {
			findAll(request,response);
			findcartOp(request,response);
			isloginOp(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.outJson(jm, response);
	}

	private void findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*JsonModel jm=new JsonModel();*/
		List<Resfood> list;
		try {
			list = resfoodBiz.findAll();
			jm.setCode1(1);
			jm.setObj1(list);
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode1(0);
			jm.setErrorMsg(e.getMessage());
		}
		//super.outJson(jm, response);
	}
	private void findcartOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		Map<Integer,Resfood> cart=null;
		if(session.getAttribute( YcConstant.CART_NAME)!=null){
			cart=(Map<Integer, Resfood>) session.getAttribute(YcConstant.CART_NAME);
		}
	//	JsonModel jm=new JsonModel();
		if(cart==null){
			jm.setCode2(0);
		}else{
			jm.setCode2(1);
			jm.setObj2(cart);
		}
	//	super.outJson(jm, response);
	}
	private void isloginOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
	//	JsonModel jm=new JsonModel();
		if(session.getAttribute(YcConstant.LOGIN_USER)!=null){
			Resuser resuser=(Resuser) session.getAttribute(YcConstant.LOGIN_USER);
			jm.setCode3(1);
			resuser.setPwd(null);
			jm.setObj3(resuser);
		}else{
			jm.setCode3(0);
		}
	//	super.outJson(jm, response);
	}
}
