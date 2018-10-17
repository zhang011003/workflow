package com.misrobot.workflow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.misrobot.workflow.model.WfTaskwithdrawSetting;

public interface WfTaskwithdrawSettingRepository extends JpaRepository<WfTaskwithdrawSetting, String>{

	Optional<WfTaskwithdrawSetting> findByProcessDefinitionId(String id);

}
