package com.sobey.cmdbuild.webservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

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
import com.sobey.cmdbuild.webservice.response.base.IdResult;
import com.sobey.cmdbuild.webservice.response.base.PaginationResult;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.cmdbuild.webservice.response.result.CompanyResult;
import com.sobey.cmdbuild.webservice.response.result.plural.CompaniesResult;
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

	@Test
	@Ignore
	public void findCompany() {
		Integer companyId = 78;
		CompanyResult response = service.findCompany(companyId);
		assertEquals("sobey", response.getCompanyDTO().getCode());
	}

	@Test
	@Ignore
	public void getCompanies() {
		CompaniesResult result = service.getCompanies();
		assertEquals("0", result.getCode());
	}

	@Test
	// @Ignore
	public void saveCompany() {
		Company company = CompanyData.randomCompany();
		CompanyDTO companyDTO = BeanMapper.map(company, CompanyDTO.class);
		IdResult response = service.createCompany(companyDTO);
		assertNotNull(response.getId());
	}

	@Test
	@Ignore
	public void updateCompany() {

		Integer companyId = 99;
		CompanyResult getCompanyResult = service.findCompany(companyId);
		CompanyDTO companyDTO = getCompanyResult.getCompanyDTO();
		companyDTO.setCode("codeliukai333");
		companyDTO.setDescription("刘凯目前单身,求一妹子~!");
		IdResult response = service.updateCompany(companyId, companyDTO);
		assertNotNull(response.getId());
	}

	@Test
	@Ignore
	public void deleteCompany() {
		Integer companyId = 78;
		IdResult response = service.deleteCompany(companyId);
		assertNotNull(response.getId());
	}

	@Test
	@Ignore
	public void getPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		/**
		 * TODO 查询有问题.设置 status = 'A' , 但是传递到下个方法后转换成了status = 65 , 存疑. <br>
		 * 将该参数放置在最后一步的分页查询还是OK的.
		 */
		// searchParams.put("EQ_status", CMDBuildConstants.STATUS_ACTIVE);

		PaginationResult<CompanyDTO> result = service.getCompanyPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());
		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());
	}

}
