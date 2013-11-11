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
import com.sobey.cmdbuild.entity.NetappController;
import com.sobey.cmdbuild.webservice.response.dto.NetappControllerDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class NetappControllerSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateNetappController();
		testFindNetappController();
		testGetNetappControllerList();
		testGetNetappControllerPagination();
		testUpdateNetappController();
		testDeleteNetappController();

	}

	// @Test
	// @Ignore
	public void testFindNetappController() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<NetappControllerDTO> responseParams = infrastructureService
				.findNetappControllerByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<NetappControllerDTO> response = infrastructureService.findNetappController(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetNetappControllerList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<NetappControllerDTO> result = infrastructureService.getNetappControllerList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateNetappController() {

		NetappController netappController = TestData.randomNetappController();

		NetappControllerDTO netappControllerDTO = BeanMapper.map(netappController, NetappControllerDTO.class);

		IdResult response = infrastructureService.createNetappController(netappControllerDTO);

		assertNotNull(response.getId());

		code = netappController.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateNetappController() {

		DTOResult<NetappControllerDTO> response = infrastructureService.findNetappController(id);

		NetappControllerDTO netappControllerDTO = response.getDto();

		netappControllerDTO.setCode(RandomData.randomName("code"));

		netappControllerDTO.setDescription(RandomData.randomName("update"));

		IdResult result = infrastructureService.updateNetappController(id, netappControllerDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteNetappController() {

		IdResult response = infrastructureService.deleteNetappController(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetNetappControllerPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<NetappControllerDTO> result = infrastructureService.getNetappControllerPagination(
				searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}