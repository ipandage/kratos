#sequenceid的sql
CREATE TABLE kratos_sequenceid(
	k_id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
	k_type INT NOT NULL COMMENT '类型',
	k_useData BIGINT NOT NULL COMMENT '申请占位数量',
	PRIMARY KEY (k_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;

#不分库分表场景下的读写分离sql
CREATE TABLE userinfo(
	userInfo_id BIGINT NOT NULL COMMENT '主键',
	userName VARCHAR(20) NOT NULL COMMENT '类型',
	PRIMARY KEY (userInfo_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;

#片名连续的库内分片sql
CREATE DATABASE um_0000;
CREATE TABLE userinfo_0000(
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userInfo_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE userinfo_0001(
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userInfo_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0000(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0001(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE DATABASE um_0001;
CREATE TABLE userinfo_0002(
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userInfo_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE userinfo_0003(
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userInfo_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0002(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0003(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;

#非片名连续的库内分片sql
CREATE DATABASE um_0000;
CREATE TABLE userinfo_0000(
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userInfo_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE userinfo_0001(
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userInfo_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0000(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0001(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE DATABASE um_0001;
CREATE TABLE userinfo_0000(
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userInfo_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE userinfo_0001(
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userInfo_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0000(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0001(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;

#片名连续的一库一片sql
CREATE DATABASE um_0000;
CREATE TABLE userinfo_0000(
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userInfo_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0000(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE DATABASE um_0001;
CREATE TABLE userinfo_0001(
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userInfo_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index_0001(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;

#非片名连续的一库一片sql
CREATE DATABASE um_0000;
CREATE TABLE userinfo(
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userInfo_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE DATABASE um_0001;
CREATE TABLE userinfo(
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	userName VARCHAR(20) NOT NULL COMMENT '姓名',
	PRIMARY KEY (userInfo_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
CREATE TABLE email_index(
	email VARCHAR(80) NOT NULL COMMENT '主键',
	email_hash BIGINT NOT NULL COMMENT '主键的hash值/路由条件',
	userInfo_id BIGINT NOT NULL COMMENT '主键/路由条件',
	PRIMARY KEY (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin;
