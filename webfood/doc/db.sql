create database webfoods character set utf8;


drop database webfoods;

use webfoods;
drop table resuser;
select * from resadmin;

create table administrator(
	adminid int primary key auto_increment,
	adminname varchar(50),
	adminpwd varchar(50)
)engine=MYISAM character set utf8;

truncate table administrator;

create table active(
	active_id int primary key auto_increment,
	activeLimit numeric(8,2),
	activeReduce numeric(8,2)
	
)engine=MYISAM character set utf8;

truncate table active;

create table resadmin(
	raid int primary key auto_increment,
	raname varchar(50),
	rapwd varchar(50)
)engine=MYISAM character set utf8;

drop table resadmin;
truncate table resadmin;

create table resuser(
	userid int primary key auto_increment,
	username varchar(50),
	pwd varchar(50),
	email varchar(500)
)engine=MYISAM character set utf8;

drop table resuser;
truncate table resuser;
--normprice :原价  realprice : 现价  description : 简介 detail 详细额
select *from(select t.*,rownum rn from
	(select gid,gname,price,pic,g.tid,tname from goods g inner join type t on g.tid=t.tid where g.tid=t.tid order by gid )t
where rownum<=10) where rn>3

select * from resfood limit 10,5;

select * from table where id <= (select id from t order by id desc limit 0,1) order by id limit 3;

//分页查询
select * from resfood where fid<=10 order by fid asc limit 5;

create table resfood(
	fid int primary key auto_increment,
	fname varchar(50),
	normprice numeric(8,2),
	realprice numeric(8,2),
	detail varchar(2000),
	fphoto varchar(1000)
)engine=MYISAM character set utf8;
truncate table resfood;
--订单表： roid：订单号  userid  :外键，下单的用户编号  ordertime ：下单时间  uname :收货人姓名 deliverytype：送货方式 payment ：支付方式

select * from resorder;
create table resorder(
	roid int primary key auto_increment,
	userid int,
	address varchar(500),
	tel varchar(100),
	ordertime date,
	deliverytiem date,
	ps varchar(2000),
	status int
)engine=MYISAM character set utf8;

truncate table resorder;
--订单表的下单人号与用户表中的客户编号有主外键关系
alter table resorder 
	add constraint fk_resorder
		foreign key(userid) references resuser(userid);
		
--dealprice : 成交价  roid:订单号 fid: 商品号  num：数量

		select * from resorderitem;
create table resorderitem(
	roiid int primary key auto_increment,
	roid int,
	fid int,
	dealprice numeric(8,2),
	num int 
)engine=MYISAM character set utf8;

truncate table resorderitem;

alter table resorderitem add constraint fk_resorderitem_roid foreign key(roid) references resorder(roid);

alter table resorderitem add constraint fk_tbl_res_fid foreign key(fid) references resfood(fid);

commit;	







