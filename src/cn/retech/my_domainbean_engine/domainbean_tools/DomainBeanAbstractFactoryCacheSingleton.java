package cn.retech.my_domainbean_engine.domainbean_tools;

import java.util.HashMap;
import java.util.Map;

import android.text.TextUtils;
import cn.retech.domainbean_model.DomainBeanHelperClassNameMapping;

/**
 * 获取目标业务Bean, 对应的Tools 抽象工厂对象.
 * 
 * 
 */
public enum DomainBeanAbstractFactoryCacheSingleton {
	getInstance;

	/**
	 * 
	 */
	private DomainBeanHelperClassNameMapping strategyClassNameMapping = new DomainBeanHelperClassNameMapping();
	/**
	 * 缓存助手策略算法对象的集合
	 */
	private Map<String, IDomainBeanAbstractFactory> abstractFactoryObjectBufList = new HashMap<String, IDomainBeanAbstractFactory>(100);

	/**
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public IDomainBeanAbstractFactory getDomainBeanAbstractFactoryObjectByKey(String key) throws Exception {

		if (TextUtils.isEmpty(key)) {
			throw new NullPointerException("key is empty!");
		}

		IDomainBeanAbstractFactory abstractFactoryObject = abstractFactoryObjectBufList.get(key);
		if (null == abstractFactoryObject) {
			String className = strategyClassNameMapping.getTargetClassNameForKey(key);
			if (TextUtils.isEmpty(className)) {
				// 找不到对应的算法类
				throw new IllegalStateException("key is invalid!");
			}

			@SuppressWarnings("rawtypes")
			Class cl = Class.forName(className);
			abstractFactoryObject = (IDomainBeanAbstractFactory) cl.newInstance();
			abstractFactoryObjectBufList.put(key, abstractFactoryObject);
		}
		return abstractFactoryObject;
	}
}
