<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="com.yc.utils.YcConstant,redis.clients.jedis.Jedis,java.util.*,java.text.*,com.yc.utils.redisutil.fn2.rank.RankUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
		int topn = 3;
		Jedis jedis = new Jedis(YcConstant.REDIS_URL);
		Set<String> set = RankUtil.getTopN(jedis, topn);
	%>

	前<%=topn%>名为：<br />
	<%
		for (String uid : set) {
	%>
	<%=uid%><br />

	<%
		}
	%>



</body>
</html>