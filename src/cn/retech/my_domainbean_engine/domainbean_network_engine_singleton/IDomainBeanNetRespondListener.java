package cn.retech.my_domainbean_engine.domainbean_network_engine_singleton;

import cn.retech.my_domainbean_engine.net_error_handle.NetErrorBean;

public interface IDomainBeanNetRespondListener {
	public void onSuccess(final Object respondDomainBean);
	public void onFailure(final NetErrorBean error);
}
