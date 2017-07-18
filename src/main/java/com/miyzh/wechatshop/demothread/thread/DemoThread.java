package com.miyzh.wechatshop.demothread.thread;

import org.apache.log4j.Logger;

import com.miyzh.wechatshop.demothread.service.IDemoThreadService;

public class DemoThread implements Runnable{
	private Logger log = Logger.getLogger(this.getClass());
	private int sendType;
	/** object数组 **/
	private Object[] objectArr;
	
	public DemoThread(int sendType, Object[] objectArr) {
		this.sendType = sendType;
		this.objectArr = objectArr;
	}
	
	
	@Override
	public void run() {
		try {
			switch (sendType) {

			case 1:
				doSomething();
				break;
			default:
				break;
			}

		} catch (Exception e) {
			log.error("异步调用错误!", e);
		}
	}
	
	private void doSomething(){
		if (null != objectArr && objectArr.length > 2) {
			IDemoThreadService marketCog = (IDemoThreadService) objectArr[1];
			marketCog.handler((String)objectArr[2]);
		}
	}
}
