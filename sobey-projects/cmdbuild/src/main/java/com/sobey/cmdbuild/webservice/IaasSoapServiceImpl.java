package com.sobey.cmdbuild.webservice;

import javax.jws.WebService;

import org.apache.cxf.feature.Features;

import com.sobey.cmdbuild.constants.WsConstants;

//@WebService(serviceName = "CmdbuildService", endpointInterface = "com.sobey.cmdbuild.webservice.CmdbuildSoapService", targetNamespace = WsConstants.NS)
//查看webservice的日志.
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class IaasSoapServiceImpl extends BasicSoapSevcie implements IaasSoapService {

}
