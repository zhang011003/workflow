package com.misrobot.workflow.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the wf_system_tree database table.
 * 
 */
@Entity
@Table(name="wf_system_tree")
public class WfSystemTree implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String name;

	@Column(name="parent_id")
	private String parentId;

	//bi-directional many-to-one association to WfSysteminfoTree
	@OneToMany(mappedBy="wfSystemTree")
	private List<WfSysteminfoTree> wfSysteminfoTrees;

	//bi-directional many-to-one association to WfSystemtreeModel
	@OneToMany(mappedBy="wfSystemTree")
	private List<WfSystemtreeModel> wfSystemtreeModels;

	public WfSystemTree() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<WfSysteminfoTree> getWfSysteminfoTrees() {
		return this.wfSysteminfoTrees;
	}

	public void setWfSysteminfoTrees(List<WfSysteminfoTree> wfSysteminfoTrees) {
		this.wfSysteminfoTrees = wfSysteminfoTrees;
	}

	public WfSysteminfoTree addWfSysteminfoTree(WfSysteminfoTree wfSysteminfoTree) {
		getWfSysteminfoTrees().add(wfSysteminfoTree);
		wfSysteminfoTree.setWfSystemTree(this);

		return wfSysteminfoTree;
	}

	public WfSysteminfoTree removeWfSysteminfoTree(WfSysteminfoTree wfSysteminfoTree) {
		getWfSysteminfoTrees().remove(wfSysteminfoTree);
		wfSysteminfoTree.setWfSystemTree(null);

		return wfSysteminfoTree;
	}

	public List<WfSystemtreeModel> getWfSystemtreeModels() {
		return this.wfSystemtreeModels;
	}

	public void setWfSystemtreeModels(List<WfSystemtreeModel> wfSystemtreeModels) {
		this.wfSystemtreeModels = wfSystemtreeModels;
	}

	public WfSystemtreeModel addWfSystemtreeModel(WfSystemtreeModel wfSystemtreeModel) {
		getWfSystemtreeModels().add(wfSystemtreeModel);
		wfSystemtreeModel.setWfSystemTree(this);

		return wfSystemtreeModel;
	}

	public WfSystemtreeModel removeWfSystemtreeModel(WfSystemtreeModel wfSystemtreeModel) {
		getWfSystemtreeModels().remove(wfSystemtreeModel);
		wfSystemtreeModel.setWfSystemTree(null);

		return wfSystemtreeModel;
	}

}