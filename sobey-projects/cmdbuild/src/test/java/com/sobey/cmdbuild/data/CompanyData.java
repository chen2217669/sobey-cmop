package com.sobey.cmdbuild.data;

import java.util.Date;

import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.utils.CMDBuildConstants;
import com.sobey.test.data.RandomData;

public class CompanyData {

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

}
