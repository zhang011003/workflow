package com.misrobot.workflow.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.misrobot.workflow.model.WfSystemInfo;
import com.misrobot.workflow.repository.WfSystemInfoRepository;
import com.misrobot.workflow.service.SystemInfoService;

@Service
public class SystemInfoServiceImpl implements SystemInfoService {

	@Autowired
	private WfSystemInfoRepository repository;
	
	@Override
	public Optional<WfSystemInfo> getSystemInfoBySystemCode(String systemCode) {
		return repository.findById(systemCode);
	}
}
