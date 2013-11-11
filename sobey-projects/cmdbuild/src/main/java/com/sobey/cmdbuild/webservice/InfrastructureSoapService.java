package com.sobey.cmdbuild.webservice;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.FimasBoxDTO;
import com.sobey.cmdbuild.webservice.response.dto.FimasDTO;
import com.sobey.cmdbuild.webservice.response.dto.FimasPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.FirewallDTO;
import com.sobey.cmdbuild.webservice.response.dto.FirewallPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.HardDiskDTO;
import com.sobey.cmdbuild.webservice.response.dto.IpaddressDTO;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerDTO;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.MemoryDTO;
import com.sobey.cmdbuild.webservice.response.dto.NetappBoxDTO;
import com.sobey.cmdbuild.webservice.response.dto.NetappControllerDTO;
import com.sobey.cmdbuild.webservice.response.dto.NetappPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.NicDTO;
import com.sobey.cmdbuild.webservice.response.dto.NicPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.ServerDTO;
import com.sobey.cmdbuild.webservice.response.dto.ServerPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.SwitchPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.SwitchesDTO;
import com.sobey.cmdbuild.webservice.response.dto.VlanDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "InfrastructureService", targetNamespace = WsConstants.NS)
public interface InfrastructureSoapService {

	/*************************************************
	 *************** Infrastructure ********************
	 *************************************************/

	// ==============================//
	// =========== Fimas ===========//
	// ==============================//

	DTOResult<FimasDTO> findFimas(@WebParam(name = "id") Integer id);

	DTOResult<FimasDTO> findFimasByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createFimas(@WebParam(name = "FimasDTO") FimasDTO fimasDTO);

	IdResult updateFimas(@WebParam(name = "id") Integer id, @WebParam(name = "FimasDTO") FimasDTO fimasDTO);

	IdResult deleteFimas(@WebParam(name = "id") Integer id);

	DTOListResult<FimasDTO> getFimasList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<FimasDTO> getFimasPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== FimasBox ==========//
	// ==============================//

	DTOResult<FimasBoxDTO> findFimasBox(@WebParam(name = "id") Integer id);

	DTOResult<FimasBoxDTO> findFimasBoxByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createFimasBox(@WebParam(name = "FimasBoxDTO") FimasBoxDTO fimasBoxDTO);

	IdResult updateFimasBox(@WebParam(name = "id") Integer id, @WebParam(name = "FimasBoxDTO") FimasBoxDTO fimasBoxDTO);

	IdResult deleteFimasBox(@WebParam(name = "id") Integer id);

	DTOListResult<FimasBoxDTO> getFimasBoxList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<FimasBoxDTO> getFimasBoxPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== FimasPort =========//
	// ==============================//

	DTOResult<FimasPortDTO> findFimasPort(@WebParam(name = "id") Integer id);

	DTOResult<FimasPortDTO> findFimasPortByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createFimasPort(@WebParam(name = "FimasPortDTO") FimasPortDTO fimasPortDTO);

