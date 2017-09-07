package com.yc.util.redisutil.redisUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisUtil<T> {
	public void saveToHash(Jedis jedis,String keyPrefix,String id,List<T> list,Class<T> c) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String getIdMethodName="get"+id.substring(0,1).toUpperCase()+id.substring(1);
		//取出所有的get方法
		Set<Method> methodGet=getMethod(c);
		for(T rf:list){
			//取出id
			String itemid=keyPrefix+":";
			//取getFid() 方法值  -> 为取编号做准备
			for(Method m:methodGet){
				if(m.getName().equals(getIdMethodName)){
					itemid=itemid+m.invoke(rf).toString();
					break;
				}
			}
			for(Method m:methodGet){
				String fieldName=m.getName().substring(3, 4).toLowerCase()+m.getName().substring(4);
				Object value=m.invoke(rf);
				if(value!=null){
					jedis.hset(itemid, fieldName, value.toString());
				}
			}
		}
	}
	public List<T> getFromHash(Jedis jedis,String keyPrefix,String id,Class<T> c) {
		List<T> list=new ArrayList<T>();
		//先取出所有的键
		String keysPattern=keyPrefix;//"allfoor:*"
		Set<String> keyset=jedis.keys(keysPattern); //查出所有的“allfood:*”键
		Iterator<String> its=keyset.iterator();
		T t=null;
		while(its.hasNext()){
			String key=its.next();  //取出每个键 -> "allfood:1"    "allfood:2"
			
			Map<String,String> map=jedis.hgetAll(key);//根据键取出hash   ->Map
			//   fname  xxxx
			//   price 222

			t=parseMapToT( map,c);
			list.add(t);
		}

		return list;
	}

	private T parseMapToT(Map<String, String> map, Class<T> c) {
		//1.获取所有的set方法名
		Set<Method> setMethod=getSetMethod(c);  //setPname,
		//setPrice
		T t=null;
		try{
			t= c.newInstance(); //创建反射类的实例化对象 Product
			//循环所有的方法，查到与 method Names中相同的方法
			for( Method method:setMethod){
				//循环map中所有的键，拼接上set后与method.getName相等比较
				Set<String> keys=map.keySet();
				for(String key:keys){
					String methodName="set"+key.substring(0,1).toUpperCase()+key.substring(1);
					if(method.getName().equals(methodName)){
						//激活这个method      invoke
						String typeName=method.getParameterTypes()[0].getName();
						//TODO : 类型 问题
						//System.out.println(method.getParameterTypes()[0]);
						//Class      method.getParameterTypes()[0]
						String value=map.get(key);
						if(value!=null && !"".equals(value)){
							if("java.lang.Integer".equals(typeName) || "int".equals(typeName)){
								method.invoke(t, Integer.parseInt(value));
							}else if("java.lang.Double".equals(typeName) || "double".equals(typeName)){
								method.invoke(t, Double.parseDouble(value));
							}else if("java.lang.Long".equals(typeName) || "long".equals(typeName)){
								method.invoke(t, Long.parseLong(value));
							}else if("java.lang.Float".equals(typeName) || "float".equals(typeName)){
								method.invoke(t, Float.parseFloat(value));
							}else {
								method.invoke(t,value);
							}
						}
						break;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return t;
	}
	
	
	private Set<Method> getSetMethod(Class c) {
		Method[] ms=c.getMethods();
		Set<Method> result=new HashSet<Method>();
		for(Method m:ms){
			if(m.getName().startsWith("set")){
				result.add(m);
			}
		}
		return result;
	}

	private Set<Method> getMethod(Class c) {
		Method[] ms=c.getMethods();
		Set<Method> result=new HashSet<Method>();
		for(Method m:ms){
			if(m.getName().startsWith("get")){
				result.add(m);
			}
		}
		return result;
	}
}
