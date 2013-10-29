package com.sobey.cmdbuild.data;

import java.util.Date;

import com.sobey.cmdbuild.entity.Company;
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
		company.setUser("admin");

		company.setBeginDate(new Date());
		company.setIdClass(null);

		char status = 'A';
		company.setStatus(status);

		return company;
	}

}
