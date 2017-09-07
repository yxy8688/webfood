/*$(function(){
	$.ajax({
		url:"resfood.action",		
		data:"op=findAll",			//发送到服务器的数据
		type:"POST",                //请求方式
		dataType:"JSON",            //预期服务器返回的数据类型
		success:function(data){       //回调函数
			//data => code obj
			//  =>  code   msg
			if(data.code==0){
				alert("服务器错误，"+data.msg);
			}else{
				showAll(data.obj);
			}
		}
	});

	//加载时  购物车如有物品
	$.ajax({
		url:"resorder.action",
		data:"op=findcart",//{"op":"findcart"}
		type:"POST",
		dataType:"JSON",
		success:function(data){
			//data => code obj
			//  =>  code   msg
			if(data.code==1){
				showbag(data.obj);
			}
		}
	});
	//判断是否登陆
	$.ajax({
		url:"resuser.action",
		data:"op=islogin",
		type:"POST",
		dataType:"JSON",
		success:function(data){
			if(data.code==1){
				haslogined=true;
			}else{
				haslogined=false;
			}
			checklogin( data.obj );
		}
	});*/
var haslogined=false;//用来判断用户是否登陆
var buyfoodidarr=[];//购买的商品的编号
var allfoodsarr;
var count=0;	 //每一的第一个数	
var pagecount=1; //当前页数
var pageAll;    //总页数
var pageItem=6;
var activeLimit;
var avtiveReduce;

$(function(){
	$.ajax({
		url:"index.action",
		type:"POST",
		dataType:"JSON",
		success:function(data){
			if(data.code1==0){
				alert("服务器错误，"+data.msg);
			}else{
				showAll(data.obj1);
			}
			if(data.code2==1){
				showbag(data.obj2);
				//refreshlook(data.obj2);
			}
			if(data.code3==1){
				haslogined=true;
			}else{
				haslogined=false;
			}
			checklogin( data.obj3 );
		}
	});
	//优惠活动
	$.ajax({
		url:"active.action",
		data:"op=showActiveContent",
		type:"POST",
		dataType:"JSON",
		success:function(data){
			if(data.code==1){
				actionContent(data.obj);
			}
		}
	});
	
//	页面一加载完，即清空购物车绑定清空事件
	$("#delall").click(function (){
		if($("#bagcontainer").html()){
			$.ajax({
				url:"resorder.action",
				data:"op=clearall",
				type:"POST",
				dataType:"JSON",
				success:function(data){
					if(data.code==1){
						showbag( data.obj);
					}else{
						alert("修改失败2....");
					}
				}
			});
		}
	});

	$("#page").val(1);
	$("#unshow").click(unshow);    //加了括号代表激活这个方法，而没加括号则表示调用这个方法
	//关闭地址输入框
	$("#unshowinfo").click(unshowinfo);
	//显示登录框
	$("#showlogin").click(showLogin);
	//验证码刷新
	$("#yzm_img").click(function(){
		$(this).attr("src","verifyCode.action?"+new Date().getTime());

	});
	isLoginShow();
	
	$("#action").click(function(){
		$("#actionContent").toggle();
	});
	

	
	$("#showlook").click(showlookdiv);
	//点击购物车时隐藏
	$("#car").click(function(){
		$("#carbag").toggle();
	});
	//下一页
	$("#downpage").click(function(){
		if(pagecount==pageAll){
			pagecount=pageAll;

		}else{
			count+=pageItem;
			pagecount+=1;
			$("#page").html(pagecount);
			$("li").remove();
			showAll(allfoodsarr);
		}
		isLoginShow();
	});
	//上一页
	$("#uppage").click(function(){
		if(pagecount==1){
			pagecount=1;
		}else{
			pagecount-=1;
			count-=pageItem;
			$("#page").html(pagecount);
			$("li").remove();
			showAll(allfoodsarr);
		}
		isLoginShow();
	});
	//首页
	$("#firstpage").click(function(){     
		count=0;
		pagecount=1;
		$("#page").html(pagecount);
		$("li").remove();
		showAll(allfoodsarr);
		isLoginShow();
	});
	//尾页
	$("#lastpage").click(function(){
		count=(pageAll-1)*pageItem;
		pagecount=pageAll;
		$("#page").html(pagecount);
		$("li").remove();
		showAll(allfoodsarr);
		isLoginShow();
	});

	$("#btn").click(function(){
		$.ajax({
			url:"resuser.action",
			//以下两种方案在ajax发出请求时，会自动调用toJsonString()
			//data:{"op":$("#op").val(),"username":$("#username").val(),"pwd":$("#pwd").val(),"valcode":$("#valcode").val()},
			//data:$("#myform").serialize(),       //{op:"login",username:"a",pwd:"a",valcode:'XXX'}
			data:"op="+$("#op").val()+"&username="+$("#username").val()+"&pwd="+$("#pwd").val()+"&valcode="+$("#yzm").val(),
			type:"POST",
			dataType:"JSON",
			success:function(data){

				if(data.code==1){
					//TODO:登陆成功
					unshow();
					haslogined=true;
					alert("登陆成功");
					checklogin(data.obj);
					$("#ull").empty();
					redisshow();
				}else{
					alert("登陆失败，原因："+data.errorMsg);
					haslogined=false;
				}
			}
		});
	});

//	提交订单按钮事件
	$("#submit").click(function(){
		$.ajax({
			url:"resorder.action",
			data:"op=confirmorder&address="+$("#address").val()+"&tel="+$("#tel").val()+"&deliverytiem="+$("#deliverytiem").val()+"&ps="+$("#ps").val(),
			dataType:"JSON",
			type:"POST",
			success:function(data){
				if(data.code==1){
					unshowinfo();
					showbag( data.obj);
					alert("购买成功");
				}else{
					alert("购买失败");
				}
			}
		});
	});

});

