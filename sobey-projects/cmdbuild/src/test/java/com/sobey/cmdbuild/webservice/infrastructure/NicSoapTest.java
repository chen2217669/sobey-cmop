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
import com.sobey.cmdbuild.entity.Nic;
import com.sobey.cmdbuild.webservice.response.dto.NicDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.test.data.RandomData;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class NicSoapTest extends BaseFunctionalTestCase {
	private Integer id = 0;

	private String code = "";

	@Test
	public void testAll() {
		testCreateNic();
		testFindNic();
		testGetNicList();
		testGetNicPagination();
		testUpdateNic();
		testDeleteNic();

	}

	// @Test
	// @Ignore
	public void testFindNic() {
		System.out.println(code + ">>>>>>>>>>>>>");

		Map<String, Object> searchParams = Maps.newHashMap();

		searchParams.put("EQ_code", code);

		DTOResult<NicDTO> responseParams = infrastructureService.findNicByParams(searchParams);

		assertEquals(code, responseParams.getDto().getCode());

		id = responseParams.getDto().getId();// 设置id

		DTOResult<NicDTO> response = infrastructureService.findNic(id);

		assertNotNull(response);

		System.out.println(id + ">>>>>>>>>>>>>");

	}

	// @Test
	// @Ignore
	public void testGetNicList() {

		Map<String, Object> searchParams = Maps.newHashMap();

		DTOListResult<NicDTO> result = infrastructureService.getNicList(searchParams);

		System.out.println("返回的查询结果数量:" + result.getDtos().size());

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testCreateNic() {

		Nic nic = TestData.randomNic();

		NicDTO nicDTO = BeanMapper.map(nic, NicDTO.class);

		IdResult response = infrastructureService.createNic(nicDTO);

		assertNotNull(response.getId());

		code = nic.getCode();// 设置code

	}

	// @Test
	// @Ignore
	public void testUpdateNic() {

		DTOResult<NicDTO> response = infrastructureService.findNic(id);

		NicDTO nicDTO = response.getDto();

		nicDTO.setCode(RandomData.randomName("code"));

		nicDTO.setDescription(RandomData.randomName("update"));

		IdResult result = infrastructureService.updateNic(id, nicDTO);

		assertEquals("0", result.getCode());

	}

	// @Test
	// @Ignore
	public void testDeleteNic() {

		IdResult response = infrastructureService.deleteNic(id);

		assertNotNull(response.getId());

	}

	// @Test
	// @Ignore
	public void testGetNicPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		PaginationResult<NicDTO> result = infrastructureService.getNicPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());

		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());

	}
}