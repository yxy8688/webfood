package com.yc.biz.impl;

import java.util.List;

import com.yc.bean.Active;

import com.yc.biz.IActive;
import com.yc.dao.DBHelper;
import com.yc.util.redisutil.redisUtil.RedisUtil;
import com.yc.utils.YcConstant;

import redis.clients.jedis.Jedis;

public class ActiveBizImpl implements IActive {

	private Jedis jedis=new Jedis(YcConstant.REDIS_URL);
	private DBHelper db=new DBHelper();
	@Override
	public List<Active> findAll() throws Exception {
		List<Active> list=null;

		try {
			jedis.connect();
			if(jedis.isConnected()==true  && jedis.keys( YcConstant.ACTIVE+":*").size()>0){
				RedisUtil<Active> ru=new RedisUtil<Active>();
				list=ru.getFromHash(jedis,YcConstant.ACTIVE+":*","active_id",Active.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		if(list==null){

			System.out.println("里面没有数据，重新从数据库查一次");
			list=db.find("select * from active", Active.class);

			RedisUtil<Active> ru=new RedisUtil<Active>();
			ru.saveToHash(jedis,YcConstant.ACTIVE,"active_id",list,Active.class);
		}
		
		return list;
	}


	public static void main(String[] args) throws Exception {
		ActiveBizImpl aBizImpl=new ActiveBizImpl();

		List<Active> list=aBizImpl.findAll();
		for(Active f:list){
			System.out.println(f+"nihao ");
		}
	}
}
