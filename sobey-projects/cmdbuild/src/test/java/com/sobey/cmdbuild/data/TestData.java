package com.sobey.cmdbuild.data;

import java.util.Date;

import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.entity.Consumptions;
import com.sobey.cmdbuild.entity.DeviceSpec;
import com.sobey.cmdbuild.entity.EcsSpec;
import com.sobey.cmdbuild.entity.EipSpec;
import com.sobey.cmdbuild.entity.Es3Spec;
import com.sobey.cmdbuild.entity.Fimas;
import com.sobey.cmdbuild.entity.FimasBox;
import com.sobey.cmdbuild.entity.FimasPort;
import com.sobey.cmdbuild.entity.Firewall;
import com.sobey.cmdbuild.entity.FirewallPort;
import com.sobey.cmdbuild.entity.HardDisk;
import com.sobey.cmdbuild.entity.Idc;
import com.sobey.cmdbuild.entity.Ipaddress;
import com.sobey.cmdbuild.entity.LoadBalancer;
import com.sobey.cmdbuild.entity.LoadBalancerPort;
import com.sobey.cmdbuild.entity.Memory;
import com.sobey.cmdbuild.entity.NetappBox;
import com.sobey.cmdbuild.entity.NetappController;
import com.sobey.cmdbuild.entity.NetappPort;
import com.sobey.cmdbuild.entity.Nic;
import com.sobey.cmdbuild.entity.NicPort;
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.cmdbuild.entity.Server;
import com.sobey.cmdbuild.entity.ServerPort;
import com.sobey.cmdbuild.entity.SwitchPort;
import com.sobey.cmdbuild.entity.Switches;
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.cmdbuild.entity.Vlan;
import com.sobey.test.data.RandomData;

public class TestData {

	private static Date startDate = new Date(System.currentTimeMillis());
	private static Date endDate = new Date(System.currentTimeMillis() + (60 * 60 * 24 * 7 * 1000));

	public static Company randomCompany() {
		Company company = new Company();
		company.setId(0);
		company.setCode(RandomData.randomName("code"));
		company.setDescription(RandomData.randomName("description"));

		company.setPhone(RandomData.randomName("phone"));
		company.setAddress(RandomData.randomName("address"));
		company.setZip(RandomData.randomName("zip"));
		company.setRemark(RandomData.randomName("remark"));
		return company;
	}

	public static Tenants randomTenants() {
		Tenants tenants = new Tenants();
		tenants.setId(0);
		tenants.setCode(RandomData.randomName("code"));
		tenants.setDescription(RandomData.randomName("description"));

		tenants.setPhone(RandomData.randomName("phone"));
		tenants.setRemark(RandomData.randomName("remark"));
		tenants.setAccontBalance(RandomData.randomDouble());
		tenants.setCompany(86);
		tenants.setPassword(RandomData.randomName("password"));
		tenants.setEmail(RandomData.randomName("email"));
		return tenants;
	}

	public static Tag randomTag() {
		Tag tag = new Tag();
		tag.setId(0);
		tag.setCode(RandomData.randomName("code"));
		tag.setDescription(RandomData.randomName("description"));

		tag.setRemark(RandomData.randomName("remark"));
		tag.setTenants(217);
		return tag;
	}

	public static Idc randomIdc() {
		Idc idc = new Idc();
		idc.setId(0);
		idc.setCode(RandomData.randomName("code"));
		idc.setDescription(RandomData.randomName("description"));

		idc.setRemark(RandomData.randomName("remark"));
		idc.setCity(RandomData.randomName("city"));
		idc.setZip(RandomData.randomName("zip"));
		idc.setAddress(RandomData.randomName("address"));
		idc.setPhone(RandomData.randomName("phone"));
		return idc;
	}

	public static Rack randomRack() {
		Rack rack = new Rack();
		rack.setId(0);
		rack.setCode(RandomData.randomName("code"));
		rack.setDescription(RandomData.randomName("description"));

		rack.setRemark(RandomData.randomName("remark"));
		rack.setIdc(129);
		return rack;
	}

	public static Consumptions randomConsumptions() {
		Consumptions con = new Consumptions();

		con.setId(0);
		con.setNotes(RandomData.randomName("note"));
		con.setCode(RandomData.randomName("code10"));
		con.setConsumptionsStatus(69);// 执行中

		con.setServiceStart(startDate);
		con.setServiceEnd(endDate);
		con.setIdentifier(RandomData.randomName("fuwuid"));
		con.setTenants(87);

		con.setDescription(RandomData.randomName("description"));
		con.setRemark(RandomData.randomName("remark"));
		con.setSpending(RandomData.randomDouble());

		return con;
	}

