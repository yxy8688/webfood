package com.yc.biz;

import java.util.Map;

import com.yc.bean.Resfood;
import com.yc.bean.Resorder;

public interface ResorderBiz {
	public void submitOrder(Resorder order,Map<Integer,Resfood> cart) throws Exception;
}
