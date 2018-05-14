package com.timer;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Timer {
	@Id
	private Long entryId;
	private String userId;
	private Long inTime;
	private Long outTime;
	private Boolean completed;
	public Timer(String userId, Long inTime, Long outTime, Long entryId, Boolean completed) {
		this.userId = userId;
		this.inTime = inTime;
		this.outTime = outTime;
		this.entryId = entryId;
		this.completed = completed;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getInTime() {
		return inTime;
	}
	public void setInTime(Long inTime) {
		this.inTime = inTime;
	}
	public Long getOutTime() {
		return outTime;
	}
	public void setOutTime(Long outTime) {
		this.outTime = outTime;
	}
	public Long getEntryId() {
		return entryId;
	}
	public void setEntryId(Long entryId) {
		this.entryId = entryId;
	}
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	
	
}