	public static DeviceSpec randomDeviceSpec() {
		DeviceSpec dev = new DeviceSpec();

		dev.setId(0);
		dev.setDeviceType(0);
		dev.setBeginDate(startDate);
		dev.setCode(RandomData.randomName("code10"));
		dev.setDescription(RandomData.randomName("description"));

		dev.setBrand(0);
		dev.setHeight(0);
		dev.setMaintenance(0);
		dev.setCpuModel(RandomData.randomName("i"));
		dev.setHdNumber(RandomData.randomInt());
		dev.setRamNumber(RandomData.randomInt());
		dev.setPower(RandomData.randomInt());
		dev.setPrice(RandomData.randomDouble());
		dev.setNicNumber(RandomData.randomInt());
		dev.setRemark(RandomData.randomName("remark"));

		return dev;
	}

	public static EcsSpec randomEcsSpec() {
		EcsSpec esc = new EcsSpec();

		esc.setId(0);
		esc.setNotes(RandomData.randomName("note"));
		esc.setCode(RandomData.randomName("code10"));
		esc.setDescription(RandomData.randomName("description"));
		esc.setBeginDate(startDate);
		esc.setCpuNumber(RandomData.randomInt());
		esc.setDiskSize(RandomData.randomInt());
		esc.setMemory(RandomData.randomInt());
		esc.setPrice(RandomData.randomDouble());

		esc.setRemark(RandomData.randomName("remark"));
		esc.setBrand(0);

		return esc;
	}

	public static EipSpec randomEipSpec() {
		EipSpec eip = new EipSpec();

		eip.setId(0);
		eip.setBeginDate(startDate);
		eip.setCode(RandomData.randomName("code10"));
		eip.setDescription(RandomData.randomName("description"));
		eip.setPrice(RandomData.randomDouble());
		
		eip.setRemark(RandomData.randomName("remark"));
		eip.setBrand(0);

		return eip;
	}

	public static Es3Spec randomEs3Spec() {
		Es3Spec es3 = new Es3Spec();

		es3.setId(0);
		es3.setBrand(0);
		es3.setCode(RandomData.randomName("code10"));
		es3.setDescription(RandomData.randomName("description"));

		es3.setMaxSpace(RandomData.randomInt());
		es3.setPrice(RandomData.randomDouble());
		es3.setRemark(RandomData.randomName("remark"));
		es3.setBrand(0);
		es3.setBeginDate(startDate);

		return es3;
	}

	public static Fimas randomFimas() {

		Fimas fimas = new Fimas();

		fimas.setId(0);
		fimas.setCode(RandomData.randomName("code"));
		fimas.setDescription(RandomData.randomName("description"));

		return fimas;
	}

	public static FimasBox randomFimasBox() {

		FimasBox fimasBox = new FimasBox();
		fimasBox.setId(0);
		fimasBox.setCode(RandomData.randomName("code"));
		fimasBox.setDescription(RandomData.randomName("description"));
		fimasBox.setBeginDate(startDate);
		fimasBox.setIdc(0);
		fimasBox.setRack(0);
		fimasBox.setDeviceSpec(0);
		fimasBox.setDiskType(0);
		fimasBox.setDiskNumber(RandomData.randomInt());
		fimasBox.setSite(0);

		// 非必须参数
		fimasBox.setIpaddress(0);
		fimasBox.setSn(RandomData.randomName("sn"));
		fimasBox.setGdzcSn(RandomData.randomName("gdzcSn"));
		fimasBox.setRemark(RandomData.randomName("remark"));

		return fimasBox;
	}

	public static FimasPort randomFimasPort() {
		FimasPort fimasPort = new FimasPort();
		fimasPort.setId(0);
		fimasPort.setCode(RandomData.randomName("code"));
		fimasPort.setDescription(RandomData.randomName("description"));
		return fimasPort;
	}

	public static Firewall randomFirewall() {
		Firewall firewall = new Firewall();
		firewall.setId(0);
		firewall.setCode(RandomData.randomName("code"));
		firewall.setDescription(RandomData.randomName("description"));
		return firewall;
	}

	public static FirewallPort randomFirewallPort() {
		FirewallPort firewallPort = new FirewallPort();
		firewallPort.setId(0);
		firewallPort.setCode(RandomData.randomName("code"));
		firewallPort.setDescription(RandomData.randomName("description"));
		return firewallPort;
	}

	public static HardDisk randomHardDisk() {

		HardDisk hardDisk = new HardDisk();

		hardDisk.setId(0);
		hardDisk.setCode(RandomData.randomName("code"));
		hardDisk.setDescription(RandomData.randomName("description"));
		hardDisk.setBeginDate(startDate);
		hardDisk.setIdc(86);
		hardDisk.setHardDiskSize(1024);

		// 非必须参数
		// hardDisk.setServer(0);

		return hardDisk;
	}

	public static Ipaddress randomIpaddress() {
		Ipaddress ipaddress = new Ipaddress();
		ipaddress.setId(0);
		ipaddress.setCode(RandomData.randomName("code"));
		ipaddress.setDescription(RandomData.randomName("description"));
		return ipaddress;
	}

