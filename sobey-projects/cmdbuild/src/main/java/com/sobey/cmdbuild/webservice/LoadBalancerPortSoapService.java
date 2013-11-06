package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "LoadBalancerPortService", targetNamespace = WsConstants.NS)
public interface LoadBalancerPortSoapService {
	DTOResult<LoadBalancerPortDTO> findLoadBalancerPort(@WebParam(name = "id") Integer id);

	DTOResult<LoadBalancerPortDTO> findLoadBalancerPortByCode(@WebParam(name = "code") String code);

	IdResult createLoadBalancerPort(@WebParam(name = "loadBalancerPortDTO") LoadBalancerPortDTO loadBalancerPortDTO);

	IdResult updateLoadBalancerPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerPortDTO") LoadBalancerPortDTO loadBalancerPortDTO);

	IdResult deleteLoadBalancerPort(@WebParam(name = "id") Integer id);

	DTOListResult<LoadBalancerPortDTO> getLoadBalancerPortList();

	PaginationResult<LoadBalancerPortDTO> getLoadBalancerPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}