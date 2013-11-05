package com.sobey.cmdbuild.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.cmdbuild.service.organisation.As2HistoryService;
import com.sobey.cmdbuild.service.organisation.As2Service;
import com.sobey.cmdbuild.service.organisation.CompanyHistoryService;
import com.sobey.cmdbuild.service.organisation.CompanyService;
import com.sobey.cmdbuild.service.organisation.ConsumptionsHistoryService;
import com.sobey.cmdbuild.service.organisation.ConsumptionsService;
import com.sobey.cmdbuild.service.organisation.Cs2HistoryService;
import com.sobey.cmdbuild.service.organisation.Cs2Service;
import com.sobey.cmdbuild.service.organisation.DeviceSpecHistoryService;
import com.sobey.cmdbuild.service.organisation.DeviceSpecService;
import com.sobey.cmdbuild.service.organisation.DnsHistoryService;
import com.sobey.cmdbuild.service.organisation.DnsService;
import com.sobey.cmdbuild.service.organisation.EcsHistoryService;
import com.sobey.cmdbuild.service.organisation.EcsService;
import com.sobey.cmdbuild.service.organisation.EcsSpecHistoryService;
import com.sobey.cmdbuild.service.organisation.EcsSpecService;
import com.sobey.cmdbuild.service.organisation.EipHistoryService;
import com.sobey.cmdbuild.service.organisation.EipPolicyHistoryService;
import com.sobey.cmdbuild.service.organisation.EipPolicyService;
import com.sobey.cmdbuild.service.organisation.EipService;
import com.sobey.cmdbuild.service.organisation.EipSpecHistoryService;
import com.sobey.cmdbuild.service.organisation.EipSpecService;
import com.sobey.cmdbuild.service.organisation.ElbHistoryService;
import com.sobey.cmdbuild.service.organisation.ElbPolicyHistoryService;
import com.sobey.cmdbuild.service.organisation.ElbPolicyService;
import com.sobey.cmdbuild.service.organisation.ElbService;
import com.sobey.cmdbuild.service.organisation.Es3SpecHistoryService;
import com.sobey.cmdbuild.service.organisation.Es3SpecService;
import com.sobey.cmdbuild.service.organisation.EsgHistoryService;
import com.sobey.cmdbuild.service.organisation.EsgPolicyHistoryService;
import com.sobey.cmdbuild.service.organisation.EsgPolicyService;
import com.sobey.cmdbuild.service.organisation.EsgService;
import com.sobey.cmdbuild.service.organisation.FimasBoxHistoryService;
import com.sobey.cmdbuild.service.organisation.FimasBoxService;
import com.sobey.cmdbuild.service.organisation.FimasHistoryService;
import com.sobey.cmdbuild.service.organisation.FimasPortHistoryService;
import com.sobey.cmdbuild.service.organisation.FimasPortService;
import com.sobey.cmdbuild.service.organisation.FimasService;
import com.sobey.cmdbuild.service.organisation.FirewallHistoryService;
import com.sobey.cmdbuild.service.organisation.FirewallPortHistoryService;
import com.sobey.cmdbuild.service.organisation.FirewallPortService;
import com.sobey.cmdbuild.service.organisation.FirewallService;
import com.sobey.cmdbuild.service.organisation.GroupPolicyHistoryService;
import com.sobey.cmdbuild.service.organisation.GroupPolicyService;
import com.sobey.cmdbuild.service.organisation.HardDiskHistoryService;
import com.sobey.cmdbuild.service.organisation.HardDiskService;
import com.sobey.cmdbuild.service.organisation.IdcHistoryService;
import com.sobey.cmdbuild.service.organisation.IdcService;
import com.sobey.cmdbuild.service.organisation.IpaddressHistoryService;
import com.sobey.cmdbuild.service.organisation.IpaddressService;
import com.sobey.cmdbuild.service.organisation.LoadBalancerHistoryService;
import com.sobey.cmdbuild.service.organisation.LoadBalancerPortHistoryService;
import com.sobey.cmdbuild.service.organisation.LoadBalancerPortService;
import com.sobey.cmdbuild.service.organisation.LoadBalancerService;
import com.sobey.cmdbuild.service.organisation.MemoryHistoryService;
import com.sobey.cmdbuild.service.organisation.MemoryService;
import com.sobey.cmdbuild.service.organisation.NetappBoxHistoryService;
import com.sobey.cmdbuild.service.organisation.NetappBoxService;
import com.sobey.cmdbuild.service.organisation.NetappControllerHistoryService;
import com.sobey.cmdbuild.service.organisation.NetappControllerService;
import com.sobey.cmdbuild.service.organisation.NetappPortHistoryService;
import com.sobey.cmdbuild.service.organisation.NetappPortService;
import com.sobey.cmdbuild.service.organisation.NicHistoryService;
import com.sobey.cmdbuild.service.organisation.NicPortHistoryService;
import com.sobey.cmdbuild.service.organisation.NicPortService;
import com.sobey.cmdbuild.service.organisation.NicService;
import com.sobey.cmdbuild.service.organisation.RackHistoryService;
import com.sobey.cmdbuild.service.organisation.RackService;
import com.sobey.cmdbuild.service.organisation.ServerHistoryService;
import com.sobey.cmdbuild.service.organisation.ServerPortHistoryService;
import com.sobey.cmdbuild.service.organisation.ServerPortService;
import com.sobey.cmdbuild.service.organisation.ServerService;
import com.sobey.cmdbuild.service.organisation.SwitchPortHistoryService;
import com.sobey.cmdbuild.service.organisation.SwitchPortService;
import com.sobey.cmdbuild.service.organisation.SwitchesHistoryService;
import com.sobey.cmdbuild.service.organisation.SwitchesService;
import com.sobey.cmdbuild.service.organisation.TagHistoryService;
import com.sobey.cmdbuild.service.organisation.TagService;
import com.sobey.cmdbuild.service.organisation.TenantsHistoryService;
import com.sobey.cmdbuild.service.organisation.TenantsService;
import com.sobey.cmdbuild.service.organisation.VlanHistoryService;
import com.sobey.cmdbuild.service.organisation.VlanService;
import com.sobey.cmdbuild.service.organisation.VpnHistoryService;
import com.sobey.cmdbuild.service.organisation.VpnService;

