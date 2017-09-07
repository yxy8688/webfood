package com.yc.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.bean.Resfood;
import com.yc.dao.DBHelper;

public class Test2 {
	public static void main(String[] args) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		DBHelper db=new DBHelper();
		List<Object> params=new ArrayList<Object>();
		
		//List<Map<String,Object>> list=db.finds("select * from resadmin",params);
		
		/*List<Map<String,Object>> list=db.finds("select * from resadmin");
		
		for(Map<String,Object> map:list){
			System.out.println(map);
		}*/
		
		List< Resfood> listt=db.find("select * from resfood", Resfood.class);
		
		for(Resfood r:listt){
			System.out.println(r);
		}
	}
}
