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
import com.sobey.cmdbuild.entity.EcsSpec;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class EcsSpecSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateEcsSpec();
		testFindEcsSpec();
		testGetEcsSpecList();
		testGetEcsSpecPagination();
		testUpdateEcsSpec();
		// testDeleteEcsSpec();
	}

	// @Test
	// @Ignore
	public void testFindEcsSpec() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<EcsSpecDTO> responseParams = financialSoapService.findEcsSpecByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<EcsSpecDTO> response = financialSoapService.findEcsSpec(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetEcsSpecList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<EcsSpecDTO> result = financialSoapService.getEcsSpecList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateEcsSpec() {

		EcsSpec ecsSpec = TestData.randomEcsSpec();

		EcsSpecDTO ecsSpecDTO = BeanMapper.map(ecsSpec, EcsSpecDTO.class);

		IdResult response = financialSoapService.createEcsSpec(ecsSpecDTO);

		assertNotNull(response.getId());

		code = ecsSpec.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateEcsSpec() {

		DTOResult<EcsSpecDTO> response = financialSoapService.findEcsSpec(id);

		EcsSpecDTO ecsSpecDTO = response.getDto();

		ecsSpecDTO.setCode(RandomData.randomName("code"));

		ecsSpecDTO.setDescription(RandomData.randomName("update"));

		IdResult result = financialSoapService.updateEcsSpec(id, ecsSpecDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteEcsSpec() {

		IdResult response = financialSoapService.deleteEcsSpec(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetEcsSpecPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<EcsSpecDTO> result = financialSoapService.getEcsSpecPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}