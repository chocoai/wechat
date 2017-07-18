/**
 * <pre>
 * Copyright Digital Bay Technology Group. Co. Ltd.All Rights Reserved.
 *
 * Original Author: 孙顺博
 *
 * ChangeLog:
 * 2015-1-16 by 孙顺博 create
 * </pre>
 */
package com.miyzh.framework.base.action.request;
import com.miyzh.framework.base.bean.BaseBean;


/**
 * <pre>
 * 终端访问设备信息记录.
 * </pre>
 *
 * @author 孙顺博  2015-1-16
 */
@SuppressWarnings("serial")
public class MobileInfo extends BaseBean
{ 

	/** 访问主键. */
    private  String  callkey;		
	
	/** APP用户主键. */
    private  String  userkey;		
	
	/** 手机型号. */
    private  String  model;		
	
	/** 联网方式. */
    private  String  apn;		
	
	/** 操作系统. */
    private  String  osname;		
	
	/** 操作系统版本号. */
    private  String  osversion;		
	
	/** 操作系统语音. */
    private  String  oslanguage;		
	
	/** 移动设备标识码. */
    private  String  imei;		
	
	/** 短信中心. */
    private  String  sms;		
	
	/** 移动用户标识码. */
    private  String  imsi;		
	
	/** 屏幕长度. */
    private  String  high;		
	
	/** 屏幕宽度. */
    private  String  wide;		
	
	/** CPU型号. */
    private  String  cpumodel;		
	
	/** 访问所在区域X. */
    private  String  xlocation;		
	
	/** 访问所在区域Y. */
    private  String  ylocation;		
	
	/** 访问时间. */
    private  String  calldate;		
    
    /** The dev token. */
    private String devToken;
 
    /**
     * <pre>
     * Gets the dev token.
     * </pre>
     *
     * @return the string
     * @author 孙顺博  2015-1-16
     */
    public String getDevToken() {
		return devToken;
	}
	
	/**
	 * <pre>
	 * Sets the dev token.
	 * </pre>
	 *
	 * @param devToken the dev token
	 * @author 孙顺博  2015-1-16
	 */
	public void setDevToken(String devToken) {
		this.devToken = devToken;
	}
	
	/**
	 * <pre>
	 * Sets the callkey.
	 * </pre>
	 *
	 * @param callkey the callkey
	 * @author 孙顺博  2015-1-16
	 */
	public void setCallkey( String  callkey) 
	{
		this.callkey= callkey;
	}
	
	/**
	 * <pre>
	 * Gets the callkey.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getCallkey()
	{
		return callkey;
	}
    
    /**
     * <pre>
     * Sets the userkey.
     * </pre>
     *
     * @param userkey the userkey
     * @author 孙顺博  2015-1-16
     */
    public void setUserkey( String  userkey) 
	{
		this.userkey= userkey;
	}
	
	/**
	 * <pre>
	 * Gets the userkey.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getUserkey()
	{
		return userkey;
	}
    
    /**
     * <pre>
     * Gets the l.
     * </pre>
     *
     * @return the l
     * @author 孙顺博  2015-1-16
     */
    public String getModel() {
		return model;
	}
	
	/**
	 * <pre>
	 * Sets the l.
	 * </pre>
	 *
	 * @param model the new l
	 * @author 孙顺博  2015-1-16
	 */
	public void setModel(String model) {
		this.model = model;
	}
    
    /**
     * <pre>
     * Sets the apn.
     * </pre>
     *
     * @param apn the apn
     * @author 孙顺博  2015-1-16
     */
    public void setApn( String  apn) 
	{
		this.apn= apn;
	}
	
	/**
	 * <pre>
	 * Gets the apn.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getApn()
	{
		return apn;
	}
    
    /**
     * <pre>
     * Sets the osname.
     * </pre>
     *
     * @param osname the osname
     * @author 孙顺博  2015-1-16
     */
    public void setOsname( String  osname) 
	{
		this.osname= osname;
	}
	
