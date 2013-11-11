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
import com.sobey.cmdbuild.entity.NetappPort;
import com.sobey.cmdbuild.webservice.response.dto.NetappPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class NetappPortSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateNetappPort();
		testFindNetappPort();
		testGetNetappPortList();
		testGetNetappPortPagination();
		testUpdateNetappPort();
		testDeleteNetappPort();

	}

	// @Test
	// @Ignore
	public void testFindNetappPort() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<NetappPortDTO> responseParams = infrastructureService.findNetappPortByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<NetappPortDTO> response = infrastructureService.findNetappPort(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetNetappPortList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<NetappPortDTO> result = infrastructureService.getNetappPortList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateNetappPort() {

		NetappPort netappPort = TestData.randomNetappPort();

		NetappPortDTO netappPortDTO = BeanMapper.map(netappPort, NetappPortDTO.class);

		IdResult response = infrastructureService.createNetappPort(netappPortDTO);

		assertNotNull(response.getId());

		code = netappPort.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateNetappPort() {

		DTOResult<NetappPortDTO> response = infrastructureService.findNetappPort(id);

		NetappPortDTO netappPortDTO = response.getDto();

		netappPortDTO.setCode(RandomData.randomName("code"));

		netappPortDTO.setDescription(RandomData.randomName("update"));

		IdResult result = infrastructureService.updateNetappPort(id, netappPortDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteNetappPort() {

		IdResult response = infrastructureService.deleteNetappPort(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetNetappPortPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<NetappPortDTO> result = infrastructureService.getNetappPortPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}