//优惠活动
function actionContent(obj){
	activeLimit=obj[0].activeLimit;
	activeReduce=obj[0].activeReduce;
	$("#actionContent").html("每单满"+activeLimit+"减"+activeReduce+"元");
}



//关闭购物地址显示框
function unshowinfo(){
	$("#myinfo").hide();
	$("#mubu").hide();
}

function checklogin(obj){//检测用户登录
	if(haslogined){
		haslogin(obj);

	}else{
		$("#showlogin").show();
		$("#exitspan").hide();
	}
}
//显示登陆用户的信息
function haslogin(obj){
	$("#showlogin").hide();
	$("#exitspan").show().html("欢迎您"+obj.username+",<a href='javascript:void()' onclick='javascript:clickexit()'>退出</a>");
}

//用户退出
function clickexit(obj){
	$.ajax({
		url:"resuser.action?op=logout",
		type:"GET",
		dataType:"JSON",
		success:function(data){
			if(data.code==1){
				$("#showlogin").show();
				$("#exitspan").hide();
				haslogined=false;
				refreshlook();
			}
		}
	});
	return false;
}





function showAll(obj){
	allfoodsarr=obj;
	pageAll=Math.ceil(allfoodsarr.length/pageItem);
	$("#pageSize").html("/"+pageAll);
	var allfoods=$("#allfoods");


	for(var i=count;i<pagecount*pageItem;i++){
		if(i==allfoodsarr.length){
			return;
		}
		var onefood=allfoodsarr[i];  //取出一个菜
		var li=document.createElement("li");

		//插入菜名
		var title=document.createElement("h3");
		title.innerHTML=onefood.fname;    //<h3 id="1">xxx</h3>
		title.id="fid"+onefood.fid;
		li.appendChild(title);


		//插入菜单详情的div
		var fooddesc=document.createElement("div"); //  <div class ="fooddesc" id="fooddesc1"></div>
		yc.addClassName(fooddesc,"fooddesc");
		fooddesc.id="fooddesc"+onefood.fid;
		var foodimg=document.createElement("img"); //<img src="images/foods/50002.jpg">
		foodimg.src="../../pics/"+onefood.fphoto;

		yc.addClassName(foodimg,"foodimg");
		fooddesc.appendChild(foodimg); //<div class="fooddesc" id="fooddesc1"><img src="images/foods/50002.jpg"></div>

		var art=document.createElement("article");
		fooddesc.appendChild(art);

		yc.addClassName(art,"foodprice");

		var detail=document.createElement("p");
		if(onefood.detail){
			detail.innerHTML="菜品描述："+onefood.detail;
		}else{
			detail.innerHTML="菜品描述：无";
		}
		art.appendChild(detail);
		var normprice=document.createElement("p");
		yc.addClassName(normprice,'normprice');
		normprice.innerHTML="原价：￥"+onefood.normprice;
		art.appendChild(normprice);

		var realprice=document.createElement("p");
		yc.addClassName(realprice,'realprice');
		realprice.innerHTML="特价：￥"+onefood.realprice;
		art.appendChild(realprice);

		var buybtn=document.createElement("a");
		buybtn.innerHTML="加入购物车";
		yc.addClassName(buybtn,"buybtn");
		art.appendChild(buybtn);

		fooddesc.style.display="none";
		li.appendChild(fooddesc);

		document.getElementById("allfoods").appendChild(li);

		(function(index,id){
			yc.addEvent(title,"click",function(){
				showdescs(id);
				if(haslogined==false){
					if(Cookies.get(allfoodsarr[index].fid)){
						Cookies.del(allfoodsarr[index].fid);
						Cookies.set(allfoodsarr[index].fid,encodeURI(allfoodsarr[index].fname),60*24);
					}else{
						Cookies.set(allfoodsarr[index].fid,encodeURI(allfoodsarr[index].fname),60*24);
					}
					refreshlook();
				}else{
					$.ajax({
						url:"history.action",
						type:"POST",
						data:"op=saveHistory&data="+allfoodsarr[index].fid+"="+encodeURI(allfoodsarr[index].fname),
						dataType:"JSON",
						success:function(data){
							if(data.code==1){
								var array=data.obj;
								refreshlook2(array);
							}
						}
					});
				}
			});
		})(i,onefood.fid);

		(function(index){

			$(buybtn).click(function(){
				var url="resorder.action?num=1&op=order&fid="+index;
				$.ajax({
					url:url,
					type:"GET",
					dataType:"JSON",
					success:function(data){
						if(data.code==1){
							showbag( data.obj);
						}else{
							alert("下订失败....");
						}
					}
				});
			});

		})(onefood.fid);
	}
}

