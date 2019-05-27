package com.shijuwenhua.signin.model;

public class ActivityDto {
	
	private long id;
	private String creator;
	private String title;
	private String description;
	private String icon;
	private int requiredAttendTimes;
	private String type;
	private String status;
	private int attendTimes;

	public ActivityDto(Activity activity, String status, int attendTimes) {
		this.id = activity.getId();
		this.creator = activity.getCreator();
		this.title = activity.getTitle();
		this.description = activity.getDescription();
		this.icon = activity.getIcon();
		this.requiredAttendTimes = activity.getRequiredAttendTimes();
		this.type = activity.getType();
		this.status = status;
		this.attendTimes = attendTimes;
	}
	
	public ActivityDto(Activity activity) {
		this.id = activity.getId();
		this.creator = activity.getCreator();
		this.title = activity.getTitle();
		this.description = activity.getDescription();
		this.icon = activity.getIcon();
		this.requiredAttendTimes = activity.getRequiredAttendTimes();
		this.type = activity.getType();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAttendTimes() {
		return attendTimes;
	}

	public void setAttendTimes(int attendTimes) {
		this.attendTimes = attendTimes;
	}
}
