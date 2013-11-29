package cn.retech.my_domainbean_engine.http_engine;

import java.util.concurrent.ExecutorService;

import cn.retech.my_domainbean_engine.net_error_handle.NetErrorBean;

public interface IHttpRespondListener {
	public void onCompletion(final ExecutorService executor, final byte[] responseData);
	public void onError(final ExecutorService executor, final NetErrorBean error);
}
