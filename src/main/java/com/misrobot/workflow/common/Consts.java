package com.misrobot.workflow.common;

public class Consts {
	public enum TaskWithdrawSupport {
		YES(1), NO(0);
		private int value;
		private TaskWithdrawSupport(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	public enum TaskWithdrawType {
		/**
		 * 所有节点都可撤回
		 */
		ALL_NODE, 
		/**
		 * 第一个审批节点可撤回
		 */
		FIRST_NODE;
	}
	public enum ProcessCancelType {
		/**
		 * 所有节点都可撤销
		 */
		ALL_NODE, 
		/**
		 * 第一个审批节点可撤销
		 */
		FIRST_NODE;
	}
	/**
	 * 默认第一条记录
	 */
	public static final int FIRST_RESULT = 0;
	public static final int MAX_RESULT = 100;
	
	/**
	 * 启动流程用户变量key
	 */
	public static final String PROCESS_INITIATOR = "$start_process_user$";
	
	/**
	 * 撤销节点id
	 */
	public static final String CANCEL_NODE_ID = "cancel_node";
}

