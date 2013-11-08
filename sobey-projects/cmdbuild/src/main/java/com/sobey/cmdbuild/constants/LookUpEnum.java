package com.sobey.cmdbuild.constants;

/**
 * CMDBuild数据库中 LookUp的自定义类型.
 * 
 * 需手动保持和表LookUp的type字段同步.
 * 
 * @author Administrator
 * 
 */
public enum LookUpEnum {

	/**
	 * 品牌
	 */
	Brand,

	/**
	 * 订单状态
	 */
	ConsumptionsStatus,

	/**
	 * 设备类型
	 */
	DeviceType,

	/**
	 * 磁盘类型
	 */
	DiskType,

	/**
	 * 域名类型
	 */
	DomainType,

	/**
	 * 虚拟机Agent
	 */
	ECSAgent,

	/**
	 * 虚拟机状态
	 */
	ECSStatus,

	/**
	 * EIP状态
	 */
	EIPStatus,

	/**
	 * ESG协议
	 */
	ESGProtocol,

	/**
	 * 内存频率
	 */
	Frequency,

	/**
	 * 机柜规格
	 */
	Height,

	/**
	 * 镜像
	 */
	Image,

	/**
	 * IOPS值
	 */
	IOPS,

	/**
	 * IP池类型
	 */
	IPAddressPool,

	/**
	 * ISP厂商
	 */
	ISP,

	/**
	 * 维护策略
	 */
	Maintenance,

	/**
	 * 网卡速率
	 */
	NICRate,

	/**
	 * 设备功耗
	 */
	Power,

	/**
	 * 协议
	 */
	Protocol,

	/**
	 * 磁盘转速
	 */
	RotationalSpeed,

	/**
	 * 服务类型
	 */
	ServiceType,

	/**
	 * 卷类型
	 */
	VolumeType

}