	/**
	 * <pre>
	 * Gets the osname.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getOsname()
	{
		return osname;
	}
    
    /**
     * <pre>
     * Sets the osversion.
     * </pre>
     *
     * @param osversion the osversion
     * @author 孙顺博  2015-1-16
     */
    public void setOsversion( String  osversion) 
	{
		this.osversion= osversion;
	}
	
	/**
	 * <pre>
	 * Gets the osversion.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getOsversion()
	{
		return osversion;
	}
    
    /**
     * <pre>
     * Sets the oslanguage.
     * </pre>
     *
     * @param oslanguage the oslanguage
     * @author 孙顺博  2015-1-16
     */
    public void setOslanguage( String  oslanguage) 
	{
		this.oslanguage= oslanguage;
	}
	
	/**
	 * <pre>
	 * Gets the oslanguage.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getOslanguage()
	{
		return oslanguage;
	}
    
    /**
     * <pre>
     * Sets the imei.
     * </pre>
     *
     * @param imei the imei
     * @author 孙顺博  2015-1-16
     */
    public void setImei( String  imei) 
	{
		this.imei= imei;
	}
	
	/**
	 * <pre>
	 * Gets the imei.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getImei()
	{
		return imei;
	}
    
    /**
     * <pre>
     * Sets the sms.
     * </pre>
     *
     * @param sms the sms
     * @author 孙顺博  2015-1-16
     */
    public void setSms( String  sms) 
	{
		this.sms= sms;
	}
	
	/**
	 * <pre>
	 * Gets the sms.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getSms()
	{
		return sms;
	}
    
    /**
     * <pre>
     * Sets the imsi.
     * </pre>
     *
     * @param imsi the imsi
     * @author 孙顺博  2015-1-16
     */
    public void setImsi( String  imsi) 
	{
		this.imsi= imsi;
	}
	
	/**
	 * <pre>
	 * Gets the imsi.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getImsi()
	{
		return imsi;
	}
    
    /**
     * <pre>
     * Sets the high.
     * </pre>
     *
     * @param high the high
     * @author 孙顺博  2015-1-16
     */
    public void setHigh( String  high) 
	{
		this.high= high;
	}
	
	/**
	 * <pre>
	 * Gets the high.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getHigh()
	{
		return high;
	}
    
    /**
     * <pre>
     * Sets the wide.
     * </pre>
     *
     * @param wide the wide
     * @author 孙顺博  2015-1-16
     */
    public void setWide( String  wide) 
	{
		this.wide= wide;
	}
	
	/**
	 * <pre>
	 * Gets the wide.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getWide()
	{
		return wide;
	}
    
    /**
     * <pre>
     * Sets the cpumodel.
     * </pre>
     *
     * @param cpumodel the cpumodel
     * @author 孙顺博  2015-1-16
     */
    public void setCpumodel( String  cpumodel) 
	{
		this.cpumodel= cpumodel;
	}
	
	/**
	 * <pre>
	 * Gets the cpumodel.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getCpumodel()
	{
		return cpumodel;
	}
    
    /**
     * <pre>
     * Sets the xlocation.
     * </pre>
     *
     * @param xlocation the xlocation
     * @author 孙顺博  2015-1-16
     */
    public void setXlocation( String  xlocation) 
	{
		this.xlocation= xlocation;
	}
	
	/**
	 * <pre>
	 * Gets the xlocation.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getXlocation()
	{
		return xlocation;
	}
    
    /**
     * <pre>
     * Sets the ylocation.
     * </pre>
     *
     * @param ylocation the ylocation
     * @author 孙顺博  2015-1-16
     */
    public void setYlocation( String  ylocation) 
	{
		this.ylocation= ylocation;
	}
	
	/**
	 * <pre>
	 * Gets the ylocation.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getYlocation()
	{
		return ylocation;
	}
    
    /**
     * <pre>
     * Sets the calldate.
     * </pre>
     *
     * @param calldate the calldate
     * @author 孙顺博  2015-1-16
     */
    public void setCalldate( String  calldate) 
	{
		this.calldate= calldate;
	}
	
	/**
	 * <pre>
	 * Gets the calldate.
	 * </pre>
	 *
	 * @return the string
	 * @author 孙顺博  2015-1-16
	 */
	public  String  getCalldate()
	{
		return calldate;
	}
}
