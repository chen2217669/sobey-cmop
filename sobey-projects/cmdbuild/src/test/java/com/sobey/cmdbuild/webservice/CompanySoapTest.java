package com.sobey.cmdbuild.webservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.sobey.cmdbuild.BaseFunctionalTestCase;
import com.sobey.cmdbuild.data.CompanyData;
import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.webservice.response.GetCompanyResult;
import com.sobey.cmdbuild.webservice.response.base.IdResult;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.core.mapper.BeanMapper;

/**
 * Cmdbuild SOAP服务的功能测试, 测试主要的接口调用.
 * 
 * 使用在Spring applicaitonContext.xml中用<jaxws:client/>，根据CmdbuildWebService接口创建的Client.
 * 
 * 
 * @author Administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class CompanySoapTest extends BaseFunctionalTestCase {

	@Autowired
	private CmdbuildSoapService service;

	/**
	 * 测试获取用户.
	 */
	@Test
	// @Ignore
	public void getCompany() {
		GetCompanyResult response = service.getCompany(78);
		assertEquals("sobey", response.getCompanyDTO().getCode());
	}

	@Test
	@Ignore
	public void saveCompany() {
		Company company = CompanyData.randomCompany();
		CompanyDTO companyDTO = BeanMapper.map(company, CompanyDTO.class);
		IdResult response = service.createCompany(companyDTO);
		assertNotNull(response.getId());
	}

	@Test
	@Ignore
	public void updateCompany() {

		Integer companyId = 78;

		GetCompanyResult getCompanyResult = service.getCompany(companyId);
		CompanyDTO companyDTO = getCompanyResult.getCompanyDTO();
		companyDTO.setCode("codeliukai333");
		companyDTO.setDescription("刘凯目前单身123!");

		IdResult response = service.updateCompany(companyId, companyDTO);
		assertNotNull(response.getId());
	}

	@Test
	@Ignore
	public void deleteCompany() {
		Integer companyId = 78;
		IdResult response = service.deleteCompany(companyId);
		// assertNotNull(response.getId());
	}

}