/**
 * Service引用公共类,将所有业务的service方法统一在此类中注入.
 * 
 * @author Administrator
 * 
 */
@Service
public class CommonService {

	@Autowired
	public CompanyService companyService;

	@Autowired
	public As2Service as2Service;
	@Autowired
	public As2HistoryService as2HistoryService;
	@Autowired
	public CompanyHistoryService companyHistoryService;
	@Autowired
	public ConsumptionsService consumptionsService;
	@Autowired
	public ConsumptionsHistoryService consumptionsHistoryService;
	@Autowired
	public Cs2Service cs2Service;
	@Autowired
	public Cs2HistoryService cs2HistoryService;
	@Autowired
	public DeviceSpecService deviceSpecService;
	@Autowired
	public DeviceSpecHistoryService deviceSpecHistoryService;
	@Autowired
	public DnsService dnsService;
	@Autowired
	public DnsHistoryService dnsHistoryService;
	@Autowired
	public EcsService ecsService;
	@Autowired
	public EcsHistoryService ecsHistoryService;
	@Autowired
	public EcsSpecService ecsSpecService;
	@Autowired
	public EcsSpecHistoryService ecsSpecHistoryService;
	@Autowired
	public EipService eipService;
	@Autowired
	public EipHistoryService eipHistoryService;
	@Autowired
	public EipPolicyService eipPolicyService;
	@Autowired
	public EipPolicyHistoryService eipPolicyHistoryService;
	@Autowired
	public EipSpecService eipSpecService;
	@Autowired
	public EipSpecHistoryService eipSpecHistoryService;
	@Autowired
	public ElbService elbService;
	@Autowired
	public ElbHistoryService elbHistoryService;
	@Autowired
	public ElbPolicyService elbPolicyService;
	@Autowired
	public ElbPolicyHistoryService elbPolicyHistoryService;
	@Autowired
	public Es3SpecService es3SpecService;
	@Autowired
	public Es3SpecHistoryService es3SpecHistoryService;
	@Autowired
	public EsgService esgService;
	@Autowired
	public EsgHistoryService esgHistoryService;
	@Autowired
	public EsgPolicyService esgPolicyService;
	@Autowired
	public EsgPolicyHistoryService esgPolicyHistoryService;
	@Autowired
	public FimasService fimasService;
	@Autowired
	public FimasBoxService fimasBoxService;
	@Autowired
	public FimasBoxHistoryService fimasBoxHistoryService;
	@Autowired
	public FimasHistoryService fimasHistoryService;
	@Autowired
	public FimasPortService fimasPortService;
	@Autowired
	public FimasPortHistoryService fimasPortHistoryService;
	@Autowired
	public FirewallService firewallService;
	@Autowired
	public FirewallHistoryService firewallHistoryService;
	@Autowired
	public FirewallPortService firewallPortService;
	@Autowired
	public FirewallPortHistoryService firewallPortHistoryService;
	@Autowired
	public GroupPolicyService groupPolicyService;
	@Autowired
	public GroupPolicyHistoryService groupPolicyHistoryService;
	@Autowired
	public HardDiskService hardDiskService;
	@Autowired
	public HardDiskHistoryService hardDiskHistoryService;
	@Autowired
	public IdcService idcService;
	@Autowired
	public IdcHistoryService idcHistoryService;
	@Autowired
	public IpaddressService ipaddressService;
	@Autowired
	public IpaddressHistoryService ipaddressHistoryService;
	@Autowired
	public LoadBalancerService loadBalancerService;
	@Autowired
	public LoadBalancerHistoryService loadBalancerHistoryService;
	@Autowired
	public LoadBalancerPortService loadBalancerPortService;
	@Autowired
	public LoadBalancerPortHistoryService loadBalancerPortHistoryService;
	@Autowired
	public MemoryService memoryService;
	@Autowired
	public MemoryHistoryService memoryHistoryService;
	@Autowired
	public NetappBoxService netappBoxService;
	@Autowired
	public NetappBoxHistoryService netappBoxHistoryService;
	@Autowired
	public NetappControllerService netappControllerService;
	@Autowired
	public NetappControllerHistoryService netappControllerHistoryService;
	@Autowired
	public NetappPortService netappPortService;
	@Autowired
	public NetappPortHistoryService netappPortHistoryService;
	@Autowired
	public NicService nicService;
	@Autowired
	public NicHistoryService nicHistoryService;
	@Autowired
	public NicPortService nicPortService;
	@Autowired
	public NicPortHistoryService nicPortHistoryService;
	@Autowired
	public RackService rackService;
	@Autowired
	public RackHistoryService rackHistoryService;
	@Autowired
	public ServerService serverService;
	@Autowired
	public ServerHistoryService serverHistoryService;
	@Autowired
	public ServerPortService serverPortService;
	@Autowired
	public ServerPortHistoryService serverPortHistoryService;
	@Autowired
	public SwitchesService switchesService;
	@Autowired
	public SwitchesHistoryService switchesHistoryService;
	@Autowired
	public SwitchPortService switchPortService;
	@Autowired
	public SwitchPortHistoryService switchPortHistoryService;
	@Autowired
	public TagService tagService;
	@Autowired
	public TagHistoryService tagHistoryService;
	@Autowired
	public TenantsService tenantsService;
	@Autowired
	public TenantsHistoryService tenantsHistoryService;
	@Autowired
	public VlanService vlanService;
	@Autowired
	public VlanHistoryService vlanHistoryService;
	@Autowired
	public VpnService vpnService;
	@Autowired
	public VpnHistoryService vpnHistoryService;

}
