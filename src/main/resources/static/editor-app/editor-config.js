/*
 * Activiti Modeler component part of the Activiti project
 * Copyright 2005-2014 Alfresco Software, Ltd. All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
'use strict';

var KISBPM = KISBPM || {};

KISBPM.CONFIG = {
		'showRemovedProperties' : false
};

KISBPM.HEADER_CONFIG = {
		'showAppTitle' : true,
		'showHeaderMenu' : true,
		'showMainNavigation' : true,
		'showPageHeader' : true
};

// add by zhangyu begin for properties visible
KISBPM.MISROBOT_SHAPE_PROPERTIES_CONFIG = {
	'BPMNDiagram': {
		'oryx-process_id': false,
		'oryx-documentation': false,
		'oryx-process_author': false,
		'oryx-process_version': false,
		'oryx-process_namespace': false,
		'oryx-executionlisteners': false,
		'oryx-eventlisteners': false,
		'oryx-signaldefinitions': false,
		'oryx-messagedefinitions': false
	},
	'StartNoneEvent': {
		'oryx-overrideid': false,
		// 'oryx-documentation': false,
		'oryx-executionlisteners': false,
		// 'oryx-initiator': false,
		'oryx-formkeydefinition':false
	},
	'UserTask': {
		// 'oryx-overrideid': false,
		// // 'oryx-documentation': false,
		// 'oryx-asynchronousdefinition': false,
		// 'oryx-exclusivedefinition': false,
		// 'oryx-executionlisteners': false,
		// 'oryx-multiinstance_type': false,
		// 'oryx-multiinstance_cardinality': false,
		// 'oryx-multiinstance_collection': false,
		// 'oryx-multiinstance_variable': false,
		// 'oryx-multiinstance_condition': false,
		// 'oryx-isforcompensation': false,
		// // 'oryx-usertaskassignment': false,
		// 'oryx-formkeydefinition': false,
		// 'oryx-duedatedefinition': false,
		// 'oryx-prioritydefinition': false,
		// 'oryx-tasklisteners': false
	},
	'SequenceFlow': {
		'oryx-overrideid': false,
		// 'oryx-documentation': false,
		'oryx-executionlisteners': false,
		'oryx-defaultflow': false
	},
	'ExclusiveGateway': {
		'oryx-overrideid': false,
		// 'oryx-documentation': false,
		'oryx-asynchronousdefinition': false,
		'oryx-exclusivedefinition': false,
		'oryx-sequencefloworder': false
	},
	'EndNoneEvent': {
		'oryx-overrideid': false,
		// 'oryx-documentation': false,
		'oryx-executionlisteners': false
	}
};