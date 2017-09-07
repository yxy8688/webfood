use webfoods;


update resuser set username='b' , email='123' where userid=1;

--活动表中插入数据
insert into active(activeLimit,activeReduce) values('100.0','5.0');
select * from active;

--向管理员表中加入数据
insert into administrator(adminname ,adminpwd) values('a','0cc175b9c0f1b6a831c399e269772661');

select * from resuser where username='a' and pwd='0cc175b9c0f1b6a831c399e269772661';
--用户表初始化数据
insert into resuser(username,pwd ,email) values('a','0cc175b9c0f1b6a831c399e269772661','a@163.com');
insert into resuser(username,pwd ,email) values('b','0cc175b9c0f1b6a831c399e269772661','b@163.com');

--插入菜
select * from resfood;

truncate table resfood;

insert into resfood (fname,normprice,realprice,detail,fphoto) values('素炒莴笋丝','22.0','20.0','营养丰富','500008.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('蛋炒饭','22.0','20.0','营养丰富','500022.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('酸辣鱼','42.0','40.0','营养丰富','500023.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('卤粉','12.0','10.0','营养丰富','500024.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('西红柿蛋汤','12.0','10.0','营养丰富','500025.jpg');

select sum(dealprice) SUM from resorderitem  ritem  inner join resorder rorder on ritem.roid=rorder.roid where userid=2;

insert into resfood (fname,normprice,realprice,detail,fphoto) values('炖鸡','102.0','100.0','营养丰富','500026.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('炒鸡','12.0','10.0','营养丰富','500033.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('炒饭','12.0','10.0','营养丰富','500034.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('手撕前女友','12.0','10.0','营养丰富','500035.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('面条','12.0','10.0','营养丰富','500036.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('端菜','12.0','10.0','营养丰富','500038.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('酸豆角','12.0','10.0','营养丰富','500041.jpg');

select *from resfood;
--不测试：生成一条订单， a用户订了 1号菜一份 以及2号菜2份
insert into resorder(userid,address,tel,ordertime,deliverytime,ps,status) values(1,'湖南省衡阳市'.'13878789999',now(),now(),'送餐上门',0);

insert into resorderitem(roid ,fid,dealprice,num) values(1,1,20,1);

insert into resorderitem(roid ,fid,dealprice,num) values(1,2,20,1);
--注意以上三条语句要求在事物中处理

commit;




create table memberfee(
	memberid int primary key auto_increment,
	year int ,
	shouldPay int,
	truePay int
)engine=MYISAM character set utf8;

truncate table memberfee;

select * from memberfee;

insert into memberfee(year,shouldPay,truePay) values(2013,140,100);
insert into memberfee(year,shouldPay,truePay) values(2014,200,150);
select * from memberfee;

select sum(dealprice) SUM,count(ritem.roiid) count from resorderitem  ritem  inner join resorder rorder on ritem.roid=rorder.roid where userid=1
