package com.misrobot.workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.misrobot.workflow.model.WfDictionary;

public interface WfDictionaryRepository extends JpaRepository<WfDictionary, String>{

	List<WfDictionary> findByTypeLike(String type);
}
