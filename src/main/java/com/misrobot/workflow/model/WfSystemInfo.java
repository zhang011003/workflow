package com.misrobot.workflow.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the wb_system_info database table.
 * 
 */
@Entity
@Table(name="wf_system_info")
public class WfSystemInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="system_code")
	private String systemCode;

	private String description;

	private byte enabled;

	private String name;

	public WfSystemInfo() {
	}

	public String getSystemCode() {
		return this.systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte getEnabled() {
		return this.enabled;
	}
	
	public boolean isEnabled() {
		return this.enabled == 1;
	}

	public void setEnabled(byte enabled) {
		this.enabled = enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = (byte) (enabled ? 1 : 0);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}