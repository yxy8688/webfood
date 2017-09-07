package com.yc.biz.impl;

import com.yc.biz.UserRankBiz;
import com.yc.utils.YcConstant;
import com.yc.utils.redisutil.fn2.rank.RankUtil;

import redis.clients.jedis.Jedis;

/**
 * 业务操作：计算用户的积分，一般在这里加入一个核心的算法
 * @author 袁湘云
 *
 */
public class UserRankBizImpl implements UserRankBiz {

	private Jedis jedis=new Jedis(YcConstant.REDIS_URL);
	@Override
	public void updateScore(double total, int userid) {
		double score=total/100;
		RankUtil.updateScore(jedis, score, userid+"");
	}

}