function redisshow(){
	$.ajax({
		url:"history.action",
		type:"POST",
		data:"op=getHistory",
		dataType:"JSON",
		success:function(data){
			if(data.code==1){
				var array=data.obj;
				refreshlook2(array);
			}
		}
	});
}

function refreshlook2(cookiearr){//显示用户登录后redis中的浏览记录

	yc.$("ull").innerHTML="";
	var flag=0;
	for (var i = 0; i <cookiearr.length ; i++) {
		var lii=document.createElement("li");
		yc.$('ull').appendChild(lii);
		//var matcharr=/([\u0391-\uFFE5]+)=(\d*)/.exec(cookiearr[i]);
		var matcharr=cookiearr[i].split("=");
		lii.innerHTML=decodeURI(matcharr[1]);//下标为1的是名字，下表为0的是编号
		flag++;
		(function(index){  //在http协议中index存的是   空格+index
			yc.addEvent(lii,"click",function(){
				showdescs(parseInt(index));//强制类型转换
				window.scrollTo(0,50*index);
			});
		})(matcharr[0]);

		if(flag>=9){
			break;
		}

	}
}

function refreshlook(){//显示用户浏览记录

	var cookiearr=Cookies.getall();

	yc.$("ull").innerHTML="";
	var flag=0;

	if(!document.cookie) return ;  //是否支持cookie
	for (var i = cookiearr.length-1; i >=0 ; i--) {
		var lii=document.createElement("li");
		yc.$('ull').appendChild(lii);

		//var matcharr=/([\u0391-\uFFE5]+)=(\d*)/.exec(cookiearr[i]);
		var matcharr=cookiearr[i].split("=");
		lii.innerHTML=decodeURI(matcharr[1]);//下标为1的是名字，下表为0的是编号
		flag++;
		(function(index){  //在http协议中index存的是   空格+index
			yc.addEvent(lii,"click",function(){
				showdescs(parseInt(index));//强制类型转换
				window.scrollTo(0,50*index);
			});
		})(matcharr[0]);

		if(flag>=9){
			break;
		}

	}
}
//判断是登陆后的记录显示，还是没登陆的显示
 function isLoginShow(){
	 if(haslogined){
			redisshow();
		}else{

			//显示浏览记录的详情  cookie
			refreshlook();
		}
 }


//显示浏览记录详情
function showlookdiv(){
	if(yc.hasClassName("look","lookblock2")){
		yc.addClassName("look","lookblock3");
		yc.addClassName("showlook","showlookblock3");
		var removestr='yc.removeClassName("look","lookblock1");yc.removeClassName("showlook","showlookblock1");';
		removestr+='yc.removeClassName("look","lookblock2");yc.removeClassName("showlook","showlookblock2");'
			removestr+='yc.removeClassName("look","lookblock3");yc.removeClassName("showlook","showlookblock3");'
				setTimeout(removestr,300);
	}else{
		yc.addClassName("look","lookblock1");
		yc.addClassName("showlook","showlookblock1");
		setTimeout('yc.addClassName("look","lookblock2");yc.addClassName("showlook","showlookblock2");',300);
		// yc.addClassName("look","lookblock2");
		// yc.addClassName("showlook","showlookblock2");
	}
}

