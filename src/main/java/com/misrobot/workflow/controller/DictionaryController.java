package com.misrobot.workflow.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.misrobot.workflow.controller.dto.WfDictionaryResp;
import com.misrobot.workflow.model.WfDictionary;
import com.misrobot.workflow.service.DictionaryService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("dict")
public class DictionaryController {
	
	@Autowired
	private DictionaryService dictionaryService;
	
	@ApiOperation(value = "通过type模糊查询字典信息", response = List.class)
	@GetMapping("/type/{type}")
	public List<WfDictionaryResp> getDictionariesByType(@PathVariable @NotNull String type) {
		List<WfDictionary> dictionaries = dictionaryService.getDictionariesByType(type);
		List<WfDictionaryResp> dictionaryResps = Lists.newArrayListWithCapacity(dictionaries.size());
		for (WfDictionary dictionary : dictionaries) {
			WfDictionaryResp resp = new WfDictionaryResp();
			resp.setId(dictionary.getValue());
			resp.setName(dictionary.getName());
			dictionaryResps.add(resp);
		}
		return dictionaryResps;
	}
}
