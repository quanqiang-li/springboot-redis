CREATE TABLE `red_packet_info` (
	`id` INT (11) NOT NULL AUTO_INCREMENT,
	`red_packet_id` BIGINT (11) NOT NULL DEFAULT 0 COMMENT '红包id，采用timestamp+5位随机数',
	`total_amount` INT (11) NOT NULL DEFAULT 0 COMMENT '红包总金额，单位分',
	`total_packet` INT (11) NOT NULL DEFAULT 0 COMMENT '红包总个数',
	`remaining_amount` INT (11) NOT NULL DEFAULT 0 COMMENT '剩余红包金额，单位分',
	`remaining_packet` INT (11) NOT NULL DEFAULT 0 COMMENT '剩余红包个数',
	`uid` INT (20) NOT NULL DEFAULT 0 COMMENT '新建红包用户的用户标识',
	`create_time` TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	PRIMARY KEY (`id`)
)  COMMENT = '红包信息表，新建一个红包插入一条记录';