package com.sobey.cmdbuild.webservice;

import org.apache.cxf.feature.Features;

//@WebService(serviceName = "CmdbuildService", endpointInterface = "com.sobey.cmdbuild.webservice.CmdbuildSoapService", targetNamespace = WsConstants.NS)
//查看webservice的日志.
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class IaasSoapServiceImpl extends BasicSoapSevcie implements IaasSoapService {

}
