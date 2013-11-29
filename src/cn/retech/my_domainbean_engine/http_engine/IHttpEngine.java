package cn.retech.my_domainbean_engine.http_engine;

import java.util.Map;
import java.util.concurrent.ExecutorService;

public interface IHttpEngine {
	public ExecutorService createHttpExecutor(final String url, final Object netRequestDomainBean, final Map<String, String> headers, final Map<String, String> body, final String method, final IHttpRespondListener httpRespondListener);
}
