package com.sobey.cmdbuild.webservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.sobey.cmdbuild.BaseFunctionalTestCase;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.webservice.response.dto.TagDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.mapper.BeanMapper;

/**
 * Tag SOAP服务的功能测试, 测试主要的接口调用.
 * 
 * 使用在Spring applicaitonContext.xml中用<jaxws:client/>，根据CmdbuildWebService接口创建的Client.
 * 
 * 
 * @author Administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class TagSoapTest extends BaseFunctionalTestCase {

	@Autowired
	private CmdbuildSoapService service;

	@Test
	@Ignore
	public void find() {
		Integer id = 102;
		DTOResult<TagDTO> response = service.findTag(id);
		assertEquals("sobey", response.getDto().getCode());
	}

	@Test
	@Ignore
	public void getList() {
		DTOListResult<TagDTO> result = service.getTagList();
		assertEquals("0", result.getCode());
	}

	@Test
	@Ignore
	public void getListByTenants() {
		Integer tenantsId = 87;
		DTOListResult<TagDTO> result = service.getTagListByTenants(tenantsId);
		assertEquals("0", result.getCode());
	}

	@Test
	@Ignore
	public void save() {
		Tag tag = TestData.randomTag();
		TagDTO tagDTO = BeanMapper.map(tag, TagDTO.class);
		IdResult response = service.createTag(tagDTO);
		assertNotNull(response.getId());
	}

	@Test
	@Ignore
	public void update() {
		Integer id = 102;
		DTOResult<TagDTO> response = service.findTag(id);
		TagDTO tagDTO = response.getDto();
		tagDTO.setDescription("冬天来了啊");
		IdResult result = service.updateTag(id, tagDTO);
		assertNotNull(result.getId());
	}

	@Test
	// @Ignore
	public void delete() {
		Integer id = 102;
		IdResult response = service.deleteTag(id);
		assertNotNull(response.getId());
	}

	@Test
	@Ignore
	public void getPagination() {

		Map<String, Object> searchParams = new HashMap<String, Object>();

		searchParams.put("EQ_company", 87);

		PaginationResult<TagDTO> result = service.getTagPagination(searchParams, 1, 10);

		assertNotNull(result.getGetTotalElements());
		System.out.println("返回的查询结果数量:" + result.getGetTotalElements());
	}
}
