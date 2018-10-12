package com.misrobot.workflow.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the wf_systeminfo_tree database table.
 * 
 */
@Entity
@Table(name="wf_systeminfo_tree")
public class WfSysteminfoTree implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	//bi-directional many-to-one association to WbSystemInfo
	@ManyToOne
	@JoinColumn(name="systeminfo_id")
	private WfSystemInfo wbSystemInfo;

	//bi-directional many-to-one association to WfSystemTree
	@ManyToOne
	@JoinColumn(name="systemtree_id")
	private WfSystemTree wfSystemTree;

	public WfSysteminfoTree() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public WfSystemInfo getWbSystemInfo() {
		return this.wbSystemInfo;
	}

	public void setWbSystemInfo(WfSystemInfo wbSystemInfo) {
		this.wbSystemInfo = wbSystemInfo;
	}

	public WfSystemTree getWfSystemTree() {
		return this.wfSystemTree;
	}

	public void setWfSystemTree(WfSystemTree wfSystemTree) {
		this.wfSystemTree = wfSystemTree;
	}

}