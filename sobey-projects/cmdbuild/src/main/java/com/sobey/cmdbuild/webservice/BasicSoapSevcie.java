package com.sobey.cmdbuild.webservice;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.cmdbuild.service.CommonService;
import com.sobey.cmdbuild.webservice.response.result.WSResult;

/**
 * SoapSevcie的基本类.
 * 
 * 包含公共service、validator的注入和错误的处理方法.
 * 
 * @author Administrator
 * 
 */
public class BasicSoapSevcie {

	protected static Logger logger = LoggerFactory.getLogger(BasicSoapSevcie.class);

	@Autowired
	protected Validator validator;

	@Autowired
	protected CommonService comm;

	protected <T extends WSResult> T handleParameterError(T result, Exception e, String message) {
		logger.error(message, e.getMessage());
		result.setError(WSResult.PARAMETER_ERROR, message);
		return result;
	}

	protected <T extends WSResult> T handleParameterError(T result, Exception e) {
		logger.error(e.getMessage());
		result.setError(WSResult.PARAMETER_ERROR, e.getMessage());
		return result;
	}

	protected <T extends WSResult> T handleGeneralError(T result, Exception e) {
		logger.error(e.getMessage());
		result.setDefaultError();
		return result;
	}

	protected <T extends WSResult> T handleGeneralError(T result, Exception e, String message) {
		logger.error(message, e.getMessage());
		result.setError(WSResult.PARAMETER_ERROR, message);
		return result;
	}

}
