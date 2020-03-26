package com.douzone.jblog.vo;

public class BlogVo {
	private String blogID;
	private String title;
	private String logoURL;
	
	public String getBlogID() {
		return blogID;
	}
	public void setBlogID(String blogID) {
		this.blogID = blogID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLogoURL() {
		return logoURL;
	}
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}
	
	@Override
	public String toString() {
		return "BlogVo [blogID=" + blogID + ", title=" + title + ", logoURL=" + logoURL + "]";
	}
	
}
