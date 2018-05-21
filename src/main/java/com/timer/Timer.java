package com.timer;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Timer {
	 public static SimpleDateFormat day=new SimpleDateFormat("EEE MMM dd");
	 public static SimpleDateFormat time=new SimpleDateFormat("hh:mm:ss a");
	 
	 @Ignore
	 TimeZone tz = TimeZone.getDefault();
		
	 
	@Id
	private Long id;
	@Index
	private Long userId;
	@Index
	private Long inTime;
	@Index
	private Long outTime;
	@Index
	private Boolean completed;
	@Ignore
	private String days;
	@Ignore
	private String intimeday;
	@Ignore
	private String outtimeday;
	@Ignore
	 private String dayformat;
	
	public Timer() {}
	public Timer(Long userId, Long inTime) {
		this.userId = userId;
		this.inTime = inTime;
		this.completed=false;
		//this.intimeday=time.format(inTime);
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
	public String getintimeday()
	{
	   time.setTimeZone(tz);
	   return this.intimeday=time.format(this.inTime);  	  
	}
	public String getouttimeday()
	{
		if(this.outTime != null)
		{
		time.setTimeZone(tz);
		return this.outtimeday=time.format(this.outTime); 
		}
		else
			return this.outtimeday;
	}
	public String getdayformat() {
		day.setTimeZone(tz);
		return this.dayformat=day.format(this.inTime); 
		
	}
	
}
