package com.sobey.cmdbuild.webservice.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.BaseFunctionalTestCase;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.entity.LoadBalancer;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class LoadBalancerSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateLoadBalancer();
		testFindLoadBalancer();
		testGetLoadBalancerList();
		testGetLoadBalancerPagination();
		testUpdateLoadBalancer();
		testDeleteLoadBalancer();

	}

	// @Test
	// @Ignore
	public void testFindLoadBalancer() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<LoadBalancerDTO> responseParams = infrastructureService.findLoadBalancerByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<LoadBalancerDTO> response = infrastructureService.findLoadBalancer(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetLoadBalancerList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<LoadBalancerDTO> result = infrastructureService.getLoadBalancerList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateLoadBalancer() {

		LoadBalancer loadBalancer = TestData.randomLoadBalancer();

		LoadBalancerDTO loadBalancerDTO = BeanMapper.map(loadBalancer, LoadBalancerDTO.class);

		IdResult response = infrastructureService.createLoadBalancer(loadBalancerDTO);

		assertNotNull(response.getId());

		code = loadBalancer.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateLoadBalancer() {

		DTOResult<LoadBalancerDTO> response = infrastructureService.findLoadBalancer(id);

		LoadBalancerDTO loadBalancerDTO = response.getDto();

		loadBalancerDTO.setCode(RandomData.randomName("code"));

		loadBalancerDTO.setDescription(RandomData.randomName("update"));

		IdResult result = infrastructureService.updateLoadBalancer(id, loadBalancerDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteLoadBalancer() {

		IdResult response = infrastructureService.deleteLoadBalancer(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetLoadBalancerPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<LoadBalancerDTO> result = infrastructureService.getLoadBalancerPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}