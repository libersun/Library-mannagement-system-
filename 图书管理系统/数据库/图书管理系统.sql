CREATE DATABASE `cqutlms` ;
USE `cqutlms`;

DROP TABLE IF EXISTS `books`;

/*创建图书表,主键是图书编号*/
CREATE TABLE `books` (
  `bookId` varchar(20) NOT NULL COMMENT '图书编号',
  `bookName` varchar(20) NOT NULL COMMENT '图书名',
  `author` varchar(20) NOT NULL COMMENT '作者',
  `inventory` int NOT NULL COMMENT '库存',
  PRIMARY KEY (`bookId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


/*插入图书表数据*/
insert  into `books`(`bookId`,`bookName`,`author`,`inventory`) values ('1','老人与海','海明威',139),('2','假如给我三天光明','海伦凯勒',198),('4','c语言程序设计','谭浩强',177),('6','西游记','吴承恩',238),('7','水浒传','施耐庵',10),('8','红楼梦','曹雪芹',50);


/*创建借阅表*/
DROP TABLE IF EXISTS `mybooks`;

CREATE TABLE `mybooks` (
  `bookId` varchar(20) NOT NULL COMMENT '图书编号',
  `bookName` varchar(20) NOT NULL COMMENT '图书名',
  `author` varchar(20) NOT NULL COMMENT '作者',
  `stuId` varchar(20) NOT NULL COMMENT '学生Id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*插入借阅表数据*/
insert  into `mybooks`(`bookId`,`bookName`,`author`,`stuId`) values ('1','老人与海','海明威','20197788'),('4','c语言程序设计','谭浩强','20197788');


/*创建学生表*/
DROP TABLE IF EXISTS `students`;

CREATE TABLE `students` (
  `stuId` varchar(20) NOT NULL COMMENT '学号',
  `college` varchar(20) NOT NULL COMMENT '学院',
  `profession` varchar(20) NOT NULL COMMENT '专业',
  `stuName` varchar(5) NOT NULL COMMENT '学生姓名',
  `startYear` varchar(20) NOT NULL COMMENT '入学年份',
  `gender` varchar(2) NOT NULL COMMENT '性别',
  `password` varchar(20) NOT NULL COMMENT '登录密码',
  PRIMARY KEY (`stuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


/*插入学生表数据*/
insert  into `students`(`stuId`,`college`,`profession`,`stuName`,`startYear`,`gender`,`password`) values ('20181010','会计学院','工商管理','王磊','2018','男','123456'),('20181011','轻工学院','机器人工程','李维','2018','男','123456'),('20181020','计算机学院','电子信息工程','黄大力','2018','男','123456'),('20204455','计算机学院','大数据','王铁柱','2020','男','123456'),('20197788','经济学院','应用统计学','梁朝伟','2019','男','123456'),('20191010','计算机学院','计算机科学与技术','王乐','2019','男','123456');


