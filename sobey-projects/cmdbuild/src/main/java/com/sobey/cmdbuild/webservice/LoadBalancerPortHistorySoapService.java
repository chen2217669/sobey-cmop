package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "LoadBalancerPortHistoryService", targetNamespace = WsConstants.NS)
public interface LoadBalancerPortHistorySoapService {
	DTOResult<LoadBalancerPortHistoryDTO> findLoadBalancerPortHistory(@WebParam(name = "id") Integer id);

	DTOResult<LoadBalancerPortHistoryDTO> findLoadBalancerPortHistoryByCode(@WebParam(name = "code") String code);

	IdResult createLoadBalancerPortHistory(
			@WebParam(name = "loadBalancerPortHistoryDTO") LoadBalancerPortHistoryDTO loadBalancerPortHistoryDTO);

	IdResult updateLoadBalancerPortHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerPortHistoryDTO") LoadBalancerPortHistoryDTO loadBalancerPortHistoryDTO);

	IdResult deleteLoadBalancerPortHistory(@WebParam(name = "id") Integer id);

	DTOListResult<LoadBalancerPortHistoryDTO> getLoadBalancerPortHistoryList();

	PaginationResult<LoadBalancerPortHistoryDTO> getLoadBalancerPortHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}