package com.misrobot.workflow.service;

import java.util.Optional;

import com.misrobot.workflow.model.WfSystemInfo;

public interface SystemInfoService {
	Optional<WfSystemInfo> getSystemInfoBySystemCode(String systemCode);
}
