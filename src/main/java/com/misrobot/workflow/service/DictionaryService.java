package com.misrobot.workflow.service;

import java.util.List;

import com.misrobot.workflow.model.WfDictionary;

public interface DictionaryService {

	List<WfDictionary> getDictionariesByType(String type);

}
