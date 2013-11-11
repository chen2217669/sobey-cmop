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
import com.sobey.cmdbuild.constants.LookUpEnum;
import com.sobey.cmdbuild.webservice.response.dto.LookUpDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

/**
 * LookUp SOAP服务的功能测试, 测试主要的接口调用.
 * 
 * 使用在Spring applicaitonContext.xml中用<jaxws:client/>，根据CmdbuildWebService接口创建的Client.
 * 
 * 
 * @author Administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class LookUpSoapTest extends BaseFunctionalTestCase {

	@Test
	@Ignore
	public void find() {
		Integer id = 70;
		DTOResult<LookUpDTO> response = cmdbuildSoapService.findLookUp(id);
		assertEquals("CentOS 6.4 64bit", response.getDto().getDescription());

		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("EQ_type", LookUpEnum.Image.name());
		searchParams.put("EQ_description", "CentOS 6.4 64bit");

		DTOResult<LookUpDTO> responseParams = cmdbuildSoapService.findLookUpByParams(searchParams);
		assertEquals("CentOS 6.4 64bit", responseParams.getDto().getDescription());
	}

	@Test
	@Ignore
	public void getList() {
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put("EQ_type", LookUpEnum.Image.name());

		DTOListResult<LookUpDTO> result = cmdbuildSoapService.getLookUpList(searchParams);
		System.out.println("获得集合数量:" + result.getDtos().size());
		assertEquals("0", result.getCode());
	}

	@Test
	@Ignore
	public void getPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<LookUpDTO> result = cmdbuildSoapService.getLookUpPagination(searchParams, 2, 10);

		assertNotNull(result.getGetTotalElements());
		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());
	}
}
