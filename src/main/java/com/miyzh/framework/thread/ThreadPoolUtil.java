package com.miyzh.framework.thread;

/**
 * 

 <pre>
 * Copyright Digital Bay Technology Group. Co. Ltd.All Rights Reserved.
 *
 * Original Author: zhangtao
 *
 * ChangeLog:
 * 2015-9-25 by zhangtao create
 * </pre>
 */

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.miyzh.wechatshop.demothread.thread.DemoThread;

/**
 * <pre>
 * the ThreadPoolUtil class for
 * 异步线程池util
 * </pre>
 *
 * @author zhangtao 2016年7月25日
 */
public class ThreadPoolUtil implements Runnable{

	/** 线程池大小 *. */
	private final int THREAD_POOL_NUM = 200;

	/** The exe. */
	ExecutorService exe = Executors.newFixedThreadPool(THREAD_POOL_NUM);

	/** The asynchronous thread pool util. */
	private static ThreadPoolUtil asynchronousThreadPoolUtil = null;

	/**
	 * Instantiates a new thread pool util.
	 * 
	 * @author zhangtao 2015-9-25
	 */
	private ThreadPoolUtil() {

	}

	/**
	 * <pre>
	 * Gets the single instance of ThreadPoolUtil.
	 * </pre>
	 * 
	 * @author zhangtao 2015-9-25
	 * @return single instance of ThreadPoolUtil
	 */
	public static ThreadPoolUtil getInstance() {
		if (null == asynchronousThreadPoolUtil) {
			asynchronousThreadPoolUtil = new ThreadPoolUtil();
		}
		return asynchronousThreadPoolUtil;
	}

	/**
	 * <pre>
	 * 添加单个线程
	 * </pre>
	 * 
	 * @author zhangtao 2015-9-25
	 * @param waitAddThreadCont
	 *            the wait add thread cont
	 * @param aThreadList
	 *            the List Thread
	 */
	public void addThread(ThreadPoolUtil virtualMemberThread) {
		exe.execute(virtualMemberThread);
	}

	/**
	 * <pre>
	 * 批量添加多个线程
	 * </pre>
	 * 
	 * @author zhangtao 2015-9-25
	 * @param waitAddThreadCont
	 *            the wait add thread cont
	 * @param aThreadList
	 *            the List Thread
	 */
	public void addThreads(List<DemoThread> aThreadList) {
		for (DemoThread virtualMemberThread : aThreadList) {
			exe.execute(virtualMemberThread);
		}
	}

	/**
	 * <pre>
	 * Checks if is terminated.
	 * </pre>
	 * 
	 * @author zhangtao 2015-9-25
	 * @return true[说明线程池中所有线程都执行完成], if is terminated
	 */
	public boolean isTerminated() {
		boolean flag = false;
		try {
			while (true) {
				if (exe.isTerminated()) {
					flag = true;
					break;
				}
				Thread.sleep(200);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