	IdResult updateFimasPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "FimasPortDTO") FimasPortDTO fimasPortDTO);

	IdResult deleteFimasPort(@WebParam(name = "id") Integer id);

	DTOListResult<FimasPortDTO> getFimasPortList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<FimasPortDTO> getFimasPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== Firewall ==========//
	// ==============================//

	DTOResult<FirewallDTO> findFirewall(@WebParam(name = "id") Integer id);

	DTOResult<FirewallDTO> findFirewallByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createFirewall(@WebParam(name = "FirewallDTO") FirewallDTO firewallDTO);

	IdResult updateFirewall(@WebParam(name = "id") Integer id, @WebParam(name = "FirewallDTO") FirewallDTO firewallDTO);

	IdResult deleteFirewall(@WebParam(name = "id") Integer id);

	DTOListResult<FirewallDTO> getFirewallList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<FirewallDTO> getFirewallPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ======== FirewallPort ========//
	// ==============================//

	DTOResult<FirewallPortDTO> findFirewallPort(@WebParam(name = "id") Integer id);

	DTOResult<FirewallPortDTO> findFirewallPortByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createFirewallPort(@WebParam(name = "FirewallPortDTO") FirewallPortDTO firewallPortDTO);

	IdResult updateFirewallPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "FirewallPortDTO") FirewallPortDTO firewallPortDTO);

	IdResult deleteFirewallPort(@WebParam(name = "id") Integer id);

	DTOListResult<FirewallPortDTO> getFirewallPortList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<FirewallPortDTO> getFirewallPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== HardDisk ==========//
	// ==============================//

	DTOResult<HardDiskDTO> findHardDisk(@WebParam(name = "id") Integer id);

	DTOResult<HardDiskDTO> findHardDiskByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createHardDisk(@WebParam(name = "HardDiskDTO") HardDiskDTO hardDiskDTO);

	IdResult updateHardDisk(@WebParam(name = "id") Integer id, @WebParam(name = "HardDiskDTO") HardDiskDTO hardDiskDTO);

	IdResult deleteHardDisk(@WebParam(name = "id") Integer id);

	DTOListResult<HardDiskDTO> getHardDiskList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<HardDiskDTO> getHardDiskPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== Ipaddress =========//
	// ==============================//

	DTOResult<IpaddressDTO> findIpaddress(@WebParam(name = "id") Integer id);

	DTOResult<IpaddressDTO> findIpaddressByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createIpaddress(@WebParam(name = "IpaddressDTO") IpaddressDTO ipaddressDTO);

	IdResult updateIpaddress(@WebParam(name = "id") Integer id,
			@WebParam(name = "IpaddressDTO") IpaddressDTO ipaddressDTO);

	IdResult deleteIpaddress(@WebParam(name = "id") Integer id);

	DTOListResult<IpaddressDTO> getIpaddressList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<IpaddressDTO> getIpaddressPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/**
	 * 分配 IPAddress。将 IPAddress 的状态设置为 “使用”状态
	 * 
	 * @param id
	 * @return IdResult
	 */
	IdResult allocateIPAddress(@WebParam(name = "id") Integer id);

	/**
	 * 批量插入 IPAddress。 新增对象。<br/>
	 * 先判断是否有相同的 code，如果有相同的 code 则不能创建。<br/>
	 * 初始的状态为“未使用” 。<br/>
	 * 
	 * @param ipaddressDTOList
	 * @return List<IdResult>
	 */
	List<IdResult> insertIPAddress(@WebParam(name = "IpaddressDTOList") List<IpaddressDTO> ipaddressDTOList);

	/**
	 * 初始化 IPAddress。<br/>
	 * 将 IPAddress 的状态设置为 “未使用”状态<br/>
	 * 
	 * @param id
	 * @return IdResult
	 */
	IdResult initIPAddress(@WebParam(name = "id") Integer id);

	// ==============================//
	// ======== LoadBalancer ========//
	// ==============================//

	DTOResult<LoadBalancerDTO> findLoadBalancer(@WebParam(name = "id") Integer id);

	DTOResult<LoadBalancerDTO> findLoadBalancerByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createLoadBalancer(@WebParam(name = "LoadBalancerDTO") LoadBalancerDTO loadBalancerDTO);

	IdResult updateLoadBalancer(@WebParam(name = "id") Integer id,
			@WebParam(name = "LoadBalancerDTO") LoadBalancerDTO loadBalancerDTO);

	IdResult deleteLoadBalancer(@WebParam(name = "id") Integer id);

	DTOListResult<LoadBalancerDTO> getLoadBalancerList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<LoadBalancerDTO> getLoadBalancerPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ====== LoadBalancerPort ======//
	// ==============================//

	DTOResult<LoadBalancerPortDTO> findLoadBalancerPort(@WebParam(name = "id") Integer id);

	DTOResult<LoadBalancerPortDTO> findLoadBalancerPortByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createLoadBalancerPort(@WebParam(name = "LoadBalancerPortDTO") LoadBalancerPortDTO loadBalancerPortDTO);

	IdResult updateLoadBalancerPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "LoadBalancerPortDTO") LoadBalancerPortDTO loadBalancerPortDTO);

	IdResult deleteLoadBalancerPort(@WebParam(name = "id") Integer id);

	DTOListResult<LoadBalancerPortDTO> getLoadBalancerPortList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<LoadBalancerPortDTO> getLoadBalancerPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// =========== Memory ===========//
	// ==============================//

	DTOResult<MemoryDTO> findMemory(@WebParam(name = "id") Integer id);

	DTOResult<MemoryDTO> findMemoryByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createMemory(@WebParam(name = "MemoryDTO") MemoryDTO memoryDTO);

	IdResult updateMemory(@WebParam(name = "id") Integer id, @WebParam(name = "MemoryDTO") MemoryDTO memoryDTO);

	IdResult deleteMemory(@WebParam(name = "id") Integer id);

	DTOListResult<MemoryDTO> getMemoryList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<MemoryDTO> getMemoryPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== NetappBox =========//
	// ==============================//

	DTOResult<NetappBoxDTO> findNetappBox(@WebParam(name = "id") Integer id);

	DTOResult<NetappBoxDTO> findNetappBoxByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createNetappBox(@WebParam(name = "NetappBoxDTO") NetappBoxDTO netappBoxDTO);

	IdResult updateNetappBox(@WebParam(name = "id") Integer id,
			@WebParam(name = "NetappBoxDTO") NetappBoxDTO netappBoxDTO);

	IdResult deleteNetappBox(@WebParam(name = "id") Integer id);

	DTOListResult<NetappBoxDTO> getNetappBoxList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<NetappBoxDTO> getNetappBoxPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ====== NetappController ======//
	// ==============================//

	DTOResult<NetappControllerDTO> findNetappController(@WebParam(name = "id") Integer id);

	DTOResult<NetappControllerDTO> findNetappControllerByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createNetappController(@WebParam(name = "NetappControllerDTO") NetappControllerDTO netappControllerDTO);

	IdResult updateNetappController(@WebParam(name = "id") Integer id,
			@WebParam(name = "NetappControllerDTO") NetappControllerDTO netappControllerDTO);

	IdResult deleteNetappController(@WebParam(name = "id") Integer id);

	DTOListResult<NetappControllerDTO> getNetappControllerList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<NetappControllerDTO> getNetappControllerPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== NetappPort ========//
	// ==============================//

	DTOResult<NetappPortDTO> findNetappPort(@WebParam(name = "id") Integer id);

	DTOResult<NetappPortDTO> findNetappPortByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createNetappPort(@WebParam(name = "NetappPortDTO") NetappPortDTO netappPortDTO);

	IdResult updateNetappPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "NetappPortDTO") NetappPortDTO netappPortDTO);

	IdResult deleteNetappPort(@WebParam(name = "id") Integer id);

	DTOListResult<NetappPortDTO> getNetappPortList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<NetappPortDTO> getNetappPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============== Nic ===========//
	// ==============================//

	DTOResult<NicDTO> findNic(@WebParam(name = "id") Integer id);

	DTOResult<NicDTO> findNicByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createNic(@WebParam(name = "NicDTO") NicDTO nicDTO);

	IdResult updateNic(@WebParam(name = "id") Integer id, @WebParam(name = "NicDTO") NicDTO nicDTO);

	IdResult deleteNic(@WebParam(name = "id") Integer id);

	DTOListResult<NicDTO> getNicList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<NicDTO> getNicPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ NicPort =========//
	// ==============================//

	DTOResult<NicPortDTO> findNicPort(@WebParam(name = "id") Integer id);

	DTOResult<NicPortDTO> findNicPortByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createNicPort(@WebParam(name = "NicPortDTO") NicPortDTO nicPortDTO);

	IdResult updateNicPort(@WebParam(name = "id") Integer id, @WebParam(name = "NicPortDTO") NicPortDTO nicPortDTO);

	IdResult deleteNicPort(@WebParam(name = "id") Integer id);

	DTOListResult<NicPortDTO> getNicPortList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<NicPortDTO> getNicPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Server ==========//
	// ==============================//

	DTOResult<ServerDTO> findServer(@WebParam(name = "id") Integer id);

	DTOResult<ServerDTO> findServerByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createServer(@WebParam(name = "ServerDTO") ServerDTO serverDTO);

	IdResult updateServer(@WebParam(name = "id") Integer id, @WebParam(name = "ServerDTO") ServerDTO serverDTO);

	IdResult deleteServer(@WebParam(name = "id") Integer id);

	DTOListResult<ServerDTO> getServerList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<ServerDTO> getServerPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========== ServerPort ========//
	// ==============================//

	DTOResult<ServerPortDTO> findServerPort(@WebParam(name = "id") Integer id);

	DTOResult<ServerPortDTO> findServerPortByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createServerPort(@WebParam(name = "ServerPortDTO") ServerPortDTO serverPortDTO);

	IdResult updateServerPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "ServerPortDTO") ServerPortDTO serverPortDTO);

	IdResult deleteServerPort(@WebParam(name = "id") Integer id);

	DTOListResult<ServerPortDTO> getServerPortList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<ServerPortDTO> getServerPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// =========== Switches =========//
	// ==============================//

	DTOResult<SwitchesDTO> findSwitches(@WebParam(name = "id") Integer id);

	DTOResult<SwitchesDTO> findSwitchesByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createSwitches(@WebParam(name = "SwitchesDTO") SwitchesDTO switchesDTO);

	IdResult updateSwitches(@WebParam(name = "id") Integer id, @WebParam(name = "SwitchesDTO") SwitchesDTO switchesDTO);

	IdResult deleteSwitches(@WebParam(name = "id") Integer id);

	DTOListResult<SwitchesDTO> getSwitchesList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<SwitchesDTO> getSwitchesPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ========= SwitchPort =========//
	// ==============================//

	DTOResult<SwitchPortDTO> findSwitchPort(@WebParam(name = "id") Integer id);

	DTOResult<SwitchPortDTO> findSwitchPortByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createSwitchPort(@WebParam(name = "SwitchPortDTO") SwitchPortDTO switchPortDTO);

	IdResult updateSwitchPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "SwitchPortDTO") SwitchPortDTO switchPortDTO);

	IdResult deleteSwitchPort(@WebParam(name = "id") Integer id);

	DTOListResult<SwitchPortDTO> getSwitchPortList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<SwitchPortDTO> getSwitchPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Vlan ============//
	// ==============================//

	DTOResult<VlanDTO> findVlan(@WebParam(name = "id") Integer id);

	DTOResult<VlanDTO> findVlanByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createVlan(@WebParam(name = "VlanDTO") VlanDTO vlanDTO);

	IdResult updateVlan(@WebParam(name = "id") Integer id, @WebParam(name = "VlanDTO") VlanDTO vlanDTO);

	IdResult deleteVlan(@WebParam(name = "id") Integer id);

	DTOListResult<VlanDTO> getVlanList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<VlanDTO> getVlanPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/**
	 * 
	 * 批量插入 Vlan。先判断是否有相同的 code，如果有相同的 code 则不能创建
	 * 
	 * @param vlanDTOList
	 * @return List<IdResult>
	 */
	List<IdResult> insertVlan(@WebParam(name = "VlanDTOList") List<VlanDTO> vlanDTOList);

}
