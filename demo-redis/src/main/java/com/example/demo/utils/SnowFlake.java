package com.example.demo.utils;

import java.util.Calendar;

/**
 * 雪花算法,从高到低,1位符号+41位时间戳+5位数据标识+5位机器标识+12位自增序列号 0
 * 00000001100111100011011101110111100101110 00000 00000 000000000101
 * 
 * @author 2476056494@qq.com
 *
 */
public class SnowFlake {

	/**
	 * 起始的时间戳:这个时间戳自己随意获取，比如自己代码的时间戳,这里是2018-12-04 14:05:01
	 */
	private final static long START_STMP = 1543903501000L;

	/**
	 * 每一部分占用的位数
	 */
	private final static long SEQUENCE_BIT = 12; // 序列号占用的位数
	private final static long MACHINE_BIT = 5; // 机器标识占用的位数
	private final static long DATACENTER_BIT = 5;// 数据中心占用的位数
	private final static long STMP_BIT = 41; // 时间戳占用的位数

	/**
	 * 每一部分的最大值：先进行左移运算，再同-1进行异或运算；异或：相同位置相同结果为0，不同结果为1
	 */
	/** 用位运算计算出12位能存储的最大正整数：4095,-1先左移,然后再和-1异或,高位全变成了0,低位全变成了1,即这里为2^12次幂 */
	private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

	/**
	 * 每一部分向左的位移
	 */

	/** 机器标志较序列号的偏移量 ,即机器标识位的起始位置,左侧开始计数0,这里就是第12位 */
	private final static long MACHINE_LEFT = SEQUENCE_BIT;

	/** 数据中心较机器标志的偏移量 ,即数据中心标识位的起始位置,左侧开始计数0,这里就是第12位序列号+5位机器 */
	private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;

	/** 时间戳较数据中心的偏移量 ,即时间戳的起始位置,左侧开始计数0,这里就是第12位序列号+5位机器+5位数据中心 */
	private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

	private static long datacenterId; // 数据中心,标识业务,0标识订单,1标识支付,2标识....,可以单独写每个业务自己的SnowFlake.class
	private static long machineId; // 机器标识,集群部署时,可以标识不同的机器
	private static long sequence = 0L; // 序列号
	private static long lastStmp = -1L;// 上一次时间戳

	/**
	 * 此处无参构造私有，同时没有给出有参构造，在于避免以下两点问题：
	 * 1、私有化避免了通过new的方式进行调用，主要是解决了在for循环中通过new的方式调用产生的id不一定唯一问题问题，因为用于
	 * 记录上一次时间戳的lastStmp永远无法得到比对； 2、没有给出有参构造在第一点的基础上考虑了一套分布式系统产生的唯一序列号应该是基于相同的参数
	 */
	private SnowFlake() {
	}

	/**
	 * 产生下一个ID
	 *
	 * @return
	 */
	public static synchronized long nextId() {
		/** 获取当前时间戳 */
		long currStmp = getNewstmp();
		/** 如果当前时间戳小于上次时间戳则抛出异常 */
		if (currStmp < lastStmp) {
			throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
		}
		/** 相同毫秒内 */
		if (currStmp == lastStmp) {
			// 相同毫秒内，序列号自增
			// 当sequence==MAX_SEQUENCE时,sequence=sequence+1,则sequence高位进1,低位全是0,和MAX_SEQUENCE&,则全是0
			sequence = (sequence + 1) & MAX_SEQUENCE;
			// 同一毫秒的序列数已经达到最大,
			if (sequence == 0L) {

				/** 获取下一时间的时间戳并赋值给当前时间戳 */
				currStmp = getNextMill();
			}
		} else {
			// 不同毫秒内，序列号置为0
			sequence = 0L;
		}
		/** 当前时间戳存档记录，用于下次产生id时对比是否为相同时间戳 */
		lastStmp = currStmp;
		return (currStmp - START_STMP) << TIMESTMP_LEFT // 时间戳部分,左移22位,腾出了右侧12位序列号+5位机器+5位数据中心位置
				| datacenterId << DATACENTER_LEFT // 数据中心部分,左移17位,腾出了右侧12位序列号+5位机器,然后和时间戳部分(|),保持了高位不变,等于拼接上了数据中心位
				| machineId << MACHINE_LEFT // 机器标识部分,左移12位,腾出了右侧12位序列号,然后和前面部分(|),保持了高位不变,等于拼接上了机器标识位
				| sequence; // 序列号部分,和前面部分(|),保持了高位不变,等于拼接上了序列号位
	}

