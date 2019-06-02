package com.shijuwenhua.signin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.shijuwenhua.signin.constant.StatusConstants;

@Entity
@Table(name="activity")
public class Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "roleSeq")  
	@TableGenerator(name = "roleSeq", allocationSize = 1, table = "seq_table", pkColumnName = "seq_id", valueColumnName = "seq_count", pkColumnValue="activity_id")
	private long id;
	
	@Column(nullable = false)
	private String creator;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String description;
	
	@Column
	private String icon;
	
	//completed activity attendance times
	@Column(nullable = false)
	private int requiredAttendTimes;
	
	@Column(nullable = false)
	private String type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return StatusConstants.ACTIVITY_ICON_LOCATION + icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getRequiredAttendTimes() {
		return requiredAttendTimes;
	}

	public void setRequiredAttendTimes(int requiredAttendTimes) {
		this.requiredAttendTimes = requiredAttendTimes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
