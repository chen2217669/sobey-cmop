package com.sobey.cmdbuild.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.cmdbuild.service.financial.ConsumptionsService;
import com.sobey.cmdbuild.service.financial.DeviceSpecService;
import com.sobey.cmdbuild.service.financial.EcsSpecService;
import com.sobey.cmdbuild.service.financial.EipSpecService;
import com.sobey.cmdbuild.service.financial.Es3SpecService;
import com.sobey.cmdbuild.service.iaas.As2Service;
import com.sobey.cmdbuild.service.iaas.Cs2Service;
import com.sobey.cmdbuild.service.iaas.DnsService;
import com.sobey.cmdbuild.service.iaas.EcsService;
import com.sobey.cmdbuild.service.iaas.EipPolicyService;
import com.sobey.cmdbuild.service.iaas.EipService;
import com.sobey.cmdbuild.service.iaas.ElbPolicyService;
import com.sobey.cmdbuild.service.iaas.ElbService;
import com.sobey.cmdbuild.service.iaas.EsgPolicyService;
import com.sobey.cmdbuild.service.iaas.EsgService;
import com.sobey.cmdbuild.service.iaas.GroupPolicyService;
import com.sobey.cmdbuild.service.iaas.VpnService;
import com.sobey.cmdbuild.service.infrastructure.FimasBoxService;
import com.sobey.cmdbuild.service.infrastructure.FimasPortService;
import com.sobey.cmdbuild.service.infrastructure.FimasService;
import com.sobey.cmdbuild.service.infrastructure.FirewallPortService;
import com.sobey.cmdbuild.service.infrastructure.FirewallService;
import com.sobey.cmdbuild.service.infrastructure.HardDiskService;
import com.sobey.cmdbuild.service.infrastructure.IpaddressService;
import com.sobey.cmdbuild.service.infrastructure.LoadBalancerPortService;
import com.sobey.cmdbuild.service.infrastructure.LoadBalancerService;
import com.sobey.cmdbuild.service.infrastructure.MemoryService;
import com.sobey.cmdbuild.service.infrastructure.NetappBoxService;
import com.sobey.cmdbuild.service.infrastructure.NetappControllerService;
import com.sobey.cmdbuild.service.infrastructure.NetappPortService;
import com.sobey.cmdbuild.service.infrastructure.NicPortService;
import com.sobey.cmdbuild.service.infrastructure.NicService;
import com.sobey.cmdbuild.service.infrastructure.ServerPortService;
import com.sobey.cmdbuild.service.infrastructure.ServerService;
import com.sobey.cmdbuild.service.infrastructure.SwitchPortService;
import com.sobey.cmdbuild.service.infrastructure.SwitchesService;
import com.sobey.cmdbuild.service.infrastructure.VlanService;
import com.sobey.cmdbuild.service.organisation.CompanyService;
import com.sobey.cmdbuild.service.organisation.IdcService;
import com.sobey.cmdbuild.service.organisation.LookUpService;
import com.sobey.cmdbuild.service.organisation.RackService;
import com.sobey.cmdbuild.service.organisation.TagService;
import com.sobey.cmdbuild.service.organisation.TenantsService;

/**
 * Service引用公共类,将所有业务的service方法统一在此类中注入.
 * 
 * @author Administrator
 * 
 */
@Service
public class CommonService {

	@Autowired
	public As2Service as2Service;

	@Autowired
	public CompanyService companyService;

	@Autowired
	public ConsumptionsService consumptionsService;

	@Autowired
	public Cs2Service cs2Service;

	@Autowired
	public DeviceSpecService deviceSpecService;

	@Autowired
	public DnsService dnsService;

	@Autowired
	public EcsService ecsService;

	@Autowired
	public EcsSpecService ecsSpecService;

	@Autowired
	public EipPolicyService eipPolicyService;

	@Autowired
	public EipService eipService;

	@Autowired
	public EipSpecService eipSpecService;

	@Autowired
	public ElbPolicyService elbPolicyService;

	@Autowired
	public ElbService elbService;

	@Autowired
	public Es3SpecService es3SpecService;

	@Autowired
	public EsgPolicyService esgPolicyService;

	@Autowired
	public EsgService esgService;

	@Autowired
	public FimasBoxService fimasBoxService;

	@Autowired
	public FimasPortService fimasPortService;

	@Autowired
	public FimasService fimasService;

	@Autowired
	public FirewallPortService firewallPortService;

	@Autowired
	public FirewallService firewallService;

	@Autowired
	public GroupPolicyService groupPolicyService;

	@Autowired
	public HardDiskService hardDiskService;

	@Autowired
	public IdcService idcService;

	@Autowired
	public IpaddressService ipaddressService;

	@Autowired
	public LoadBalancerPortService loadBalancerPortService;

	@Autowired
	public LoadBalancerService loadBalancerService;

	@Autowired
	public LookUpService lookUpService;

	@Autowired
	public MemoryService memoryService;

	@Autowired
	public NetappBoxService netappBoxService;

	@Autowired
	public NetappControllerService netappControllerService;

	@Autowired
	public NetappPortService netappPortService;

	@Autowired
	public NicPortService nicPortService;

	@Autowired
	public NicService nicService;

	@Autowired
	public RackService rackService;

	@Autowired
	public ServerPortService serverPortService;

	@Autowired
	public ServerService serverService;

	@Autowired
	public SwitchesService switchesService;

	@Autowired
	public SwitchPortService switchPortService;

	@Autowired
	public TagService tagService;

	@Autowired
	public TenantsService tenantsService;

	@Autowired
	public VlanService vlanService;

	@Autowired
	public VpnService vpnService;
}
