package com.miyzh.framework.datadic.bean;
import com.miyzh.framework.base.bean.BaseBean;

/**
* 文件名: SysDicCategroyInfo.java<br>
* 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved.<br> 
* 描述: 数据字典类型信息<br>
* 修改人: guchangpeng<br>
* 修改时间：2014-10-21 10:39:16<br>
* 修改内容：新增<br>
*/
public class SysDicCategroyInfo extends BaseBean
{ 

	/** **/
	private static final long serialVersionUID = 1L;
	/**字典类型主键 */
    private  String  categoryKey;		
	/**类型名称 */
    private  String  categoryName;		
	/**类型编码 */
    private  String  categoryCode;		
	/**类型 */
    private  String  dicType;		
	/**使用者(调用者) */
    private int invoker;		
	/**说明 */
    private  String  categoryExplain;		
	/**排序号 */
    private int sequenceNum;		
	/**删除标识 */
    private  String  deleteFlag;		
	/**创建时间 */
    private  String  createTime;		
	/**创建人 */
    private  String  createUser;		
	/**修改时间 */
    private  String  updateTime;		
	/**修改人 */
    private  String  updateUser;		
 
 
    public void setCategoryKey( String  categoryKey) 
	{
		this.categoryKey= categoryKey;
	}
	public  String  getCategoryKey()
	{
		return categoryKey;
	}
    public void setCategoryName( String  categoryName) 
	{
		this.categoryName= categoryName;
	}
	public  String  getCategoryName()
	{
		return categoryName;
	}
    public void setCategoryCode( String  categoryCode) 
	{
		this.categoryCode= categoryCode;
	}
	public  String  getCategoryCode()
	{
		return categoryCode;
	}
    public void setDicType( String  dicType) 
	{
		this.dicType= dicType;
	}
	public  String  getDicType()
	{
		return dicType;
	}
    public void setInvoker(int invoker) 
	{
		this.invoker= invoker;
	}
	public int getInvoker()
	{
		return invoker;
	}
    public void setCategoryExplain( String  categoryExplain) 
	{
		this.categoryExplain= categoryExplain;
	}
	public  String  getCategoryExplain()
	{
		return categoryExplain;
	}
    public void setSequenceNum(int sequenceNum) 
	{
		this.sequenceNum= sequenceNum;
	}
	public int getSequenceNum()
	{
		return sequenceNum;
	}
    public void setDeleteFlag( String  deleteFlag) 
	{
		this.deleteFlag= deleteFlag;
	}
	public  String  getDeleteFlag()
	{
		return deleteFlag;
	}
    public void setCreateTime( String  createTime) 
	{
		this.createTime= createTime;
	}
	public  String  getCreateTime()
	{
		return createTime;
	}
    public void setCreateUser( String  createUser) 
	{
		this.createUser= createUser;
	}
	public  String  getCreateUser()
	{
		return createUser;
	}
    public void setUpdateTime( String  updateTime) 
	{
		this.updateTime= updateTime;
	}
	public  String  getUpdateTime()
	{
		return updateTime;
	}
    public void setUpdateUser( String  updateUser) 
	{
		this.updateUser= updateUser;
	}
	public  String  getUpdateUser()
	{
		return updateUser;
	}
}
