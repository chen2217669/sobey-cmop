package com.sobey.cmdbuild.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.service.organisation.CompanyService;
import com.sobey.test.spring.SpringTransactionalTestCase;

/**
 * CompanyService 的测试用例,测试sevice层的业务逻辑
 * 
 * @author Administrator
 * 
 */
@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
public class CompanyServiceTest extends SpringTransactionalTestCase {

	@Autowired
	private CompanyService service;

	@Test
	public void saveCompany() {
		Company company = TestData.randomCompany();
		service.saveOrUpdate(company);
		assertNotNull(company.getCode());
	}

	@Test
	public void getCompanies() {
		List<Company> list = service.getCompanies();
		System.out.println("列表数据数量:" + list.size());
		assertNotNull(list);
	}

}
