package com.sobey.cmdbuild.data;

import java.util.Date;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.entity.Company;
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
		company.setUser("admin");// 无法插入postgresql,估计和其数据类型有关系,太TMD坑爹了.
		company.setBeginDate(new Date());
		company.setIdClass(Company.class.getSimpleName());
		company.setStatus(CMDBuildConstants.STATUS_ACTIVE);
		return company;
	}

	public static Tenants randomTenants() {
		Tenants tenants = new Tenants();
		tenants.setId(0);
		tenants.setCode(RandomData.randomName("code"));
		tenants.setDescription(RandomData.randomName("description"));
		tenants.setPhone(RandomData.randomName("phone"));
		tenants.setRemark(RandomData.randomName("remark"));
		tenants.setUser("admin");// 无法插入postgresql,估计和其数据类型有关系,太TMD坑爹了.
		tenants.setBeginDate(new Date());
		tenants.setIdClass(Tenants.class.getSimpleName());
		tenants.setStatus(CMDBuildConstants.STATUS_ACTIVE);
		tenants.setAccontBalance(10.0);
		tenants.setCompany(85);
		tenants.setPassword(RandomData.randomName("password"));
		tenants.setEmail(RandomData.randomName("email"));
		return tenants;
	}

}
