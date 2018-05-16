package com.timer;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Timer {
	@Id
	private Long id;
	@Index
	private Long userId;
	private Long inTime;
	private Long outTime;
	private Boolean completed;
	public Timer(Long userId, Long inTime) {
		this.userId = userId;
		this.inTime = inTime;
		this.outTime = null;
		this.completed=false;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
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
	public Long getId() {
		return id;
	}
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	
	
}
