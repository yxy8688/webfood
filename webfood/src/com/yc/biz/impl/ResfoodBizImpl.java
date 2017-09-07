package com.yc.biz.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBiz;
import com.yc.dao.DBHelper;
import com.yc.util.redisutil.redisUtil.RedisUtil;
import com.yc.utils.YcConstant;

import redis.clients.jedis.Jedis;

public class ResfoodBizImpl implements ResfoodBiz {

	private DBHelper db=new DBHelper();

	private Jedis jedis=new Jedis(YcConstant.REDIS_URL);

	RedisUtil<Resfood> ru=new RedisUtil<Resfood>();

	@Override
	public Resfood getResfoodByFid(Integer fid) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Resfood resfood=null;
		List<Resfood> list=null;
		try{
			jedis.connect();
			if(jedis.isConnected()==true  && jedis.keys(YcConstant.AllFOOD+":"+fid).size()>0){
				list=ru.getFromHash(jedis, YcConstant.AllFOOD+":"+ fid, "fid",Resfood.class);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(list==null){
			List<Object> params=new ArrayList<Object>();
			params.add(fid);
			list=db.find("select * from resfood where fid=?",Resfood.class, params);
		}
		if(list!=null && list.size()>0){
			resfood=list.get(0);
		}
		return resfood;
	}


@Override
public List<Resfood> findAll() throws Exception {
	//1.判断jedis 中是否有数据，如果有，则用redis 中的数据
	//2    没有则从数据查一次
	List<Resfood> list=null;
	
		try {
			jedis.connect();
			if(jedis.isConnected()==true  && jedis.keys( YcConstant.AllFOOD+":*").size()>0){
				RedisUtil<Resfood> ru=new RedisUtil<Resfood>();
				list=ru.getFromHash(jedis,YcConstant.AllFOOD+":*","fid",Resfood.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	if(list==null){
		
		System.out.println("里面没有数据，重新从数据库查一次");
		list=db.find("select * from resfood", Resfood.class);
		
		RedisUtil<Resfood> ru=new RedisUtil<Resfood>();
		ru.saveToHash(jedis,YcConstant.AllFOOD,"fid",list,Resfood.class);
	}
	return list;
}

public static void main(String[] args) throws Exception{
	ResfoodBizImpl rbi=new ResfoodBizImpl();
	List<Resfood> list=rbi.findAll();
	for(Resfood f:list){
		System.out.println(f);
	}
	//Resfood rf=rbi.getResfoodByFid(1);
//	System.out.println(rf);
	
}


}
