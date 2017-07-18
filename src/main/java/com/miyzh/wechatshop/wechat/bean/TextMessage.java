/**
 * 
 */
package com.miyzh.wechatshop.wechat.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.miyzh.wechatshop.wechat.util.IMessageTypeConstant;


/**
 * <pre>
 * 文本消息.
 * </pre>
 *
 * @author 张涛  2014-4-28
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class TextMessage extends AbstractMessage {
	
	/** 消息内容. */
	@XmlElement(name="Content")
	private String content;
	
	public TextMessage(){
		//发送类型为文本类型
		super.setMsgType(IMessageTypeConstant.RESP_MESSAGE_TYPE_TEXT);
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("TextMessage [Content=");
	    builder.append(content);
	    builder.append("]");
	    return builder.toString();
    }
}
