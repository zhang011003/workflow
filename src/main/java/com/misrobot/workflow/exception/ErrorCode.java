package com.misrobot.workflow.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.misrobot.workflow.common.SpringUtils;

/**
 * 0开头的为成功,3开头的为失败
 * 
 * @author mis-8
 *
 */
public enum ErrorCode {
	// 业务成功
	SUCCESS("0"),
	// 参数错误
	PARAM_ERROR("4000000000000000"),
//	// 参数{0}不能为空
//	PARAM_CANT_EMPTY("4000000000000000"),
	// 参数{0}不能是数字
	PARAM_CANT_NUMBER("4000000000000001"),
	// 根据唯一标识{0}找不到流程实例
	CANNOT_FIND_PROCESS_INSTANCE("4000000000000002"),
	// 系统异常
	SYSTEM_ERROR("4000000000000003"),
	// 数据库异常
	DATABASE_ERROR("4000000000000004"),
	//根据路径[{0}]找不到文件
	FILE_NOT_FOUND("4000000000000005"),		
	//加载[{0}]文件失败
	LOAD_FILE_FALSE("4000000000000006"),		
	//HTTP请求到{0}失败:{1}
	HTTP_REQUEST_FALSE("4000000000000007"),	
	//JSON转换为对象失败:{0}
	JSON_STR_2_OBJECT_FALSE("4000000000000008"),
	//对象{0}转换为JSON失败:{1}
	OBJECT_2_JSON_STR_FALSE("4000000000000009"),
	// 还原图片异常:{0}
	IMAGE_STR_2_IMAGE_ERROR("4000000000000010"),
	// 任务{0}没有到流程分支{1}
	NOT_FOUND_SEQUENCEFLOW("4000000000000011"),
	// 已经是第一个环节,无法再回退
	CANT_OPERATE_FIRST_NODE("4000000000000012"),
	// 流程{0}未找到
	FLOW_NOT_EXIST("4000000000000013"),
	// 机构{0}未配置服务{1}
	SERVER_NOT_FOUND("4000000000000014"),
	// 机构编码orgCode不能为空,请从URL传参或报文传参
	ORG_CANT_EMPTY("4000000000000015"),
	//用户未登录
	USER_NO_LOGIN("4000000000000016"),	
	//会话编号不能为空
	SESSIONID_CANT_EMPTY("4000000000000017"),	
	//会话{0}不存在,或已过期
	SESSIONID_NOT_EXIST("4000000000000018"),	
	//空指针异常
	NULL_POINT_EXCEPTION("4000000000000019"),	
	//本地文件不存在
	LOCAL_FILE_NOT_EXIST("4000000000000020"),	
	//服务类型未定义
	SERVER_TYPE_NOT_DEFIND("4000000000000021"),
	// 根据唯一标识{0}找不到任务记录
	CANNOT_FIND_TASK_RECORD("4000000000000022"),
	// 流程{0}已经被挂起
	PROCESS_INSTANCE_HAS_BEEN_SUSPENDED("4000000000000023"),
	// 流程{0}已经为激活状态
	PROCESS_INSTANCE_HAS_BEEN_ACTIVED("4000000000000024"),
	// 根据唯一标识{0}找不到流程定义
	CANNOT_FIND_PROCESS_DEFINITION("4000000000000025"),
	// 流程定义{0}已经被挂起
	PROCESS_DEFINITION_HAS_BEEN_SUSPENDED("4000000000000026"),
	// 流程定义{0}已经为激活状态
	PROCESS_DEFINITION_HAS_BEEN_ACTIVED("4000000000000027"),
	// 模型数据为空，请先设计流程并成功保存，再进行发布。
	MODEL_DATA_NULL("4000000000000028"),
	// 数据模型不符要求，请至少设计一条主线流程。
	DATA_MODEL_ERROR("4000000000000029"),
	// 发布模型失败。
	DEPLOY_MODEL_ERROR("4000000000000030"), 
	// 根据唯一标识{0}和系统编码{1}找不到模型
	CANNOT_FIND_MODEL_RECORD("4000000000000031"), 
	// 任务{0}已经被挂起
	TASK_HAS_BEEN_SUSPENDED("4000000000000032"),
	// 任务{0}已经为激活状态
	TASK_HAS_BEEN_ACTIVED("4000000000000033"),
	// 任务{0}已经被认领
	TASK_HAS_BEEN_CLAIMED("4000000000000034"),
	// 无法找到驳回任务节点{0}
	CANNOT_FIND_TASK_REJECT_NODE("4000000000000035"),
	// 流程{0}不支持撤销
	PROCESS_NOT_SUPPORT_CANCEL("4000000000000036"),
	// 流程{0}已无法撤销
	PROCESS_CANNOT_CANCEL("4000000000000037"),
	// 用户{0}无法撤销非自己创建的流程
	PROCESS_CANNOT_CANCEL_BY_CURRENT_USER("4000000000000038"),
	
//	// 任务{0}不支持撤回
//	TASK_NOT_SUPPORT_WITHDRAW("4000000000000039"),
//	// 任务{0}已无法撤回
//	TASK_CANNOT_WITHDRAW("4000000000000040"),
	;
	
	private String code;
	
	private MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
	
	private ErrorCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	
	public String getMessage(String... args) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(this.code, args, locale);
	}
}
