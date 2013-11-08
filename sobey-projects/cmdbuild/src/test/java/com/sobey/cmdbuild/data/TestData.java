package com.sobey.cmdbuild.data;

import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.entity.Idc;
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.test.data.RandomData;

public class TestData {

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
		tenants.setAccontBalance(10.0);
		tenants.setCompany(85);
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
		tag.setTenants(87);
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
		rack.setIdc(110);
		return rack;
	}

}
