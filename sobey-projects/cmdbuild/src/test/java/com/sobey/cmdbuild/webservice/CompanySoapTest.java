package com.sobey.cmdbuild.webservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.BaseFunctionalTestCase;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;

/**
 * Company SOAP服务的功能测试, 测试主要的接口调用.
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

	@Test
	@Ignore
	public void find() {

		Integer id = 85;
		DTOResult<CompanyDTO> response = cmdbuildSoapService.findCompany(id);
		assertEquals("code9701", response.getDto().getCode());

		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("EQ_zip", "zip");
		DTOResult<CompanyDTO> responseParams = cmdbuildSoapService.findCompanyByParams(searchParams);
		assertEquals("code9701", responseParams.getDto().getCode());
	}

	@Test
	@Ignore
	public void getList() {
		Map<String, Object> searchParams = Maps.newHashMap();
		DTOListResult<CompanyDTO> result = cmdbuildSoapService.getCompanyList(searchParams);
		System.out.println("获得集合数量:" + result.getDtos().size());
		assertEquals("0", result.getCode());
	}

	@Test
	// @Ignore
	public void save() {
		Company company = TestData.randomCompany();
		CompanyDTO companyDTO = BeanMapper.map(company, CompanyDTO.class);
		IdResult response = cmdbuildSoapService.createCompany(companyDTO);
		assertNotNull(response.getId());
	}

	@Test
	@Ignore
	public void update() {

		Integer id = 86;
		DTOResult<CompanyDTO> response = cmdbuildSoapService.findCompany(id);
		CompanyDTO companyDTO = response.getDto();
		companyDTO.setCode("coder");
		companyDTO.setDescription("我是超人啊~!");
		IdResult result = cmdbuildSoapService.updateCompany(id, companyDTO);
		assertNotNull(result.getId());
	}

	@Test
	// @Ignore
	public void delete() {
		Integer id = 138;
		IdResult response = cmdbuildSoapService.deleteCompany(id);
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

		PaginationResult<CompanyDTO> result = cmdbuildSoapService.getCompanyPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());
		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());
	}

}
