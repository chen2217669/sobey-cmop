package com.sobey.cmdbuild.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.cmdbuild.service.organisation.CompanyService;

/**
 * Service引用公共类,将所有业务的service方法统一在此类中注入.
 * 
 * @author Administrator
 * 
 */
@Service
public class CommonService {

	@Autowired
	public CompanyService companyService;

}
