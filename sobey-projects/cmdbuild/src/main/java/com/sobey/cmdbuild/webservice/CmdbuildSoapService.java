package com.sobey.cmdbuild.webservice;

import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.cmdbuild.webservice.response.dto.IdcDTO;
import com.sobey.cmdbuild.webservice.response.dto.LookUpDTO;
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

	// ==============================//
	// =========== LookUp ===========//
	// == 系统默认表,只读取,不写入 ==//

	DTOResult<LookUpDTO> findLookUp(@WebParam(name = "id") Integer id);

	DTOResult<LookUpDTO> findLookUpByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	DTOListResult<LookUpDTO> getLookUpList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<LookUpDTO> getLookUpPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/*************************************************
	 *************** Organisation ********************
	 *************************************************/

	// ==============================//
	// =========== Comany ===========//
	// ==============================//

	DTOResult<CompanyDTO> findCompany(@WebParam(name = "id") Integer id);

	DTOResult<CompanyDTO> findCompanyByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createCompany(@WebParam(name = "companyDTO") CompanyDTO companyDTO);

	IdResult updateCompany(@WebParam(name = "id") Integer id, @WebParam(name = "companyDTO") CompanyDTO companyDTO);

	IdResult deleteCompany(@WebParam(name = "id") Integer id);

	DTOListResult<CompanyDTO> getCompanyList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<CompanyDTO> getCompanyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// =========== Tenants ==========//
	// ==============================//

	DTOResult<TenantsDTO> findTenants(@WebParam(name = "id") Integer id);

	DTOResult<TenantsDTO> findTenantsByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createTenants(@WebParam(name = "tenantsDTO") TenantsDTO tenantsDTO);

	IdResult updateTenants(@WebParam(name = "id") Integer id, @WebParam(name = "tenantsDTO") TenantsDTO tenantsDTO);

	IdResult deleteTenants(@WebParam(name = "id") Integer id);

	DTOListResult<TenantsDTO> getTenantsList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<TenantsDTO> getTenantsPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Tag =============//
	// ==============================//

	DTOResult<TagDTO> findTag(@WebParam(name = "id") Integer id);

	DTOResult<TagDTO> findTagByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createTag(@WebParam(name = "tagDTO") TagDTO tagDTO);

	IdResult updateTag(@WebParam(name = "id") Integer id, @WebParam(name = "tagDTO") TagDTO tagDTO);

	IdResult deleteTag(@WebParam(name = "id") Integer id);

	DTOListResult<TagDTO> getTagList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<TagDTO> getTagPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ IDC =============//
	// ==============================//

	DTOResult<IdcDTO> findIdc(@WebParam(name = "id") Integer id);

	DTOResult<IdcDTO> findIdcByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createIdc(@WebParam(name = "idcDTO") IdcDTO idcDTO);

	IdResult updateIdc(@WebParam(name = "id") Integer id, @WebParam(name = "idcDTO") IdcDTO idcDTO);

	IdResult deleteIdc(@WebParam(name = "id") Integer id);

	DTOListResult<IdcDTO> getIdcList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<IdcDTO> getIdcPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Rack ============//
	// ==============================//

	DTOResult<RackDTO> findRack(@WebParam(name = "id") Integer id);

	DTOResult<RackDTO> findRackByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createRack(@WebParam(name = "rackDTO") RackDTO rackDTO);

	IdResult updateRack(@WebParam(name = "id") Integer id, @WebParam(name = "rackDTO") RackDTO rackDTO);

	IdResult deleteRack(@WebParam(name = "id") Integer id);

	DTOListResult<RackDTO> getRackList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<RackDTO> getRackPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

}
