package com.yc.biz;

import java.lang.reflect.InvocationTargetException;

import com.yc.bean.Resuser;

public interface ResuserBiz {
	
	public Resuser login(Resuser resuser) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException;
}
