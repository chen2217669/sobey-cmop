package com.sobey.cmdbuild.webservice;

import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.cmdbuild.webservice.response.dto.IdcDTO;
import com.sobey.cmdbuild.webservice.response.dto.RackDTO;
import com.sobey.cmdbuild.webservice.response.dto.TagDTO;
import com.sobey.cmdbuild.webservice.response.dto.TenantsDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

/**
 * CMDBuild模块对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "CmdbuildService", targetNamespace = WsConstants.NS)
public interface CmdbuildSoapService {

	/*************************************************
	 *************** Organisation ********************
	 *************************************************/

	// ==============================//
	// =========== Comany ===========//
	// ==============================//

	DTOResult<CompanyDTO> findCompany(@WebParam(name = "id") Integer id);

	DTOResult<CompanyDTO> findCompanyByCode(@WebParam(name = "code") String code);

	IdResult createCompany(@WebParam(name = "companyDTO") CompanyDTO companyDTO);

	IdResult updateCompany(@WebParam(name = "id") Integer id, @WebParam(name = "companyDTO") CompanyDTO companyDTO);

	IdResult deleteCompany(@WebParam(name = "id") Integer id);

	DTOListResult<CompanyDTO> getCompanyList();

	PaginationResult<CompanyDTO> getCompanyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// =========== Tenants ==========//
	// ==============================//

	DTOResult<TenantsDTO> findTenants(@WebParam(name = "id") Integer id);

	DTOResult<TenantsDTO> findTenantsByCode(@WebParam(name = "code") String code);

	IdResult createTenants(@WebParam(name = "tenantsDTO") TenantsDTO tenantsDTO);

	IdResult updateTenants(@WebParam(name = "id") Integer id, @WebParam(name = "tenantsDTO") TenantsDTO tenantsDTO);

	IdResult deleteTenants(@WebParam(name = "id") Integer id);

	DTOListResult<TenantsDTO> getTenantsList();

	PaginationResult<TenantsDTO> getTenantsPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Tag =============//
	// ==============================//

	DTOResult<TagDTO> findTag(@WebParam(name = "id") Integer id);

	DTOResult<TagDTO> findTagByCode(@WebParam(name = "code") String code);

	IdResult createTag(@WebParam(name = "tagDTO") TagDTO tagDTO);

	IdResult updateTag(@WebParam(name = "id") Integer id, @WebParam(name = "tagDTO") TagDTO tagDTO);

	IdResult deleteTag(@WebParam(name = "id") Integer id);

	DTOListResult<TagDTO> getTagList();

	DTOListResult<TagDTO> getTagListByTenants(@WebParam(name = "tenantsId") Integer tenantsId);

	PaginationResult<TagDTO> getTagPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ IDC =============//
	// ==============================//

	DTOResult<IdcDTO> findIdc(@WebParam(name = "id") Integer id);

	DTOResult<IdcDTO> findIdcByCode(@WebParam(name = "code") String code);

	IdResult createIdc(@WebParam(name = "idcDTO") IdcDTO idcDTO);

	IdResult updateIdc(@WebParam(name = "id") Integer id, @WebParam(name = "idcDTO") IdcDTO idcDTO);

	IdResult deleteIdc(@WebParam(name = "id") Integer id);

	DTOListResult<IdcDTO> getIdcList();

	PaginationResult<IdcDTO> getIdcPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Rack ============//
	// ==============================//

	DTOResult<RackDTO> findRack(@WebParam(name = "id") Integer id);

	DTOResult<RackDTO> findRackByCode(@WebParam(name = "code") String code);

	IdResult createRack(@WebParam(name = "rackDTO") RackDTO rackDTO);

	IdResult updateRack(@WebParam(name = "id") Integer id, @WebParam(name = "rackDTO") RackDTO rackDTO);

	IdResult deleteRack(@WebParam(name = "id") Integer id);

	DTOListResult<RackDTO> getRackList();

	PaginationResult<RackDTO> getRackPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/*************************************************
	 ****************** Financial ********************
	 *************************************************/

	// ==============================//
	// ========= Consumptions =======//
	// ==============================//

	// ==============================//
	// ========= DeviceSpec =========//
	// ==============================//

	// ==============================//
	// =========== EipSpec ==========//
	// ==============================//

	// ==============================//
	// ============ Es3Spec =========//
	// ==============================//

}
