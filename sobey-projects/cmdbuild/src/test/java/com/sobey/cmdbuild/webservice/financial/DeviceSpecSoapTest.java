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
import com.sobey.cmdbuild.entity.DeviceSpec;
import com.sobey.cmdbuild.webservice.response.dto.DeviceSpecDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class DeviceSpecSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateDeviceSpec();
		testFindDeviceSpec();
		testGetDeviceSpecList();
		testGetDeviceSpecPagination();
		testUpdateDeviceSpec();
		// testDeleteDeviceSpec();

	}

	// @Test
	// @Ignore
	public void testFindDeviceSpec() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<DeviceSpecDTO> responseParams = financialSoapService.findDeviceSpecByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<DeviceSpecDTO> response = financialSoapService.findDeviceSpec(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetDeviceSpecList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<DeviceSpecDTO> result = financialSoapService.getDeviceSpecList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	@Test
	// @Ignore
	public void testCreateDeviceSpec() {

		DeviceSpec deviceSpec = TestData.randomDeviceSpec();
		// deviceSpec.setCode("code7473");

		DeviceSpecDTO deviceSpecDTO = BeanMapper.map(deviceSpec, DeviceSpecDTO.class);

		IdResult response = financialSoapService.createDeviceSpec(deviceSpecDTO);

		assertNotNull(response.getId());

		code = deviceSpec.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateDeviceSpec() {

		DTOResult<DeviceSpecDTO> response = financialSoapService.findDeviceSpec(id);

		DeviceSpecDTO deviceSpecDTO = response.getDto();

		deviceSpecDTO.setCode(RandomData.randomName("code"));

		deviceSpecDTO.setDescription(RandomData.randomName("desc"));

		deviceSpecDTO.setDescription(RandomData.randomName("description"));

		IdResult result = financialSoapService.updateDeviceSpec(id, deviceSpecDTO);

		assertEquals("0", result.getCode());

	} // @Test

	// @Ignore
	public void testDeleteDeviceSpec() {

		IdResult response = financialSoapService.deleteDeviceSpec(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetDeviceSpecPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<DeviceSpecDTO> result = financialSoapService.getDeviceSpecPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}