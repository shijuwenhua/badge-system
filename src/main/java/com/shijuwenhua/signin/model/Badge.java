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
public class Badge {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "roleSeq")  
	@TableGenerator(name = "roleSeq", allocationSize = 1, table = "seq_table", pkColumnName = "seq_id", valueColumnName = "seq_count", pkColumnValue="badge_id")  
	private long id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	@Column
	private String icon;
	@Column(nullable = false)
	private int upgradeRequiredTime;
	@Column
	private Long upgradeBadgeId;
	@Column
	private String upgradeBadgeTitle;
	

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

	public int getUpgradeRequiredTime() {
		return upgradeRequiredTime;
	}

	public void setUpgradeRequiredTime(int upgradeRequiredTime) {
		this.upgradeRequiredTime = upgradeRequiredTime;
	}

	public Long getUpgradeBadgeId() {
		return upgradeBadgeId;
	}

	public void setUpgradeBadgeId(Long upgradeBadgeId) {
		this.upgradeBadgeId = upgradeBadgeId;
	}

	public String getUpgradeBadgeTitle() {
		return upgradeBadgeTitle;
	}

	public void setUpgradeBadgeTitle(String upgradeBadgeTitle) {
		this.upgradeBadgeTitle = upgradeBadgeTitle;
	}

}
