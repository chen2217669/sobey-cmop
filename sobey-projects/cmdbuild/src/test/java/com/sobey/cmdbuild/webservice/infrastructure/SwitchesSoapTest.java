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
import com.sobey.cmdbuild.entity.Switches;
import com.sobey.cmdbuild.webservice.response.dto.SwitchesDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class SwitchesSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateSwitches();
		testFindSwitches();
		testGetSwitchesList();
		testGetSwitchesPagination();
		testUpdateSwitches();
		testDeleteSwitches();

	}

	// @Test
	// @Ignore
	public void testFindSwitches() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<SwitchesDTO> responseParams = infrastructureService.findSwitchesByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<SwitchesDTO> response = infrastructureService.findSwitches(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetSwitchesList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<SwitchesDTO> result = infrastructureService.getSwitchesList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateSwitches() {

		Switches switches = TestData.randomSwitches();

		SwitchesDTO switchesDTO = BeanMapper.map(switches, SwitchesDTO.class);

		IdResult response = infrastructureService.createSwitches(switchesDTO);

		assertNotNull(response.getId());

		code = switches.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateSwitches() {

		DTOResult<SwitchesDTO> response = infrastructureService.findSwitches(id);

		SwitchesDTO switchesDTO = response.getDto();

		switchesDTO.setCode(RandomData.randomName("code"));

		switchesDTO.setDescription(RandomData.randomName("update"));

		IdResult result = infrastructureService.updateSwitches(id, switchesDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteSwitches() {

		IdResult response = infrastructureService.deleteSwitches(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetSwitchesPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<SwitchesDTO> result = infrastructureService.getSwitchesPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}