package com.miyzh.framework.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;

/**
 * 文件名: ListUtil.java<br>
 * 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
 * 描述: List相关工具类 <br>
 * 修改人: guchangpeng <br>
 * 修改时间：2014-12-6 下午02:45:42 <br>
 * 修改内容：新增 <br>
 */
public class ListUtil {
	/**
	 * 排序集合
	 * 
	 * @param list
	 *            要排序的list
	 * @param propertyName
	 *            属性名
	 * @param isDesc
	 *            是否降序排序 true：降序
 	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sortList(List list, String propertyName, boolean isDesc) {
		//创建一个排序规则  
		Comparator mycmp = ComparableComparator.getInstance();
		mycmp = ComparatorUtils.nullLowComparator(mycmp); // 允许null
		if (isDesc) {
			mycmp = ComparatorUtils.reversedComparator(mycmp); // 反序
		}
		Comparator cmp = new BeanComparator(propertyName, mycmp);
		Collections.sort(list, cmp);
	}
	public static void main(String[] args) {
			
	}
}
