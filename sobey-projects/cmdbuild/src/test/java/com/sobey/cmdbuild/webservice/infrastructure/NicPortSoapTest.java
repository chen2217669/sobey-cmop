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
import com.sobey.cmdbuild.entity.NicPort;
import com.sobey.cmdbuild.webservice.response.dto.NicPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class NicPortSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateNicPort();
		testFindNicPort();
		testGetNicPortList();
		testGetNicPortPagination();
		testUpdateNicPort();
		testDeleteNicPort();

	}

	// @Test
	// @Ignore
	public void testFindNicPort() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<NicPortDTO> responseParams = infrastructureService.findNicPortByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<NicPortDTO> response = infrastructureService.findNicPort(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetNicPortList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<NicPortDTO> result = infrastructureService.getNicPortList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateNicPort() {

		NicPort nicPort = TestData.randomNicPort();

		NicPortDTO nicPortDTO = BeanMapper.map(nicPort, NicPortDTO.class);

		IdResult response = infrastructureService.createNicPort(nicPortDTO);

		assertNotNull(response.getId());

		code = nicPort.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateNicPort() {

		DTOResult<NicPortDTO> response = infrastructureService.findNicPort(id);

		NicPortDTO nicPortDTO = response.getDto();

		nicPortDTO.setCode(RandomData.randomName("code"));

		nicPortDTO.setDescription(RandomData.randomName("update"));

		IdResult result = infrastructureService.updateNicPort(id, nicPortDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteNicPort() {

		IdResult response = infrastructureService.deleteNicPort(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetNicPortPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<NicPortDTO> result = infrastructureService.getNicPortPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}