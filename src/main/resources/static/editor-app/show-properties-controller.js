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
 * Controller for morph shape selection
 */

var MisrobotPropertiesEditorCtrl = [ '$rootScope','$scope', '$translate', '$http', "$window","$q",'$timeout', function($rootScope,$scope, $translate, $http, $window, $q, $timeout)  {

    $scope.value = {};

    //名称
    $scope.value.nodeName = {value:$scope.selectedItem.properties[1].value};

    // 条件判断
    $scope.value.nodeCondition = $scope.selectedItem.properties[3];
    if ($scope.value.nodeCondition.value !== undefined && $scope.value.nodeCondition.value !== null) {
        $scope.nodeCondition = {value: $scope.value.nodeCondition.value};
    } else {
        $scope.nodeCondition = {value: ''};
    }

    // 判断是否当前节点的前三个节点为人工任务节点，如果为人工任务节点，则此节点需要增加是否同意的条件选择
    var incommingShape = $rootScope.currentSelectedShape;
    for (var i = 0; i < 3; i++) {
        if (incommingShape.incoming && incommingShape.incoming.length > 0) {
            incommingShape = incommingShape.incoming[0];
        } else {
            incommingShape = undefined;
            break;
        }
    }

    if (incommingShape) {
        var incommingStencilItem = $scope.getStencilItemById(incommingShape.getStencil().idWithoutNs());

        // 为人工任务节点，则此节点需要增加是否同意的条件选择
        if (incommingStencilItem.morphRole == 'ActivitiesMorph') {
            var url = KISBPM.URL.getDictionaryDataByType('operator');
            $http({method: 'GET', url: url}).
                success(function (data, status, headers, config) {
                    $scope.operators = angular.fromJson(data);
                }).
                error(function (data, status, headers, config) {
                  console.log('Error loading model:' + data);
                });
        }
    }

    // // 查找
    // incommingShape = $rootScope.currentSelectedShape;
    // while (incommingShape.incoming && incommingShape.incoming.length > 0) {
    //       incommingShape = incommingShape.incoming[0];
    // }

    // 如果当前节点为开始节点，则显示form表单
    if ($scope.currentStencilItem.morphRole == 'StartEventsMorph') {
        $scope.class = "modal-wide";

        $scope.value.formProperty = $scope.selectedItem.properties[6];
        // Put json representing form properties on scope
        if ($scope.value.formProperty.value !== undefined && $scope.value.formProperty.value !== null
            && $scope.value.formProperty.value.formProperties !== undefined
            && $scope.value.formProperty.value.formProperties !== null) {
            // Note that we clone the json object rather then setting it directly,
            // this to cope with the fact that the user can click the cancel button and no changes should have happended
            $scope.formProperties = angular.copy($scope.value.formProperty.value.formProperties);
            
            for (var i = 0; i < $scope.formProperties.length; i++) {
                var formProperty = $scope.formProperties[i];
                if (formProperty.enumValues && formProperty.enumValues.length > 0) {
                    for (var j = 0; j < formProperty.enumValues.length; j++) {
                        var enumValue = formProperty.enumValues[j];
                        if (!enumValue.id && !enumValue.name && enumValue.value) {
                            enumValue.id = enumValue.value;
                            enumValue.name = enumValue.value;
                        }
                    }
                }
            }
            
        } else {
            $scope.formProperties = [];
        }

        $scope.selectedProperties = [];
        $scope.selectedEnumValues = [];
        
        $scope.translationsRetrieved = false;
        
        $scope.labels = {};
        
        var idPromise = $translate('PROPERTY.FORMPROPERTIES.ID');
        var namePromise = $translate('PROPERTY.FORMPROPERTIES.NAME');
        var typePromise = $translate('PROPERTY.FORMPROPERTIES.TYPE');
        
        $q.all([idPromise, namePromise, typePromise]).then(function(results) { 
            $scope.labels.idLabel = results[0];
            $scope.labels.nameLabel = results[1];
            $scope.labels.typeLabel = results[2];
            $scope.translationsRetrieved = true;
            
            // Config for grid
            $scope.gridOptions = {
                data: 'formProperties',
                enableRowReordering: true,
                headerRowHeight: 28,
                multiSelect: false,
                keepLastSelected : false,
                selectedItems: $scope.selectedProperties,
                columnDefs: [{ field: 'id', displayName: $scope.labels.idLabel },
                    { field: 'name', displayName: $scope.labels.nameLabel},
                    { field: 'type', displayName: $scope.labels.typeLabel}]
            };
            
            $scope.enumGridOptions = {
                data: 'selectedProperties[0].enumValues',
                enableRowReordering: true,
                headerRowHeight: 28,
                multiSelect: false,
                keepLastSelected : false,
                selectedItems: $scope.selectedEnumValues,
                columnDefs: [{ field: 'id', displayName: $scope.labels.idLabel },
                    { field: 'name', displayName: $scope.labels.nameLabel}]
            }
        });

        // Handler for when the value of the type dropdown changes
        $scope.propertyTypeChanged = function() {

            // Check date. If date, show date pattern
            if ($scope.selectedProperties[0].type === 'date') {
                $scope.selectedProperties[0].datePattern = 'MM-dd-yyyy hh:mm';
                
            } else {
                delete $scope.selectedProperties[0].datePattern;
            }

            // Check enum. If enum, show list of options
            if ($scope.selectedProperties[0].type === 'enum') {
                $scope.selectedProperties[0].enumValues = [ {id: 'value1', name: 'Value 1'}, {id: 'value2', name: 'Value 2'}];
                
            } else {
                delete $scope.selectedProperties[0].enumValues;
            }
        };

        // Click handler for add button
        var propertyIndex = 1;
        $scope.addNewProperty = function() {
            $scope.formProperties.push({ id : 'new_property_' + propertyIndex++,
                name : '',
                type : 'string',
                readable: true,
                writable: true});
            
            $timeout(function(){
                $scope.gridOptions.selectItem($scope.formProperties.length - 1, true);
            });
        };

        // Click handler for remove button
        $scope.removeProperty = function() {
            if ($scope.selectedProperties.length > 0) {
                var index = $scope.formProperties.indexOf($scope.selectedProperties[0]);
                $scope.gridOptions.selectItem(index, false);
                $scope.formProperties.splice(index, 1);

                $scope.selectedProperties.length = 0;
                if (index < $scope.formProperties.length) {
                    $scope.gridOptions.selectItem(index + 1, true);
                } else if ($scope.formProperties.length > 0) {
                    $scope.gridOptions.selectItem(index - 1, true);
                }
            }
        };

        // Click handler for up button
        $scope.movePropertyUp = function() {
            if ($scope.selectedProperties.length > 0) {
                var index = $scope.formProperties.indexOf($scope.selectedProperties[0]);
                if (index != 0) { // If it's the first, no moving up of course
                    // Reason for funny way of swapping, see https://github.com/angular-ui/ng-grid/issues/272
                    var temp = $scope.formProperties[index];
                    $scope.formProperties.splice(index, 1);
                    $timeout(function(){
                        $scope.formProperties.splice(index + -1, 0, temp);
                    }, 100);

                }
            }
        };

        // Click handler for down button
        $scope.movePropertyDown = function() {
            if ($scope.selectedProperties.length > 0) {
                var index = $scope.formProperties.indexOf($scope.selectedProperties[0]);
                if (index != $scope.formProperties.length - 1) { // If it's the last element, no moving down of course
                    // Reason for funny way of swapping, see https://github.com/angular-ui/ng-grid/issues/272
                    var temp = $scope.formProperties[index];
                    $scope.formProperties.splice(index, 1);
                    $timeout(function(){
                        $scope.formProperties.splice(index + 1, 0, temp);
                    }, 100);

                }
            }
        };
        
        $scope.addNewEnumValue = function() {
            if ($scope.selectedProperties.length > 0) {
                $scope.selectedProperties[0].enumValues.push({ id : '', name : ''});
            }
            
            $timeout(function(){
                $scope.enumGridOptions.selectItem($scope.selectedProperties[0].enumValues.length - 1, true);
            });
        };

        // Click handler for remove button
        $scope.removeEnumValue = function() {
            if ($scope.selectedProperties.length > 0 && $scope.selectedEnumValues.length > 0) {
                var index = $scope.selectedProperties[0].enumValues.indexOf($scope.selectedEnumValues[0]);
                $scope.enumGridOptions.selectItem(index, false);
                $scope.selectedProperties[0].enumValues.splice(index, 1);

                $scope.selectedEnumValues.length = 0;
                if (index < $scope.selectedProperties[0].enumValues.length) {
                    $timeout(function(){
                        $scope.enumGridOptions.selectItem(index + 1, true);
                    });
                    
                } else if ($scope.selectedProperties[0].enumValues.length > 0) {
                    $timeout(function(){
                        $scope.enumGridOptions.selectItem(index - 1, true);
                    });
                }
            }
        };

        // Click handler for up button
        $scope.moveEnumValueUp = function() {
            if ($scope.selectedProperties.length > 0 && $scope.selectedEnumValues.length > 0) {
                var index = $scope.selectedProperties[0].enumValues.indexOf($scope.selectedEnumValues[0]);
                if (index != 0) { // If it's the first, no moving up of course
                    // Reason for funny way of swapping, see https://github.com/angular-ui/ng-grid/issues/272
                    var temp = $scope.selectedProperties[0].enumValues[index];
                    $scope.selectedProperties[0].enumValues.splice(index, 1);
                    $timeout(function(){
                        $scope.selectedProperties[0].enumValues.splice(index + -1, 0, temp);
                    });

                }
            }
        };

        // Click handler for down button
        $scope.moveEnumValueDown = function() {
            if ($scope.selectedProperties.length > 0 && $scope.selectedEnumValues.length > 0) {
                var index = $scope.selectedProperties[0].enumValues.indexOf($scope.selectedEnumValues[0]);
                if (index != $scope.selectedProperties[0].enumValues.length - 1) { // If it's the last element, no moving down of course
                    // Reason for funny way of swapping, see https://github.com/angular-ui/ng-grid/issues/272
                    var temp = $scope.selectedProperties[0].enumValues[index];
                    $scope.selectedProperties[0].enumValues.splice(index, 1);
                    $timeout(function(){
                        $scope.selectedProperties[0].enumValues.splice(index + 1, 0, temp);
                    });

                }
            }
        };
    }

    $scope.ok = function () {
        //修改名称
        $scope.selectedItem.properties[1].value = $scope.value.nodeName.value;
        $scope.updatePropertyInModel($scope.selectedItem.properties[1]);

        // $scope.property.value = $scope.conditionExpression.value;
        // $scope.updatePropertyInModel($scope.property);

        if ($scope.formProperties.length > 0) {
            $scope.value.formProperty.value = {};
            $scope.value.formProperty.value.formProperties = $scope.formProperties;
        } else {
            formProperty.value = null;
        }

        $scope.updatePropertyInModel($scope.value.formProperty);
        $scope.close();

        // if ($scope.value.operator) {
        //     $window.alert($scope.value.operator.name);    
        // } else {
        //     $window.alert('没有选择');   
        // }
    }

    $scope.cancel = function() {
    	$scope.$hide();
    };

    // Close button handler
    $scope.close = function() {
    	$scope.$hide();
    };

}];