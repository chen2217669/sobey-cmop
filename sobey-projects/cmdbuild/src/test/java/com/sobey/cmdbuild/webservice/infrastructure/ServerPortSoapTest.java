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
import com.sobey.cmdbuild.entity.ServerPort;
import com.sobey.cmdbuild.webservice.response.dto.ServerPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class ServerPortSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateServerPort();
		testFindServerPort();
		testGetServerPortList();
		testGetServerPortPagination();
		testUpdateServerPort();
		testDeleteServerPort();

	}

	// @Test
	// @Ignore
	public void testFindServerPort() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<ServerPortDTO> responseParams = infrastructureService.findServerPortByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<ServerPortDTO> response = infrastructureService.findServerPort(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetServerPortList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<ServerPortDTO> result = infrastructureService.getServerPortList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateServerPort() {

		ServerPort serverPort = TestData.randomServerPort();

		ServerPortDTO serverPortDTO = BeanMapper.map(serverPort, ServerPortDTO.class);

		IdResult response = infrastructureService.createServerPort(serverPortDTO);

		assertNotNull(response.getId());

		code = serverPort.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateServerPort() {

		DTOResult<ServerPortDTO> response = infrastructureService.findServerPort(id);

		ServerPortDTO serverPortDTO = response.getDto();

		serverPortDTO.setCode(RandomData.randomName("code"));

		serverPortDTO.setDescription(RandomData.randomName("update"));

		IdResult result = infrastructureService.updateServerPort(id, serverPortDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteServerPort() {

		IdResult response = infrastructureService.deleteServerPort(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetServerPortPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<ServerPortDTO> result = infrastructureService.getServerPortPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}