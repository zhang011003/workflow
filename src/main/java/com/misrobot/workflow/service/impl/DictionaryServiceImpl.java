package com.misrobot.workflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.misrobot.workflow.model.WfDictionary;
import com.misrobot.workflow.repository.WfDictionaryRepository;
import com.misrobot.workflow.service.DictionaryService;

@Service
public class DictionaryServiceImpl implements DictionaryService {
	
	@Autowired
	private WfDictionaryRepository dictionaryRepository;
	
	@Override
	public List<WfDictionary> getDictionariesByType(String type) {
		return dictionaryRepository.findByTypeLike("%" + type + "%");
	}

}
