<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="head">
	小萌神订餐网
	<div class="left">
		<img id="action" src="image/tubiao/active1.png" alt="活动"
			title="每日精彩活动" />
		<marquee direction="right">
			<span id="actionContent"> </span>
		</marquee>
	</div>
	<div class="right">
		<span id="showlogin"><a>登录</a></span> <span id="exitspan"
			style="display: none;"></span>
	</div>
</div>
<div class="mubu" id="mubu"></div>
<div class="login" id="login">
	<span id="unshow">X</span>
	<form name="myform" id="myform">
		<input type="hidden" name="op" id="op" value="login">
		<table>
			<tr id="pp">
				<td class="labname"><label for="username">用户名:</label></td>
				<td colspan="2"><input name="username" type="text"
					placeholder="请输入用户名" id="username" value="a" /></td>
			</tr>
			<tr id="pp2">
				<td class="labname"><label for="pwd">密码:</label></td>
				<td colspan="2"><input type="password" id="pwd"
					placeholder="请输入密码" name="pwd" value="a" /></td>
			</tr>
			<tr id="pp3">
				<td class="labname"><label for="">验证码:</label></td>
				<td><input type="text" class="yzm" name="valcode" id="yzm"
					placeholder="请输入验证码" /></td>
				<td><img src="" id="yzm_img"></td>
			</tr>
		</table>
	</form>
	<input type="button" value="login" class="btn" id="btn">
</div>

<div class="login" id="myinfo">
	<span id="unshowinfo">X</span>
	<form name="forminfo" id="orderform">
		<input type="hidden" name="op" value="confirmOrder">
		<table>
			<tr>
				<td class="labname"><label for="address">送货地址:</label></td>
				<td><input name="address" type="text" placeholder="请输入送货地址"
					id="address" value="湖南工学院" /></td>
			</tr>
			<tr>
				<td class="labname"><label for="tel">联系电话:</label></td>
				<td><input type="text" id="tel" placeholder="请输入联系电话"
					name="tel" value="1389999999" /></td>
			</tr>
			<tr>
				<td class="labname"><label for="deliverytiem">送货时间:</label></td>
				<td><input type="text" name="deliverytiem" id="deliverytiem"
					placeholder="请输入送货时间" value="2016年10月29日 12:00" /></td>
			</tr>
			<tr>
				<td class="labname"><label for="ps">附言:</label></td>
				<td><input type="text" id="ps" placeholder="请输入附言" name="ps"
					value="abc" /></td>
			</tr>
		</table>

	</form>
	<input type="button" value="submit" class="btn" id="submit">
</div>

<div id="content" class="content">
	<ul id="allfoods" class="allfoods">

	</ul>

	<div id="fenye" class="fenye">
		<a id="firstpage">首页</a> <a id="uppage">上一页 </a> <a id="page"> 1 </a>
		<a id="pageSize" style="margin-left: 0px"> </a> <a id="downpage">下一页
		</a> <a id="lastpage">尾页 </a>
	</div>

</div>

<span id="showlook" class="showlook"></span>

<div class="look" id="look">
	<span>浏览记录</span>
	<ul id="ull" class="ull">

	</ul>
</div>

<div id="gou" class="gou">
	<div class="carbag" id="carbag">
		<p>
			购物车<span id="delall">[清空]</span>
		</p>
		<table id="bagcontainer" cellspacing="0" cellpadding="0">

		</table>
	</div>
	<span id="foodcount" class="nofood">购物车是空的</span>
	<div class="car" id="car">
		<p id='pricetext'></p>
	</div>
</div>