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
import com.sobey.cmdbuild.entity.EipSpec;
import com.sobey.cmdbuild.webservice.response.dto.EipSpecDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class EipSpecSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateEipSpec();
		testFindEipSpec();
		testGetEipSpecList();
		testGetEipSpecPagination();
		testUpdateEipSpec();
		// testDeleteEipSpec();

	}

	// @Test
	// @Ignore
	public void testFindEipSpec() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<EipSpecDTO> responseParams = financialSoapService.findEipSpecByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<EipSpecDTO> response = financialSoapService.findEipSpec(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetEipSpecList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<EipSpecDTO> result = financialSoapService.getEipSpecList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateEipSpec() {

		EipSpec eipSpec = TestData.randomEipSpec();

		EipSpecDTO eipSpecDTO = BeanMapper.map(eipSpec, EipSpecDTO.class);

		IdResult response = financialSoapService.createEipSpec(eipSpecDTO);

		assertNotNull(response.getId());

		code = eipSpec.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateEipSpec() {

		DTOResult<EipSpecDTO> response = financialSoapService.findEipSpec(id);

		EipSpecDTO eipSpecDTO = response.getDto();

		eipSpecDTO.setCode(RandomData.randomName("code"));

		eipSpecDTO.setDescription(RandomData.randomName("update"));

		IdResult result = financialSoapService.updateEipSpec(id, eipSpecDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteEipSpec() {

		IdResult response = financialSoapService.deleteEipSpec(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetEipSpecPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<EipSpecDTO> result = financialSoapService.getEipSpecPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}