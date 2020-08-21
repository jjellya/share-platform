-- version : 0.9.9.2
-- update date :2020/08/21
-- author: 林俊杰

-- 增加了openid,session_key字段
CREATE TABLE `user_info`
(
    `user_id`       int(10) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `open_id`       varchar(128) COMMENT '小程序用户openid,易班用户易班Id',
    `session_key`	varchar(128) COMMENT 'session_key',
    `user_name`     varchar(32) NOT NULL COMMENT '用户昵称或nickname',
    `avatar_url`  	varchar(128)  NOT NULL COMMENT '用户头像' ,
    `user_gender` 	tinyint(1) NULL DEFAULT 0 COMMENT '性别   0 男 1 女 ',
    `user_grade`	int COMMENT '年级',
    `user_star`     int(10)     NOT NULL DEFAULT 0 COMMENT 'Star',
    `doc_num`    	int(10)     NOT NULL DEFAULT 0 COMMENT '资源数量',
    `post_num` 		int(10)     NOT NULL DEFAULT 0 COMMENT '帖子数量',
    `user_mail`     varchar(64) COMMENT 'e-mail',
    PRIMARY KEY (`user_id`)
)  comment '用户表' ;

-- 已删除post_star
-- 更改ID数据类型为INT
-- 增加commentNUM评论数
CREATE TABLE `post_info`
(
    `post_id`      	int(10)  not NULL AUTO_INCREMENT,
    `post_title`	varchar(128) not NULL COMMENT '帖子标题',
    `post_content` 	varchar(256) comment '帖子内容',
    `comment_num`	int(10) comment '评论数',
    `user_id`     int(10) NOT NULL COMMENT '帖主楼主ID',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
	`update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    PRIMARY KEY (`post_id`),
    key `idx_post_title` (`post_title`),
    key `idx_post_tag1` (`post_tag1`),
    key `idx_post_tag2` (`post_tag2`)
) comment '社区帖子详情表' ;

-- 纯碎的标签表
-- 更改ID数据类型为INT
CREATE TABLE `tag_info`
(
    `tag_id`       	int(10)  NOT NULL AUTO_INCREMENT,
    `tag_content`	varchar(128) NOT NULL COMMENT '标签内容',
    PRIMARY KEY (`tag_id`),
    key `idx_tag_content` (`tag_content`)
) comment '标签详情表' ;

-- 新增tag关系表
-- 更改ID数据类型为INT
CREATE TABLE `tag_link`
(
    `tlink_id`      int(10)  NOT NULL AUTO_INCREMENT,
    `tag_id`       	int(10)  NOT NULL,
    `doc_id`       	int(10) COMMENT '文件ID',
    `post_id`       int(10)  COMMENT '帖子ID',
    PRIMARY KEY (`tlink_id`),
    key `idx_tag_id` (`tag_id`)
) comment '标签关系表' ;

-- 更改ID数据类型为INT
CREATE TABLE `doc_info`
(
    `doc_id`       	int(10)  NOT NULL AUTO_INCREMENT,
    `doc_name`		varchar(128)  NOT NULL COMMENT '文件名称',
    `doc_type`		INT NOT NULL COMMENT '文件类型',
    `doc_size`		INT NOT NULL COMMENT '文件大小',
    `doc_path`		varchar(128) COMMENT '文件路径',
    `download_num`	int COMMENT '下载次数',
    `star_num`		int COMMENT 'Star数',
    `user_id`       int(10) NOT NULL COMMENT '上传者ID',
    `upload_time` 	timestamp not null default current_timestamp comment '上传时间',
    PRIMARY KEY (`doc_id`),
    key `idx_doc_name` (`doc_name`)
) comment '文件资源表' ;

-- 更改ID数据类型为INT
CREATE TABLE `comment_info`
(
    `comment_id`	int(10)  NOT NULL AUTO_INCREMENT COMMENT '评论Id',
    `post_id`       int(10)  NOT NULL COMMENT '帖子Id',
    `user_id`       int(10)	 NOT NULL COMMENT '用户openId,易班用户易班Id',
    `doc_id`       	int(10) COMMENT '文件Id',
    `comment_content` varchar(256) COMMENT '评论内容:[评论类型为纯文本时不能为空]',
    `comment_type`	int NOT NULL COMMENT '评论类型：0为纯文本，1为附资源',
    `comment_time` 	timestamp not null default current_timestamp comment '评论时间',
    PRIMARY KEY (`comment_id`),
    key `idx_post_id` (`post_id`)
) comment '评论详情表' ;

-- 更改ID数据类型为INT
CREATE TABLE `link_info`
(
    `link_id`		int(10)  NOT NULL AUTO_INCREMENT COMMENT 'Star关系Id',
    `doc_id`       	int(10)  NOT NULL COMMENT '文件Id',
    `user_id`       int(10) NOT NULL COMMENT '用户openId,易班用户易班Id',
    PRIMARY KEY (`link_id`),
    key `idx_user_id` (`user_id`)
) comment 'Star关系表' ;

-- 更改ID数据类型为INT
CREATE TABLE `ad_info`
(
    `ad_id`			int(10)  NOT NULL AUTO_INCREMENT COMMENT 'advertisement 广告Id',
    `ad_title`		varchar(32)  NOT NULL COMMENT 'advertisement 广告标题',
    `ad_pic`       	varchar(128) NOT NULL COMMENT '广告图片',
    `user_id`       int(10)  NOT NULL COMMENT '广告方',
    `ad_contact`	varchar(64)  NOT NULL COMMENT '联系方式',
    PRIMARY KEY (`ad_id`),
    key `idx_ad_title` (`ad_title`)
) comment '广告表' ;

CREATE TABLE `user_score`
(
    `score_id`		int(10) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `user_id`       int(10) NOT NULL COMMENT 'userId',
    `post_id`       int(10) COMMENT '话题Id',
    `doc_id`     	int(10) COMMENT '文件Id',
    `score_type` 			tinyint(1) NULL DEFAULT 0 COMMENT '类型   0 话题 1 文件',
    `score_value`   int(10)     NOT NULL DEFAULT 0 COMMENT '评分',
    PRIMARY KEY (`score_id`)
)  comment '用户评分向量元素表' ;