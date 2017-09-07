package com.yc.utils;

/**
 * 这个类专门用来定义系统中所使用的字符串常量
 * @author 袁湘云
 *
 */
public class YcConstant {
	 //redis 中存所有的菜的键  值的类型  ： String，list,set  ,hash*/
	public final static String AllFOOD="allfood";
	//redis 的连接地址
	public final static String REDIS_URL="127.0.0.1";
	//redis 连接端口
	public final static int REDIS_PORT=6379;
	//购物车在session中的键名
	public static final String CART_NAME = "CART";
	// 登录用户在session中的键
	public final static String LOGIN_USER="login_user";
	//redis 中保存在线用户记录的天数
	public final static int KEEPNDAYSFORLINEUSER=7;
	
	public final static String ACTIVE="active";
	
	
}
