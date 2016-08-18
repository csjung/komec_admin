(function () {
	'use strict';
  
	angular.module('app').controller('HistoryController', function ($scope, $http, $q, $interval, $filter) {
		$scope.years = [];
		$scope.data = [];
		$scope.currentYear = new Date().getFullYear()
		for(var i = $scope.currentYear ; i > 1978; i--) {
			$scope.years.push({id: i, value: i});
		}
		
		$scope.gridProvider = {
           delInfo : function(row) {
               $http({
                     method  : 'POST',
                     url     : '/history/del',
                     data    : row.entity
                    })
                    .success(function(response) {
                    	$scope.gridProvider.getData();
                    });
            },
			getData : function(row) {
				$http.get('/history/getHistoryList').success(function(data) {
				  $scope.data = data;
			      $scope.historyGrid.data = data;
			    });
	        }
       };
		
		$scope.historyGrid = {
			onRegisterApi: function(gridApi){ 
			    $scope.gridApi = gridApi;
			    gridApi.rowEdit.on.saveRow($scope, $scope.saveRow); 
			},
			enableCellEditOnFocus: true,
			enableColumnMenus: false,
			appScopeProvider: $scope.gridProvider,
			columnDefs: [
				{ field: 'id', visible: false, displayName: 'id' },
				{ field: 'year', displayName: '년도', width: '5%', headerCellClass: 'text-center', enableCellEdit: true, 
				  editableCellTemplate: 'ui-grid/dropdownEditor',
				  editDropdownOptionsFunction: function(rowEntity, colDef) {
					  return $scope.years;
				  }
				},
					 
				{ field: 'month', displayName: '월', width: '5%', headerCellClass: 'text-center', enableCellEdit: true, 
				  editableCellTemplate: 'ui-grid/dropdownEditor',
				  editDropdownOptionsFunction: function(rowEntity, colDef) {
					  return [{id: '', value: ''},
			                  {id: '01', value: '01'},
			                  {id: '02', value: '02'},
			                  {id: '03', value: '03'},
			                  {id: '04', value: '04'},
			                  {id: '05', value: '05'},
			                  {id: '06', value: '06'},
			                  {id: '07', value: '07'},
			                  {id: '08', value: '08'},
			                  {id: '09', value: '09'},
			                  {id: '10', value: '10'},
			                  {id: '11', value: '11'},
			                  {id: '12', value: '12'}
			                  ];
				  }
				},
				
				{ field: 'contents', displayName: '내용', width: '85%', headerCellClass: 'text-center', enableCellEdit: true},
				{ field: 'del', displayName: '삭제' , cellEditableCondition: false,  width: '5%', headerCellClass: 'text-center', cellClass: 'text-center', cellTemplate: '<div><button class="btn btn-primary btn-xs grid-align-venter" type="button" ng-click="grid.appScope.delInfo(row)">삭제</button></div>' },
			]
		};
		
		$scope.saveRow = function( rowEntity ) {
			var promise = $q.defer();
		    $scope.gridApi.rowEdit.setSavePromise( rowEntity, promise.promise );
		    $interval( function() {
		    	console.log("saveRow");
		    	$http({
		             method  : 'POST',
		             url     : '/history/save',
		             data    : rowEntity
		            })
		            .success(function(response) {
		            	promise.resolve();
		            });
		    }, 1000, 1);
		};
		
		$scope.addHistoy = function () {
           $http({
             method  : 'POST',
             url     : '/history/save',
             data    : {year:$scope.currentYear}
            })
            .success(function(response) {
            	$scope.gridProvider.getData();
            });
        };
		
        // 년도 필터
        $scope.changeFilter = function () {
        	if ($scope.yearFilter) {
            	$scope.historyGrid.data = $filter('filter')($scope.data, $scope.yearFilter, 
            		function(actual, expected) {
            		    if (actual.year) {
            		    	console.log(actual);
                			console.log(expected);
                			var serachYear = Number(expected);
                			if (actual.year >= serachYear && actual.year <= (serachYear + 9)) {
                				return true;
                			} else {
                				return false;
                			}
            		    } else {
            		    	return false;
            		    }
            	    }
            	);
        	} else {
        		$scope.gridProvider.getData();
        	}
        	
        };
        
	    // 리스트 조회
		$scope.gridProvider.getData();
		
	});
 
}());      
