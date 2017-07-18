package com.miyzh.framework.util;

import java.util.UUID;


/**
* 文件名: UUIDTools.java<br>
* 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. <br>
* 描述: UUIDTools <br>
* 修改人: guchangpeng  <br>
* 修改时间：2014-10-11 下午06:36:22 <br>
* 修改内容：新增 <br>
*/ 
public class UUIDTools {
    private static UUIDTools uuidTools = null;

    /**
     * 空构造
     */
    private UUIDTools() {

    }

    /**
     * 单例模式
     * @return
     */
    public static synchronized UUIDTools getInstance() {
        if (uuidTools == null) {
            return new UUIDTools();
        }
        return uuidTools;
    }

    /**
     * 取得UUID
     * 
     * @return UUID.tostring
     */

    public String getUUID() {
        return UUID.randomUUID().toString();
    }
}
