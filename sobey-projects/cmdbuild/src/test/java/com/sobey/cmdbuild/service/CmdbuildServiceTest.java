package com.sobey.cmdbuild.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sobey.cmdbuild.data.CompanyData;
import com.sobey.cmdbuild.entity.Company;
import com.sobey.test.spring.SpringTransactionalTestCase;

/**
 * CmdbuildService的测试用例,测试sevice层的业务逻辑
 * 
 * @author Administrator
 * 
 */
@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
public class CmdbuildServiceTest extends SpringTransactionalTestCase {

	@Autowired
	private CmdbuildService service;

	@Test
	public void getCompany() {
		List<Company> list = service.getCompany();
		System.out.println(list.size());
		assertNotNull(list);
	}

	@Test
	@Ignore
	public void saveCompany() {
		Company company = CompanyData.randomCompany();
		service.saveOrUpdate(company);
		assertNotNull(company.getCode());
	}

}
