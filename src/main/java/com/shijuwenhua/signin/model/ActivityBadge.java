package com.shijuwenhua.signin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table
public class ActivityBadge {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "roleSeq")  
	@TableGenerator(name = "roleSeq", allocationSize = 1, table = "seq_table", pkColumnName = "seq_id", valueColumnName = "seq_count", pkColumnValue="activity_badge_id")
	private long id;
	
	@Column(nullable = false)
	private long badgeId;
	
	@Column(nullable = false)
	private long activityId;
	
	//completed badge completed attend activities
	@Column(nullable = false)
	private int requiredAttendTimes;
	
	@Column(nullable = false)
	private String requiredActivity;

	public long getBadgeId() {
		return badgeId;
	}

	public void setBadgeId(long badgeId) {
		this.badgeId = badgeId;
	}

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRequiredActivity() {
		return requiredActivity;
	}

	public void setRequiredActivity(String requriredActivity) {
		this.requiredActivity = requriredActivity;
	}

	public int getRequiredAttendTimes() {
		return requiredAttendTimes;
	}

	public void setRequiredAttendTimes(int requiredAttendTimes) {
		this.requiredAttendTimes = requiredAttendTimes;
	}

}
