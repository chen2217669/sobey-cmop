package com.sobey.cmdbuild.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;
import com.sobey.cmdbuild.entity.Company;
import com.sobey.core.persistence.DynamicSpecifications;
import com.sobey.core.persistence.SearchFilter;
import com.sobey.core.persistence.SearchFilter.Operator;
import com.sobey.test.spring.SpringTransactionalTestCase;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DynamicSpecificationTest extends SpringTransactionalTestCase {

	@Autowired
	private CompanyDao companyDao;

	@Test
	public void fineCompanyByFilter() {
		// EQ
		SearchFilter filter = new SearchFilter("code", Operator.EQ, "管理员");
		List<Company> Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter),
				Company.class));
		assertEquals(1, Companys.size());

		// LIKE
		filter = new SearchFilter("description", Operator.LIKE, "min");
		Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Company.class));
		assertEquals(1, Companys.size());

		// GT
		filter = new SearchFilter("id", Operator.GT, "1");
		Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Company.class));
		assertEquals(5, Companys.size());

		filter = new SearchFilter("id", Operator.GT, "6");
		Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Company.class));
		assertEquals(0, Companys.size());

		// GTE
		filter = new SearchFilter("id", Operator.GTE, "1");
		Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Company.class));
		assertEquals(6, Companys.size());

		filter = new SearchFilter("id", Operator.GTE, "6");
		Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Company.class));
		assertEquals(1, Companys.size());

		// LT
		filter = new SearchFilter("id", Operator.LT, "6");
		Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Company.class));
		assertEquals(5, Companys.size());

		filter = new SearchFilter("id", Operator.LT, "1");
		Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Company.class));
		assertEquals(0, Companys.size());

		// LTE
		filter = new SearchFilter("id", Operator.LTE, "6");
		Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Company.class));
		assertEquals(6, Companys.size());

		filter = new SearchFilter("id", Operator.LTE, "1");
		Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Company.class));
		assertEquals(1, Companys.size());

		// Empty filters, select all
		Companys = companyDao.findAll(DynamicSpecifications
				.bySearchFilter(new ArrayList<SearchFilter>(), Company.class));
		assertEquals(6, Companys.size());

		Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(null, Company.class));
		assertEquals(6, Companys.size());

		// AND 2 Conditions
		SearchFilter filter1 = new SearchFilter("code", Operator.EQ, "管理员");
		SearchFilter filter2 = new SearchFilter("description", Operator.LIKE, "min");
		Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter1, filter2),
				Company.class));
		assertEquals(1, Companys.size());

		filter1 = new SearchFilter("code", Operator.EQ, "管理员");
		filter2 = new SearchFilter("description", Operator.LIKE, "Company");
		Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter1, filter2),
				Company.class));
		assertEquals(0, Companys.size());

		// 2 conditions on same field
		filter1 = new SearchFilter("id", Operator.GTE, "1");
		filter2 = new SearchFilter("id", Operator.LTE, "6");

		Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter1, filter2),
				Company.class));
		assertEquals(6, Companys.size());

		// // Nest Attribute
		// filter = new SearchFilter("team.id", Operator.EQ, "1");
		// Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter),
		// Company.class));
		// assertEquals(6, Companys.size());
		//
		// filter = new SearchFilter("team.id", Operator.EQ, "10");
		// Companys = companyDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter),
		// Company.class));
		// assertEquals(0, Companys.size());
	}
}
