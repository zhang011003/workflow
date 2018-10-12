package com.misrobot.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.misrobot.workflow.model.WfSystemInfo;

public interface WfSystemInfoRepository extends JpaRepository<WfSystemInfo, String>{

}
