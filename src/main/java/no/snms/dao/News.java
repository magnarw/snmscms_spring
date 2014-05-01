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
	String address;
	Date from;
	Date to;
	String articleImageUrl;
	public String getArticleImageText() {
		return articleImageText;
	}

	public void setArticleImageText(String articleImageText) {
		this.articleImageText = articleImageText;
	}

	Float lat;
	Float lng;
	Boolean showMap;
	String articleImageText;
	
	/** These should be moved into a wrapper object.. **/
	Boolean hasMoreElements;
	Integer nextPage; 
	
	
	
	public Boolean getHasMoreElements() {
		return hasMoreElements;
	}

	public void setHasMoreElements(Boolean hasMoreElements) {
		this.hasMoreElements = hasMoreElements;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public String getArticleImageUrl() {
		return articleImageUrl;
	}

	public void setArticleImageUrl(String articleImageUrl) {
		this.articleImageUrl = articleImageUrl;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	public Boolean getShowMap() {
		return showMap;
	}

	public void setShowMap(Boolean showMap) {
		this.showMap = showMap;
	}


	
	
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
