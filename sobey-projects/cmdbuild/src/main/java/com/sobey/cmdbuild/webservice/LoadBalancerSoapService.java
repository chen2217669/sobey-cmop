package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "LoadBalancerService", targetNamespace = WsConstants.NS)
public interface LoadBalancerSoapService {
	DTOResult<LoadBalancerDTO> findLoadBalancer(@WebParam(name = "id") Integer id);

	DTOResult<LoadBalancerDTO> findLoadBalancerByCode(@WebParam(name = "code") String code);

	IdResult createLoadBalancer(@WebParam(name = "loadBalancerDTO") LoadBalancerDTO loadBalancerDTO);

	IdResult updateLoadBalancer(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerDTO") LoadBalancerDTO loadBalancerDTO);

	IdResult deleteLoadBalancer(@WebParam(name = "id") Integer id);

	DTOListResult<LoadBalancerDTO> getLoadBalancerList();

	PaginationResult<LoadBalancerDTO> getLoadBalancerPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}