(function () {
	'use strict';
  
	angular.module('app').controller('GroupController', function ($scope, $uibModal, $http) {
    	
		$scope.myAppScopeProvider = {
			showInfo : function(row) {
				var modalInstance = $uibModal.open({
					animation: true,
					templateUrl: 'views/group/groupForm.html',
					controller: 'GroupModal',
					resolve: {
						group: function () {
							if (row) {
			        			return row.entity;
			        		}  else {
			        			return $scope.group;;
			        		}
						}
					}
				});
				
				modalInstance.result.then(function (selectedItem) {
					$scope.myAppScopeProvider.getData();
			    }, function () {});
			},
			getData : function(row) {
	        	$http.get('/group/getGroupList').success(function(data) {
	      	      $scope.grid.data = data;
	      	    });
	        }
			
		};
		$scope.grid = {
			onRegisterApi: function(gridApi){ 
			    $scope.gridApi = gridApi;
			},
			appScopeProvider: $scope.myAppScopeProvider,
			enableColumnMenus: false,
			columnDefs: [
				{ field: 'id', displayName: '아이디', width: '10%', headerCellClass: 'text-center'},
				{ field: 'groupName', displayName: '그룹명', width: '40%', headerCellClass: 'text-center'},
				{ field: 'remarks', displayName: '비고', width: '30%', headerCellClass: 'text-center'},
				{ field: 'useState', displayName: '사용유무', width: '10%', headerCellClass: 'text-center', cellClass: 'text-center', cellFilter: 'useStateFilter' },
				{ field: 'update', displayName: '수정' , width: '10%', headerCellClass: 'text-center', cellClass: 'text-center', cellTemplate: '<div><button class="btn btn-primary btn-xs grid-align-venter" type="button" ng-click="grid.appScope.showInfo(row)">수정</button></div>' }
			]
		};
		
		// 입력 폼 오픈
		$scope.open = function () {
	    	$scope.myAppScopeProvider.showInfo();
	    };
      
	    // 리스트 조회
	    $scope.myAppScopeProvider.getData();
	})
	.controller('GroupModal', function ($scope, $uibModalInstance, $http, $window, group) {
		$scope.group = group;
		$scope.delFlag = false;
	    if (typeof group === "undefined") {
	    	$scope.delFlag = false;
	    } else {
	    	$scope.delFlag = true;
	    }
	    
	    // 저장
	    $scope.submitForm = function() {
		    $http({
		      method  : 'POST',
		      url     : '/group/save',
		      data    : $scope.group 
		     })
		      .success(function(response) {
		    	  $uibModalInstance.close('save');
		      });
	    };
	    $scope.del = function () {
	    	var delConfirm = $window.confirm('정말 삭제 하시겠습니까 ?');
	    	if (delConfirm) {
	    		$http({
					method  : 'POST',
					url     : '/group/del',
					data    : $scope.group 
				}).success(function(response) {
					$uibModalInstance.close('del');
				});
	    	}
	    	
	  	};
	  	
	    // 닫기
	    $scope.cancel = function () {
	  	  $uibModalInstance.dismiss('cancel');
	  	};
	});
 
}());      
