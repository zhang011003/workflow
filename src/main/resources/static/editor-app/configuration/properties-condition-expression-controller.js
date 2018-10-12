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

/*
 * Condition expression
 */

var KisBpmConditionExpressionCtrl = [ '$scope', '$modal', function($scope, $modal) {

    // Config for the modal window
    var opts = {
        template:  'editor-app/configuration/properties/condition-expression-popup.html?version=' + Date.now(),
        scope: $scope
    };

    // Open the dialog
    $modal(opts);
}];

var KisBpmConditionExpressionPopupCtrl = [ '$scope', '$translate', '$http','$q','$timeout', '$window', function($scope, $translate, $http, $q, $timeout, $window) {
    // modify by zhangyu begin

	// Put json representing condition on scope
    // if ($scope.property.value !== undefined && $scope.property.value !== null) {

    //     $scope.conditionExpression = {value: $scope.property.value};
        
    // } else {
    //     $scope.conditionExpression = {value: ''};
    // }
	
    if ($scope.property.value !== undefined && $scope.property.value !== null) {

    }

    // // 判断是否当前节点的前三个节点为人工任务节点，如果为人工任务节点，则此节点需要增加是否同意的条件选择
    // var incommingShape = $scope.selectedShape;
    // for (var i = 0; i < 3; i++) {
    //     if (incommingShape.incoming && incommingShape.incoming.length > 0) {
    //         incommingShape = incommingShape.incoming[0];
    //     } else {
    //         incommingShape = undefined;
    //         break;
    //     }
    // }

    // var url;    
    // var incommingIdWithoutNs;
    // $scope.conditionColumn0 = [];
    // $scope.conditionColumn0Select = [];
    // $scope.conditionColumn1 = [];
    // $scope.conditionColumn1Select = [];
    // $scope.conditionColumn2 = [];
    // $scope.conditionColumn2Select = [];
    // $scope.conditionColumn3 = [];
    // $scope.conditionColumn3Select = [];

    
        
    // if (incommingShape) {
    //     incommingIdWithoutNs = incommingShape.getStencil().idWithoutNs();
    //     // 为人工任务节点，则此节点需要增加是否同意的条件选择
    //     if (incommingIdWithoutNs == 'UserTask') {
    //         var item = $scope.getItemByShape(incommingShape);
            
    //         // 如果包含form表单属性，则将表单中的枚举增加到条件选择中
    //         var hasFormProperties = false;
    //         for (var i = 0; i < item.properties.length; i++) {
    //             if(item.properties[i].key == 'oryx-formproperties') {
    //                 hasFormProperties = true;
    //                 if (item.properties[i].value) {
    //                     var formProperties = item.properties[i].value.formProperties;
    //                     for (var j = 0; j < formProperties.length; j++) {
    //                         if(formProperties[j].type == 'enum') {
    //                             $scope.conditionColumn0.push({id:formProperties[j].id, name:formProperties[j].name});  
    //                             $scope.conditionColumn0Select[0] = formProperties[j].id;
    //                             for (var k = 0; k < formProperties[j].enumValues.length; k++) {
    //                                 $scope.conditionColumn2.push(formProperties[j].enumValues[k]);
    //                             }
    //                         }
    //                     }
    //                 } else {
    //                     break;
    //                 }
    //             }
    //         }
            
    //         // 如果没有填充表单信息，则从数据库中获取默认表单的枚举值并增加到条件选择中
    //         if (!hasFormProperties) {

    //         }
    //     }
    // }

    // // 查找最开始节点，如果为起始节点，则增加动态表单的内容到条件选择界面
    // incommingShape = $scope.selectedShape;
    // while (incommingShape.incoming && incommingShape.incoming.length > 0) {
    //       incommingShape = incommingShape.incoming[0];
    // }

    // incommingIdWithoutNs = incommingShape.getStencil().idWithoutNs();
    // // 为起始节点，
    // if (incommingIdWithoutNs == 'StartNoneEvent') {
    //     var item = $scope.getItemByShape(incommingShape);
        
    //     // 如果包含form表单属性，则将表单中的枚举增加到条件选择中
    //     var hasFormProperties = false;
    //     for (var i = 0; i < item.properties.length; i++) {
    //         if(item.properties[i].key == 'oryx-formproperties') {
    //             if (item.properties[i].value) {
    //                 var formProperties = item.properties[i].value.formProperties;
    //                 for (var j = 0; j < formProperties.length; j++) {
    //                     if(formProperties[j].type == 'enum') {
    //                         $scope.conditionColumn0.push({id:formProperties[j].id, name:formProperties[j].name});  
    //                         $scope.conditionColumn0Select[0] = formProperties[j].id;
    //                         for (var k = 0; k < formProperties[j].enumValues.length; k++) {
    //                             $scope.conditionColumn2.push(formProperties[j].enumValues[k]);
    //                         }
    //                     }
    //                 }
    //             } else {
    //                 break;
    //             }
    //         }
    //     }
    // }

    $scope.conditionProperties = [];
    $scope.formProperties = [];

    // Array to contain selected properties (yes - we only can select one, but ng-grid isn't smart enough)
    $scope.selectedProperties = [];
    $scope.selectedEnumValues = [];
    
    $scope.needShowGrid = false;
    $scope.translationsRetrieved = false;
    $scope.choiseOperator = [];
    $scope.compareOperator = [];
    $scope.valueOperator = [];
    
    var url;    
    var incommingIdWithoutNs;
    
    // 判断是否当前节点的前三个节点为人工任务节点，如果为人工任务节点，则此节点需要增加是否同意的条件选择
    var incommingShape = $scope.selectedShape;
    for (var i = 0; i < 3; i++) {
        if (incommingShape.incoming && incommingShape.incoming.length > 0) {
            incommingShape = incommingShape.incoming[0];
        } else {
            incommingShape = undefined;
            break;
        }
    }
    if (incommingShape) {
        incommingIdWithoutNs = incommingShape.getStencil().idWithoutNs();
        // 为人工任务节点，则此节点需要增加是否同意的条件选择
        if (incommingIdWithoutNs == 'UserTask') {
            $scope.needShowGrid = true;
            updateChoiseOperator(incommingShape);
        }
    }

    // 查找最开始节点，如果为起始节点，则增加动态表单的内容到条件选择界面
    incommingShape = $scope.selectedShape;
    while (incommingShape.incoming && incommingShape.incoming.length > 0) {
          incommingShape = incommingShape.incoming[0];
    }

    incommingIdWithoutNs = incommingShape.getStencil().idWithoutNs();
    // 为起始节点，
    if (incommingIdWithoutNs == 'StartNoneEvent') {
        $scope.needShowGrid = true;
        updateChoiseOperator(incommingShape);
    }

    if ($scope.needShowGrid) {
        url = KISBPM.URL.getDictionaryDataByType('enum_compare_operator');
        $http({method: 'GET', url: url}).
            success(function (data, status, headers, config) {
                var json = angular.fromJson(data);
                $scope.compareOperator = angular.fromJson(data);
                // $scope.conditionColumn1Select.push($scope.conditionColumn1[0].name);
            }).
            error(function (data, status, headers, config) {
              console.log('Error loading model:' + data);
            });  

        url = KISBPM.URL.getDictionaryDataByType('logical_operator');
        $http({method: 'GET', url: url}).
            success(function (data, status, headers, config) {
                $scope.logicalOperator = angular.fromJson(data);
            }).
            error(function (data, status, headers, config) {
              console.log('Error loading model:' + data);
            });  
        url = KISBPM.URL.getDictionaryDataByType('logical_operator');
        $http({method: 'GET', url: url}).
            success(function (data, status, headers, config) {
                $scope.logicalOperator = angular.fromJson(data);
            }).
            error(function (data, status, headers, config) {
              console.log('Error loading model:' + data);
            });      
    }
    $scope.labels = {};
    
    var choicePromise = $translate('PROPERTY.CONDITION.CHOICE');
    var compareOperatorPromise = $translate('PROPERTY.CONDITION.COMPARE_OPERATOR');
    var valuePromise = $translate('PROPERTY.CONDITION.VALUE');
    var logicalOperatorPromise = $translate('PROPERTY.CONDITION.LOGICAL_OPERATOR');
    
    $q.all([choicePromise, compareOperatorPromise, valuePromise, logicalOperatorPromise]).then(function(results) { 
        $scope.labels.choiceLabel = results[0];
        $scope.labels.compareOperatorLabel = results[1];
        $scope.labels.valueLabel = results[2];
        $scope.labels.logicalOperatorLabel = results[3];
        $scope.translationsRetrieved = true;
        
        // Config for grid
        $scope.gridOptions = {
            data: 'conditionProperties',
            enableRowReordering: true,
            headerRowHeight: 28,
            multiSelect: false,
            keepLastSelected : false,
            selectedItems: $scope.selectedProperties,
            columnDefs: [{ field: 'choice', displayName: $scope.labels.choiceLabel },
                { field: 'compareOperator', displayName: $scope.labels.compareOperatorLabel, width:80},
                { field: 'value', displayName: $scope.labels.valueLabel},
                { field: 'logicalOperator', displayName: $scope.labels.logicalOperatorLabel,width:80}]
        };
    });

    function updateChoiseOperator(incommingShape) {
        var item = $scope.getItemByShape(incommingShape);
        for (var i = 0; i < item.properties.length; i++) {
            if(item.properties[i].key == 'oryx-formproperties') {
                var form = item.properties[i];
                if (form.value !== undefined && form.value !== null
                    && form.value.formProperties !== undefined
                    && form.value.formProperties !== null) {
                    $scope.formProperties = form.value.formProperties;
                    for (var j = 0; j < $scope.formProperties.length; j++) {
                        $scope.choiseOperator.push({id:$scope.formProperties[j].id, name:$scope.formProperties[j].name});
                        if($scope.formProperties[j].type == 'enum') {
                            $scope.valueOperator[$scope.formProperties[j].id] = {'type':$scope.formProperties[j].type, 'value':$scope.formProperties[j].enumValues};
                        } else if($scope.formProperties[j].type == 'long') {
                            $scope.valueOperator[$scope.formProperties[j].id] = {'type':$scope.formProperties[j].type, 'value':0};
                        } else if($scope.formProperties[j].type == 'string') {
                            $scope.valueOperator[$scope.formProperties[j].id] = {'type':$scope.formProperties[j].type, 'value':''};
                        } else if($scope.formProperties[j].type == 'boolean') {
                            $scope.valueOperator[$scope.formProperties[j].id] = {'type':$scope.formProperties[j].type, 'value':''};
                        } 
                    }
                }
            }
        }
    }
    // Handler for when the value of the type dropdown changes
    $scope.propertyTypeChanged = function() {
        // // Check date. If date, show date pattern
        // if ($scope.selectedProperties[0].type === 'date') {
        //     $scope.selectedProperties[0].datePattern = 'MM-dd-yyyy hh:mm';
            
        // } else {
        //     delete $scope.selectedProperties[0].datePattern;
        // }

        // // Check enum. If enum, show list of options
        // if ($scope.selectedProperties[0].type === 'enum') {
        //     $scope.selectedProperties[0].enumValues = [ {id: 'value1', name: 'Value 1'}, {id: 'value2', name: 'Value 2'}];
            
        // } else {
        //     delete $scope.selectedProperties[0].enumValues;
        // }

    };

    // Click handler for add button
    $scope.addNewProperty = function() {
        $scope.conditionProperties.push({ choice : '',
            compareOperator : '',
            value : '',
            logicalOperator: ''});
        
        $timeout(function(){
            $scope.gridOptions.selectItem($scope.conditionProperties.length - 1, true);
        });
    };

    // Click handler for remove button
    $scope.removeProperty = function() {
        if ($scope.selectedProperties.length > 0) {
            var index = $scope.conditionProperties.indexOf($scope.selectedProperties[0]);
            $scope.gridOptions.selectItem(index, false);
            $scope.conditionProperties.splice(index, 1);

            $scope.selectedProperties.length = 0;
            if (index < $scope.conditionProperties.length) {
                $scope.gridOptions.selectItem(index + 1, true);
            } else if ($scope.conditionProperties.length > 0) {
                $scope.gridOptions.selectItem(index - 1, true);
            }
        }
    };

    $scope.save = function() {
        var expression;
        if($scope.selectedProperties 
            && $scope.selectedProperties.length > 0
            && $scope.selectedProperties[0]) {
            expression = '${' + $scope.selectedProperties[0].choice
                + $scope.selectedProperties[0].compareOperator 
                + $scope.selectedProperties[0].value 
                + '}';
        }
        $scope.property.value = expression;
        $scope.updatePropertyInModel($scope.property);
        $scope.close();
        // $window.alert(expression);
    };

    // Close button handler
    $scope.close = function() {
    	$scope.property.mode = 'read';
    	$scope.$hide();
    };
}];
