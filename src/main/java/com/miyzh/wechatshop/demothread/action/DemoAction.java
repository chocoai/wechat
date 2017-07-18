package com.miyzh.wechatshop.demothread.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.miyzh.framework.thread.ThreadPoolUtil;
import com.miyzh.wechatshop.demothread.service.IDemoThreadService;
import com.miyzh.wechatshop.demothread.thread.DemoThread;

/**
 * <pre>
 * the DemoAction class for
 * </pre>
 *
 * @author zhangtao 2016年7月25日
 */
public class DemoAction {
	
	@Autowired
	private IDemoThreadService service;
	
	public String doIndex(){
		
		List<DemoThread> threadList = new ArrayList<DemoThread>();
		
		//封装线程池参数
		Object objectArr[] = new Object[] { service,"param"};
		threadList.add(new DemoThread(1, objectArr));

		//向线程池增加线程对象,并启动线程.
		if (!threadList.isEmpty()) {
			ThreadPoolUtil.getInstance().addThreads(threadList);
		}
		
		return null;
	}

}
