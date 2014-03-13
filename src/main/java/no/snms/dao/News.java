package no.snms.dao;

import java.util.Date;

public class News {
	
	String title;
	String text;
	Date createdDate;
	String ingress; 
	String imageText;
	String author; 
	String imgUrl; 
	String _id; 
	Integer cat; 
	Integer pri;
	
	public Integer getCat() {
		return cat;
	}

	public void setCat(Integer cat) {
		this.cat = cat;
	}

	public String getIngress() {
		return ingress;
	}
	
	public void setIngress(String ingress) {
		this.ingress = ingress;
	}
	
	public String getImageText() {
		return imageText;
	}
	
	public void setImageText(String imageText) {
		this.imageText = imageText;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imageUrl) {
		this.imgUrl = imageUrl;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getPri() {
		return pri;
	}

	public void setPri(Integer pri) {
		this.pri = pri;
	}
	
	
	
}
