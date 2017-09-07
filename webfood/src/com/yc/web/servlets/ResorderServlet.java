package com.yc.web.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.bean.Resfood;
import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.biz.ResfoodBiz;
import com.yc.biz.ResorderBiz;
import com.yc.biz.impl.ResfoodBizImpl;
import com.yc.biz.impl.ResorderBizImpl;
import com.yc.biz.impl.UserRankBizImpl;
import com.yc.utils.YcConstant;
import com.yc.web.model.JsonModel;


public class ResorderServlet extends BasicServlet{

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if( "order".equals(op)){
			orderOp(request,response);
		}else if("delorder".equals(op)){
			delorderOp(request,response);
		}else if("clearall".equals(op)){
			clearall(request,response);
		}/*else if("findcart".equals(op)){
			findcartOp(request,response);
		}*/else if("confirmorder".equals(op)){
			confirmorderOp(request,response);
		}
	}

	private void confirmorderOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		//System.out.println("session是："+session);
		JsonModel jm=new JsonModel();
		if(session.getAttribute( YcConstant.LOGIN_USER)==null){
			jm.setCode(0);
			jm.setErrorMsg("you havanot logined in ...");
			super.outJson(jm, response);
			return;
		}
		
		Map<Integer,Resfood> cart=null;
		if(session.getAttribute( YcConstant.CART_NAME)==null){
			jm.setCode(0);
			jm.setErrorMsg("you havanot order any foods ...");
			super.outJson(jm, response);
			return;
		}
		
		cart=(Map<Integer, Resfood>) session.getAttribute(YcConstant.CART_NAME);
		//System.out.println("cart:"+cart);
		
		Resuser resuser= (Resuser) session.getAttribute(YcConstant.LOGIN_USER);
		//System.out.println("resuser:"+resuser);
		
		Resorder resorder= (Resorder) super.parseRequest(request,Resorder.class);
		//System.out.println("resorder:"+resorder);
		
		resorder.setStatus(0);
		resorder.setUserid(resuser.getUserid());
		ResorderBiz rb=new ResorderBizImpl();
		try {
			rb.submitOrder(resorder, cart);
			
			//下单成功，计算总价
			double total=0;
			for(Map.Entry<Integer,Resfood> entry:cart.entrySet()){
				total+=entry.getValue().getRealprice()* entry.getValue().getNum();
			}
			UserRankBizImpl urbi=new UserRankBizImpl();
			urbi.updateScore(total, resuser.getUserid());
			session.removeAttribute(YcConstant.CART_NAME);
			jm.setCode(1);
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setErrorMsg(e.getMessage());
		}
		super.outJson(jm, response);
	}


	/*private void findcartOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		Map<Integer,Resfood> cart=null;
		if(session.getAttribute( YcConstant.CART_NAME)!=null){
			cart=(Map<Integer, Resfood>) session.getAttribute(YcConstant.CART_NAME);
		}
		JsonModel jm=new JsonModel();
		if(cart==null){
			jm.setCode(0);
		}else{
			jm.setCode(1);
			jm.setObj(cart);
		}
		super.outJson(jm, response);
	}
*/
	private void clearall(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Resfood resfood= (Resfood) super.parseRequest(request,Resfood.class);//取出 fid
	//	System.out.println(resfood);
		HttpSession session=request.getSession();
		session.removeAttribute(YcConstant.CART_NAME);
		JsonModel jm=new JsonModel();
		jm.setCode(1);
		super.outJson(jm,response);
	}

	private void delorderOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Resfood resfood= (Resfood) super.parseRequest(request,Resfood.class);//取出 fid
		HttpSession session=request.getSession();

		Map<Integer,Resfood> cart=new HashMap<Integer,Resfood>();
		
		if(session.getAttribute( YcConstant.CART_NAME)!=null){
			cart=(Map<Integer, Resfood>) session.getAttribute(YcConstant.CART_NAME);
		}
		JsonModel jm=new JsonModel();
		try {
			if(cart.containsKey(resfood.getFid())){
				cart.remove(resfood.getFid());
			}else{
			}
			session.setAttribute(YcConstant.CART_NAME,cart);
			jm.setCode(1);
			jm.setObj(cart);
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setErrorMsg(e.toString());
		}
		super.outJson(jm,response);
	}

	private void orderOp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Resfood resfood= (Resfood) super.parseRequest(request,Resfood.class);//取出 fid
		//num 怎么办   两种方法
		//方法一  ，在response 中增加一个属性，  num    在上面的parseRequest 来解决参数
		//方法二   使用原生   request.getParameter（“num”）；
		HttpSession session=request.getSession();
		//判断session中的map是否已经存在
		Map<Integer,Resfood> cart=new HashMap<Integer,Resfood>();
		
		if(session.getAttribute( YcConstant.CART_NAME)!=null){
			cart=(Map<Integer, Resfood>) session.getAttribute(YcConstant.CART_NAME);
		}
		//不存在，说明这是这个用户第一次下单
		//      创建一个map  ，作为 购物车 存到 session中
		//如果存在， 则从session中取出这个map
		//处理数量
		JsonModel jm=new JsonModel();
		try {
			if(cart.containsKey(resfood.getFid())){
				//如果存在， 则数量要相加
				Resfood rfold=cart.get(resfood.getFid());
				
				if(resfood.getNum()+rfold.getNum()  <=0){
					cart.remove(resfood.getFid());
				}else{
					rfold.setNum(resfood.getNum()+rfold.getNum());//更新数量
					cart.put(resfood.getFid(), rfold);
				}
			}else{
				//如果不存在 ，则数量为1
				ResfoodBiz rb=new ResfoodBizImpl();
				Resfood rf=rb.getResfoodByFid(resfood.getFid());
				//数量
				rf.setNum(resfood.getNum());
				cart.put(resfood.getFid(), rf);
			}
			session.setAttribute(YcConstant.CART_NAME,cart);
			jm.setCode(1);
			jm.setObj(cart);
		} catch (Exception e) {
			e.printStackTrace();
			jm.setCode(0);
			jm.setErrorMsg(e.toString());
		}
		super.outJson(jm,response);
	}
}
