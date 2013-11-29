package cn.retech.my_domainbean_engine.domainbean_network_engine_singleton;

import java.io.File;

import cn.retech.my_domainbean_engine.net_error_handle.NetErrorBean;

public interface IFileAsyncHttpResponseHandler {
	public void onFailure(final NetErrorBean error);

	public void onSuccess(final File file);

	public void onProgress(final long bytesWritten, final long totalSize);
}
