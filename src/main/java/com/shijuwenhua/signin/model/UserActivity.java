package com.shijuwenhua.signin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

@Entity
@Table
public class UserActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "roleSeq")  
	@TableGenerator(name = "roleSeq", allocationSize = 1, table = "seq_table", pkColumnName = "seq_id", valueColumnName = "seq_count", pkColumnValue="user_activity_id")
	private long id;
	
	@Column(nullable = false)
	private String userOpenId;
	
	@Column(nullable = false)
	private long activityId;
	
	@Column(nullable = false)
	private int attendTimes;
	
	@Column(nullable = false)
	private String status;
	
	@Version
	private long version;
	
	@Column
	private String achievementTime;
	
	@Column
	private String comments;
	
	@Column
	private Integer commonTotalAttend;

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserOpenId() {
		return userOpenId;
	}

	public void setUserOpenId(String userOpenId) {
		this.userOpenId = userOpenId;
	}

	public int getAttendTimes() {
		return attendTimes;
	}

	public void setAttendTimes(int attendTimes) {
		this.attendTimes = attendTimes;
	}

	public String getAchievementTime() {
		return achievementTime;
	}

	public void setAchievementTime(String achievementTime) {
		this.achievementTime = achievementTime;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getCommonTotalAttend() {
		return commonTotalAttend;
	}

	public void setCommonTotalAttend(Integer commonTotalAttend) {
		this.commonTotalAttend = commonTotalAttend;
	}

}
