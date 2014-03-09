package no.snms.dao;

import java.util.Date;

public class Jumma {
	int fromMonth;
	int toMonth;
	int fromDay;
	int toDay;
	Date updated;
	int hours; 
	int minuttes;
	
	
	public int getFromMonth() {
		return fromMonth;
	}
	public void setFromMonth(int fromMonth) {
		this.fromMonth = fromMonth;
	}
	public int getToMonth() {
		return toMonth;
	}
	public void setToMonth(int toMonth) {
		this.toMonth = toMonth;
	}
	public int getFromDay() {
		return fromDay;
	}
	public void setFromDay(int fromDay) {
		this.fromDay = fromDay;
	}
	public int getToDay() {
		return toDay;
	}
	public void setToDay(int toDay) {
		this.toDay = toDay;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getMinuttes() {
		return minuttes;
	}
	public void setMinuttes(int minuttes) {
		this.minuttes = minuttes;
	} 
	




	
}