	private static long getNextMill() {
		long mill = getNewstmp();
		while (mill <= lastStmp) {
			mill = getNewstmp();
		}
		return mill;
	}

	private static long getNewstmp() {
		return System.currentTimeMillis();
	}

	public static void main(String[] args) {
		//批量生成100个id
//		for (int i = 0; i < 100; i++) {
//			long nextId = SnowFlake.nextId();
//			System.out.println(nextId);
//			System.out.println(leftZero(nextId) + Long.toBinaryString(nextId));
//		}
//		58295813561909248
//		0 00000001100111100011011101110111100101101 00000 00000 000000000000
//		58295813561909249
//		0 00000001100111100011011101110111100101101 00000 00000 000000000001
		//选择生成的id,反向推导出各个位上的数值
		long id = 58295813566103557L;
		long sequence2 = getSequence(id);
		System.out.println(sequence2);
		System.out.println(leftZero(sequence2) + Long.toBinaryString(sequence2));
		long machine = getMachine(id);
		System.out.println(machine);
		System.out.println(leftZero(machine) + Long.toBinaryString(machine));
		long datacenter = getDatacenter(id);
		System.out.println(datacenter);
		System.out.println(leftZero(datacenter) + Long.toBinaryString(datacenter));
		long stmp = getStmp(id);
		System.out.println(stmp);
		System.out.println(leftZero(stmp) + Long.toBinaryString(stmp));
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(START_STMP + stmp);
		System.out.println(instance.getTime());
	}

	/**
	 * 左边补0
	 * 
	 * @param value
	 * @return
	 */
	private static String leftZero(Long id) {
		StringBuffer sb = new StringBuffer();
		int numberOfLeadingZeros = Long.numberOfLeadingZeros(id);
		// id==0,最多补63个,本身占一位
		numberOfLeadingZeros = numberOfLeadingZeros == 64 ? 63 : numberOfLeadingZeros;
		for (int i = 0; i < numberOfLeadingZeros; i++) {
			sb.append("0");
		}
		return sb.toString();
	}

	/**
	 * 反向推导出序列号
	 * 
	 * @param id
	 * @return
	 */
	private static long getSequence(Long id) {
		// 先把-1无符号右移,得到二进制低位SEQUENCE_BIT个1,高位全是0;最后和id与操作,得出后面SEQUENCE_BIT位的结果
		long sequence = (-1L >>> (1 + STMP_BIT + DATACENTER_BIT + MACHINE_BIT)) & id;
		return sequence;
	}

	/**
	 * 反向推导出机器号
	 * 
	 * @param id
	 * @return
	 */
	private static long getMachine(Long id) {
		// 先把-1无符号右移,得到二进制低位MACHINE_BIT个1,高位全是0;最后id通过右移把MACHINE_BIT放在低位参加与操作,得出后面MACHINE_BIT位的结果
		long machine = (-1L >>> (1 + STMP_BIT + DATACENTER_BIT + SEQUENCE_BIT)) & (id >> SEQUENCE_BIT);
		return machine;
	}

	/**
	 * 反向推导出数据标志位
	 * 
	 * @param id
	 * @return
	 */
	private static long getDatacenter(Long id) {
		// 先把-1无符号右移,得到二进制低位DATACENTER_BIT个1,高位全是0;最后id通过右移把MACHINE_BIT放在低位参加与操作,得出后面DATACENTER_BIT位的结果
		long machine = (-1L >>> (1 + STMP_BIT + MACHINE_BIT + SEQUENCE_BIT)) & (id >> (MACHINE_BIT + SEQUENCE_BIT));
		return machine;
	}

	/**
	 * 反向推导出时间戳
	 * 
	 * @param id
	 * @return
	 */
	private static long getStmp(Long id) {
		// 先把-1无符号右移,得到二进制低位STMP_BIT个1,高位全是0;最后id通过右移把STMP_BIT放在低位参加与操作,得出后面STMP_BIT位的结果
		long stmp = (-1L >>> (1 + DATACENTER_BIT + MACHINE_BIT + SEQUENCE_BIT))
				& (id >> (DATACENTER_BIT + MACHINE_BIT + SEQUENCE_BIT));
		return stmp;
	}

}
