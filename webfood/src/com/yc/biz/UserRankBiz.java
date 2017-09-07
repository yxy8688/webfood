package com.yc.biz;

public interface UserRankBiz {
	/**
	 * 计算积分的算法
	 * @param total ：总的购买金额
	 * @param userid
	 */
	public void updateScore(double total,int userid);
}
