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
import com.sobey.cmdbuild.entity.Fimas;
import com.sobey.cmdbuild.webservice.response.dto.FimasDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class FimasSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateFimas();
		testFindFimas();
		testGetFimasList();
		testGetFimasPagination();
		testUpdateFimas();
		testDeleteFimas();

	}

	// @Test
	// @Ignore
	public void testFindFimas() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<FimasDTO> responseParams = infrastructureService.findFimasByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<FimasDTO> response = infrastructureService.findFimas(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetFimasList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<FimasDTO> result = infrastructureService.getFimasList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateFimas() {

		Fimas fimas = TestData.randomFimas();

		FimasDTO fimasDTO = BeanMapper.map(fimas, FimasDTO.class);

		IdResult response = infrastructureService.createFimas(fimasDTO);

		assertNotNull(response.getId());

		code = fimas.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateFimas() {

		DTOResult<FimasDTO> response = infrastructureService.findFimas(id);

		FimasDTO fimasDTO = response.getDto();

		fimasDTO.setCode(RandomData.randomName("code"));

		fimasDTO.setDescription(RandomData.randomName("update"));

		IdResult result = infrastructureService.updateFimas(id, fimasDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteFimas() {

		IdResult response = infrastructureService.deleteFimas(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetFimasPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<FimasDTO> result = infrastructureService.getFimasPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}