	public static LoadBalancer randomLoadBalancer() {
		LoadBalancer loadBalancer = new LoadBalancer();
		loadBalancer.setId(0);
		loadBalancer.setCode(RandomData.randomName("code"));
		loadBalancer.setDescription(RandomData.randomName("description"));
		return loadBalancer;
	}

	public static LoadBalancerPort randomLoadBalancerPort() {
		LoadBalancerPort loadBalancerPort = new LoadBalancerPort();
		loadBalancerPort.setId(0);
		loadBalancerPort.setCode(RandomData.randomName("code"));
		loadBalancerPort.setDescription(RandomData.randomName("description"));
		return loadBalancerPort;
	}

	public static Memory randomMemory() {

		Memory memory = new Memory();

		memory.setId(0);
		memory.setCode(RandomData.randomName("code"));
		memory.setDescription(RandomData.randomName("description"));
		memory.setBeginDate(startDate);
		memory.setIdc(0);
		memory.setRam(4);

		// 非必须参数
		memory.setBrand(0);
		memory.setFimas(0);
		memory.setFrequency(0);
		memory.setNotes(RandomData.randomName("notes"));
		memory.setServer(0);

		return memory;
	}

	public static NetappBox randomNetappBox() {

		NetappBox netappBox = new NetappBox();
		netappBox.setId(0);
		netappBox.setCode(RandomData.randomName("code"));
		netappBox.setDescription(RandomData.randomName("description"));
		netappBox.setBeginDate(startDate);
		netappBox.setIdc(0);
		netappBox.setRack(0);
		netappBox.setDeviceSpec(0);
		netappBox.setDiskNumber(3);
		netappBox.setDiskType(0);

		// 非必须参数
		netappBox.setGdzcSn(RandomData.randomName("gdzcSn"));
		netappBox.setRemark(RandomData.randomName("remark"));
		netappBox.setSn(RandomData.randomName("sn"));
		netappBox.setIpaddress(0);

		return netappBox;
	}

	public static NetappController randomNetappController() {
		NetappController netappController = new NetappController();
		netappController.setId(0);
		netappController.setCode(RandomData.randomName("code"));
		netappController.setDescription(RandomData.randomName("description"));
		return netappController;
	}

	public static NetappPort randomNetappPort() {

		NetappPort netappPort = new NetappPort();

		netappPort.setId(0);
		netappPort.setCode(RandomData.randomName("code"));
		netappPort.setDescription(RandomData.randomName("description"));
		netappPort.setBeginDate(startDate);

		return netappPort;
	}

	public static Nic randomNic() {

		Nic nic = new Nic();

		nic.setId(0);
		nic.setCode(RandomData.randomName("code"));
		nic.setDescription(RandomData.randomName("description"));
		nic.setBeginDate(startDate);
		nic.setPortNumber(3);
		nic.setIdc(0);

		nic.setBrand(0);

		return nic;
	}

	public static NicPort randomNicPort() {

		NicPort nicPort = new NicPort();

		nicPort.setId(0);
		nicPort.setCode(RandomData.randomName("code"));
		nicPort.setDescription(RandomData.randomName("description"));
		nicPort.setSite(RandomData.randomName("位置"));
		nicPort.setBeginDate(startDate);
		nicPort.setNic(0);

		nicPort.setMacAddress(RandomData.randomName("MAC地址"));

		return nicPort;
	}

	public static Server randomServer() {
		Server server = new Server();

		server.setId(0);
		server.setCode(RandomData.randomName("code"));
		server.setDescription(RandomData.randomName("description"));

		server.setGdzcSn(RandomData.randomName("gdzcSn"));
		server.setIdc(129);
		server.setRack(224);
		server.setSn(RandomData.randomName("sn"));

		return server;
	}

	public static ServerPort randomServerPort() {
		ServerPort serverPort = new ServerPort();
		serverPort.setId(0);
		serverPort.setCode(RandomData.randomName("code"));
		serverPort.setDescription(RandomData.randomName("description"));
		return serverPort;
	}

	public static Switches randomSwitches() {
		Switches switches = new Switches();
		switches.setId(0);
		switches.setCode(RandomData.randomName("code"));
		switches.setDescription(RandomData.randomName("description"));
		return switches;
	}

	public static SwitchPort randomSwitchPort() {
		SwitchPort switchPort = new SwitchPort();
		switchPort.setId(0);
		switchPort.setCode(RandomData.randomName("code"));
		switchPort.setDescription(RandomData.randomName("description"));
		return switchPort;
	}

	public static Vlan randomVlan() {

		Vlan vlan = new Vlan();

		vlan.setId(0);
		vlan.setCode(RandomData.randomName("code"));
		vlan.setDescription(RandomData.randomName("description"));
		vlan.setBeginDate(startDate);
		vlan.setTenants(0);

		vlan.setRemark(RandomData.randomName("remark"));

		return vlan;
	}

}
