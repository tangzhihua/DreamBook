package cn.retech.my_domainbean_engine.http_engine;

import cn.retech.my_domainbean_engine.http_engine.concrete.HttpEngineForHttpClient;

/*
 * Every method of java will have a stack, and every invokation on that method will have it's own 'stack frame'. 
 * 每一个java方法都有一个堆栈, 而且对那个方法每次调用时, 都会有属于它自己的 'stack frame'.
 * So the local data of one method invokation will not affect others. 
 * 所以一个方法被调用时的本地数据, 不会影响到别人.
 * Please do not confuse 'synchronization' with 'atomic'. 
 * 请不要混淆 '同步' 和 '原子性'
 * If one static method is synchronized, JVM will use the Class as the lock. If not, it acts as an instance method.
 * 如果一个静态方法是同步的, JVM会使用类锁. 如果不, 它会被当成一个实力方法.
 * */
public enum HttpEngineFactoryMethodSingleton implements IHttpEngineFactoryMethod {
	getInstance;

	@Override
	public IHttpEngine getHttpEngine() {
		return new HttpEngineForHttpClient();
	}

}
