package com.miyzh.wechatshop.wechat.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.miyzh.wechatshop.wechat.util.IMessageTypeConstant;



/**
 * <pre>
 * 图文类型消息.
 * </pre>
 *
 * @author 张涛  2014-4-28
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class NewsMessage extends AbstractMessage {

	/** 图文总数. */
	@XmlElement(name="ArticleCount")
	private String articleCount;
	
	/** 图文内容. */
	@XmlElementWrapper(name="Articles")
	private List<NewsItem> item;

	/**
	 * Instantiates a new news message.
	 *
	 * @author 张涛  2014-4-28
	 */
	public NewsMessage() {
		// 发送类型为图文类型
		super.setMsgType(IMessageTypeConstant.RESP_MESSAGE_TYPE_NEWS);
	}

	/**
	 * <pre>
	 * Gets the article count.
	 * </pre>
	 *
	 * @return the string
	 * @author 张涛  2014-4-28
	 */
	public String getArticleCount() {
		return articleCount;
	}

	/**
	 * <pre>
	 * Sets the article count.
	 * </pre>
	 *
	 * @param articleCount the article count
	 * @author 张涛  2014-4-28
	 */
	public void setArticleCount(String articleCount) {
		this.articleCount = articleCount;
	}

//	public NewsArticles getArticles() {
//		return articles;
//	}
//
//	public void setArticles(NewsArticles articles) {
//		this.articles = articles;
//	}

	public List<NewsItem> getItem() {
		return item;
	}

	public void setItem(List<NewsItem> item) {
		this.item = item;
	}

}
