package com.sobey.cmdbuild.webservice.financial;

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
import com.sobey.cmdbuild.entity.Consumptions;
import com.sobey.cmdbuild.webservice.response.dto.ConsumptionsDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class ConsumptionsSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateConsumptions();
		testFindConsumptions();
		// testGetConsumptionsList();
		// testGetConsumptionsPagination();
		testUpdateConsumptions();
		// testDeleteConsumptions();

	}

	// @Test
	// @Ignore
	public void testFindConsumptions() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<ConsumptionsDTO> responseParams = financialSoapService.findConsumptionsByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<ConsumptionsDTO> response = financialSoapService.findConsumptions(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetConsumptionsList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<ConsumptionsDTO> result = financialSoapService.getConsumptionsList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateConsumptions() {

		Consumptions consumptions = TestData.randomConsumptions();

		// consumptions.setCode("code108367");

		ConsumptionsDTO consumptionsDTO = BeanMapper.map(consumptions, ConsumptionsDTO.class);

		IdResult response = financialSoapService.createConsumptions(consumptionsDTO);

		assertNotNull(response.getId());

		code = consumptions.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateConsumptions() {

		DTOResult<ConsumptionsDTO> response = financialSoapService.findConsumptions(id);

		ConsumptionsDTO consumptionsDTO = response.getDto();

		consumptionsDTO.setCode(RandomData.randomName("code"));

		consumptionsDTO.setDescription(RandomData.randomName("update"));

		IdResult result = financialSoapService.updateConsumptions(id, consumptionsDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteConsumptions() {

		IdResult response = financialSoapService.deleteConsumptions(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetConsumptionsPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<ConsumptionsDTO> result = financialSoapService.getConsumptionsPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}