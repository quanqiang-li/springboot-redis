CREATE TABLE `red_packet_record` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`amount` INT (11) NOT NULL DEFAULT '0' COMMENT '抢到红包的金额',
	`nick_name` VARCHAR (32) NOT NULL DEFAULT '0' COMMENT '抢到红包的用户的用户名',
	`img_url` VARCHAR (255) NOT NULL DEFAULT '0' COMMENT '抢到红包的用户的头像',
	`uid` INT (20) NOT NULL DEFAULT '0' COMMENT '抢到红包用户的用户标识',
	`red_packet_id` BIGINT (11) NOT NULL DEFAULT '0' COMMENT '红包id，采用timestamp+5位随机数',
	`create_time` TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
)  COMMENT = '抢红包记录表，抢一个红包插入一条记录';