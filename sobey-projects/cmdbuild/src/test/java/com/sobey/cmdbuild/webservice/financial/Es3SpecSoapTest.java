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
import com.sobey.cmdbuild.entity.Es3Spec;
import com.sobey.cmdbuild.webservice.response.dto.Es3SpecDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class Es3SpecSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateEs3Spec();
		testFindEs3Spec();
		testGetEs3SpecList();
		testGetEs3SpecPagination();
		testUpdateEs3Spec();
		testDeleteEs3Spec();

	}

	// @Test
	// @Ignore
	public void testFindEs3Spec() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<Es3SpecDTO> responseParams = financialSoapService.findEs3SpecByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<Es3SpecDTO> response = financialSoapService.findEs3Spec(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetEs3SpecList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<Es3SpecDTO> result = financialSoapService.getEs3SpecList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateEs3Spec() {

		Es3Spec es3Spec = TestData.randomEs3Spec();

		Es3SpecDTO es3SpecDTO = BeanMapper.map(es3Spec, Es3SpecDTO.class);

		IdResult response = financialSoapService.createEs3Spec(es3SpecDTO);

		assertNotNull(response.getId());

		code = es3Spec.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateEs3Spec() {

		DTOResult<Es3SpecDTO> response = financialSoapService.findEs3Spec(id);

		Es3SpecDTO es3SpecDTO = response.getDto();

		es3SpecDTO.setCode(RandomData.randomName("code"));

		es3SpecDTO.setDescription(RandomData.randomName("update"));

		IdResult result = financialSoapService.updateEs3Spec(id, es3SpecDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteEs3Spec() {

		IdResult response = financialSoapService.deleteEs3Spec(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetEs3SpecPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<Es3SpecDTO> result = financialSoapService.getEs3SpecPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}