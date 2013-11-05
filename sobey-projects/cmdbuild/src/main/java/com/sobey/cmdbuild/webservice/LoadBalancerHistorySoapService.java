package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "LoadBalancerHistoryService", targetNamespace = WsConstants.NS)
public interface LoadBalancerHistorySoapService {
	DTOResult<LoadBalancerHistoryDTO> findLoadBalancerHistory(@WebParam(name = "id") Integer id);

	DTOResult<LoadBalancerHistoryDTO> findLoadBalancerHistoryByCode(@WebParam(name = "code") String code);

	IdResult createLoadBalancerHistory(
			@WebParam(name = "loadBalancerHistoryDTO") LoadBalancerHistoryDTO loadBalancerHistoryDTO);

	IdResult updateLoadBalancerHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerHistoryDTO") LoadBalancerHistoryDTO loadBalancerHistoryDTO);

	IdResult deleteLoadBalancerHistory(@WebParam(name = "id") Integer id);

	DTOListResult<LoadBalancerHistoryDTO> getLoadBalancerHistoryList();

	PaginationResult<LoadBalancerHistoryDTO> getLoadBalancerHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}