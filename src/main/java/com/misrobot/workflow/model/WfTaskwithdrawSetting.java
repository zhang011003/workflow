package com.misrobot.workflow.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the wf_taskwithdraw_setting database table.
 * 
 */
@Entity
@Table(name="wf_taskwithdraw_setting")
public class WfTaskwithdrawSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="node_name")
	private String nodeName;

	@Column(name="process_definition_id")
	private String processDefinitionId;

	private byte withdraw;

	@Column(name="withdraw_type")
	private String withdrawType;

	public WfTaskwithdrawSetting() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getProcessDefinitionId() {
		return this.processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public byte getWithdraw() {
		return this.withdraw;
	}

	public void setWithdraw(byte withdraw) {
		this.withdraw = withdraw;
	}

	public String getWithdrawType() {
		return this.withdrawType;
	}

	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
	}

}