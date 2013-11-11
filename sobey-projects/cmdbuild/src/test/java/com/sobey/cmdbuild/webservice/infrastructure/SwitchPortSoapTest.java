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
import com.sobey.cmdbuild.entity.SwitchPort;
import com.sobey.cmdbuild.webservice.response.dto.SwitchPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class SwitchPortSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateSwitchPort();
		testFindSwitchPort();
		testGetSwitchPortList();
		testGetSwitchPortPagination();
		testUpdateSwitchPort();
		testDeleteSwitchPort();

	}

	// @Test
	// @Ignore
	public void testFindSwitchPort() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<SwitchPortDTO> responseParams = infrastructureService.findSwitchPortByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<SwitchPortDTO> response = infrastructureService.findSwitchPort(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetSwitchPortList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<SwitchPortDTO> result = infrastructureService.getSwitchPortList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateSwitchPort() {

		SwitchPort switchPort = TestData.randomSwitchPort();

		SwitchPortDTO switchPortDTO = BeanMapper.map(switchPort, SwitchPortDTO.class);

		IdResult response = infrastructureService.createSwitchPort(switchPortDTO);

		assertNotNull(response.getId());

		code = switchPort.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateSwitchPort() {

		DTOResult<SwitchPortDTO> response = infrastructureService.findSwitchPort(id);

		SwitchPortDTO switchPortDTO = response.getDto();

		switchPortDTO.setCode(RandomData.randomName("code"));

		switchPortDTO.setDescription(RandomData.randomName("update"));

		IdResult result = infrastructureService.updateSwitchPort(id, switchPortDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteSwitchPort() {

		IdResult response = infrastructureService.deleteSwitchPort(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetSwitchPortPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<SwitchPortDTO> result = infrastructureService.getSwitchPortPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}