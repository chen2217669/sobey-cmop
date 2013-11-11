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
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.cmdbuild.webservice.response.dto.TenantsDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;

/**
 * Tenants SOAP服务的功能测试, 测试主要的接口调用.
 * 
 * 使用在Spring applicaitonContext.xml中用<jaxws:client/>，根据CmdbuildWebService接口创建的Client.
 * 
 * 
 * @author Administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class TenantsSoapTest extends BaseFunctionalTestCase {

	@Test
	@Ignore
	public void find() {
		Integer id = 87;
		DTOResult<TenantsDTO> response = cmdbuildSoapService.findTenants(id);
		assertEquals("sobey", response.getDto().getCode());
	}

	@Test
	@Ignore
	public void getList() {
		Map<String, Object> searchParams = Maps.newHashMap();
		DTOListResult<TenantsDTO> result = cmdbuildSoapService.getTenantsList(searchParams);
		assertEquals("0", result.getCode());
	}

	@Test
	// @Ignore
	public void save() {
		Tenants tenants = TestData.randomTenants();
		TenantsDTO TenantsDTO = BeanMapper.map(tenants, TenantsDTO.class);
		IdResult response = cmdbuildSoapService.createTenants(TenantsDTO);
		assertNotNull(response.getId());
	}

	@Test
	@Ignore
	public void update() {
		Integer id = 86;
		DTOResult<TenantsDTO> response = cmdbuildSoapService.findTenants(id);
		TenantsDTO TenantsDTO = response.getDto();
		TenantsDTO.setCode("codeliukai333");
		TenantsDTO.setDescription("刘凯目前单身,求一妹子~!");
		IdResult result = cmdbuildSoapService.updateTenants(id, TenantsDTO);
		assertNotNull(result.getId());
	}

	@Test
	@Ignore
	public void delete() {
		Integer id = 88;
		IdResult response = cmdbuildSoapService.deleteTenants(id);
		assertNotNull(response.getId());
	}

	@Test
	@Ignore
	public void getPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		searchParams.put("EQ_company", 85);

		PaginationResult<TenantsDTO> result = cmdbuildSoapService.getTenantsPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());
		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());
	}
}