//显示购物车数据
//cart 指的是购物车中的商品数据  （“编号”：{resfood}，“编号”：{resfood}）
function showbag( cart){
	var count=0;   //统计总共有多少个商品条目
	for(var key in cart){
		if(cart.hasOwnProperty(key)){
			count++;
		}
	}
	//取出显示购物车信息的table
	var bag=$("#bagcontainer");
	if(count<=0){
		removebuy();
		calprice(cart);
		bag.html("<p>购物车是空的，赶紧选购吧</p>");
		$(".carbag").css({"bottom":"50px"});
		bag.css({"height":"260px"});
		return;
	}
	//TODO:  不为空，则执行
	calprice(cart);
	addbuy();

	bag.html("");
	buyfoodidarr=[];
	var theight=0;
	var str="";
	for(var key in cart){
		if(cart.hasOwnProperty(key)){
			var buyfood=cart[key];
			buyfoodidarr.push(key);
			str+="<tr>";
			str+="<td width='140px'>";
			str+=buyfood.fname;
			str+="</td>";
			str+="<td width='130px' class='editfoodnum'>";
			str+="<span>"+buyfood.num+"</span>";
			str+="<b class='subfoodx' onclick='removefood("+key+")'>X</b>";
			str+="<input type='button' value='+' onclick='editfood(1,"+key+")'/>";
			str+="<input type='button' value='-' onclick='editfood(-1,"+key+")'/>";

			str+="</td>";
			str+="<td width='80px' sytle='color:#F69C30'> ";
			str+="￥"+(buyfood.num*buyfood.realprice);
			str+="</td>";
			str+="</tr>";
			theight++;
		}
	}
	bag.html(str);

	$(".carbag").css({"bottom":"50px"});
	bag.height( theight*40);
}

function removefood(id){
	$.ajax({
		url:"resorder.action?op=delorder&fid="+id,
		type:"GET",
		dataType:"JSON",
		success:function(data){
			if(data.code==1){
				showbag( data.obj);
			}else{
				alert("修改失败1....");
			}
		}
	});
}

//修改数量
function editfood(num,id){
	$.ajax({
		url:"resorder.action?op=order&num="+num+"&fid="+id,
		type:"GET",
		dataType:"JSON",
		success:function(data){

			if(data.code==1){
				showbag( data.obj);
			}else{
				alert("修改失败2....");
			}
		}
	});
}

//修改结算
function addbuy(){
	$("#foodcount").html("去结算&gt;");
	$("#foodcount").addClass("gotobuy");

	$("#foodcount").click(tobuy);
}
//当结算，要判断是否登陆，如果没有登录过，则显示登陆界面
//登陆了，则显示送货单

function tobuy(){
	if(haslogined){
		showinfo();
	}else{
		showLogin();
	}
}
//显示登陆页
function showLogin(){
	$("#yzm_img").attr("src","verifyCode.action?"+new Date().getTime());
	$('#login').show();
	$('#mubu').show();

}
function showinfo(){
	$('#myinfo').show();
	$('#mubu').show();
}
//退出
function unshow() {
	$('#login').hide();
	$('#mubu').hide();
}

function calprice(cart){//计算价格
	var price=0;
	for(var property in cart){
		if(cart.hasOwnProperty(property)){
			var food=cart[property];
			price+=food.realprice*food.num;
			
		}
	}
	if(price>=activeLimit){
		
		price=price-Math.floor(price/activeLimit)*activeReduce;
	}
	
	

	$("#pricetext").html("￥"+price);

}
function removebuy(){
	$("#foodcount").html("购物车是空的");

//	yc.removeClassName("foodcount","gotobuy");
	//yc.removeEvent("foodcount","click",tobuy);
	$("#foodcount").removeClass("gotobuy");
	$("#foodcount").off("click");
}

//初始化页面，把所有的菜品插入到界面并绑定事件

function showdescs(index){//显示菜品的详情
	var allfoods=yc.$("allfoods");
	var titles=allfoods.getElementsByTagName("h3");
	var title=yc.$("fid"+index);
	var descs=allfoods.getElementsByTagName("div");
	var desc=yc.$("fooddesc"+index);
	for(var j=0;j<descs.length;j++){
		if(descs[j]==desc) continue;
		descs[j].style.display="none";

		if(index!=allfoodsarr[allfoodsarr.length-1].fid){
			yc.removeClassName("fid"+allfoodsarr[allfoodsarr.length-1].fid,"noradius");
		}
	}
	yc.toggleDisplay("fooddesc"+index,"block");
	if(index==allfoodsarr[allfoodsarr.length-1].fid){
		if(yc.hasClassName(title,"noradius")){
			yc.removeClassName(title,"noradius");
		}else{
			yc.addClassName(title,"noradius");
		}
	}
}




