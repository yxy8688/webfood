package com.yc.utils.redisutil.fn2.rank;

import com.yc.utils.YcConstant;

import redis.clients.jedis.Jedis;

public class Test {

	public static void main(String[] args) {
		Jedis jedis=new Jedis(YcConstant.REDIS_URL);
		RankUtil.updateScore(jedis, 100, "1");
		RankUtil.updateScore(jedis, 10, "13");
		RankUtil.updateScore(jedis, 160, "19");
		RankUtil.updateScore(jedis, 1900, "12");
		
		System.out.println( RankUtil.getTopN(jedis, 2) );
		
		System.out.println( RankUtil.getRank(jedis, "13"));
	}

}
