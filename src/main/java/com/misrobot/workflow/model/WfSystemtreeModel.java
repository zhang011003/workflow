package com.misrobot.workflow.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the wf_systemtree_model database table.
 * 
 */
@Entity
@Table(name="wf_systemtree_model")
public class WfSystemtreeModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="model_id")
	private String modelId;

	//bi-directional many-to-one association to WfSystemTree
	@ManyToOne
	@JoinColumn(name="systemtree_id")
	private WfSystemTree wfSystemTree;

	public WfSystemtreeModel() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelId() {
		return this.modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public WfSystemTree getWfSystemTree() {
		return this.wfSystemTree;
	}

	public void setWfSystemTree(WfSystemTree wfSystemTree) {
		this.wfSystemTree = wfSystemTree;
	}

}