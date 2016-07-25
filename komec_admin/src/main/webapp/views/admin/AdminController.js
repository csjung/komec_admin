(function () {
	'use strict';
  
	angular.module('app').controller('AdminController', function ($scope, $uibModal, $http) {
    	
		$scope.myAppScopeProvider = {
			showInfo : function(row) {
				var modalInstance = $uibModal.open({
					animation: true,
					templateUrl: 'views/admin/adminForm.html',
					controller: 'AdminModal',
					resolve: {
						admin: function () {
							if (row) {
								row.entity.passwordConfirm = row.entity.password;
			        			return row.entity;
			        		}  else {
			        			return $scope.admin;;
			        		}
						}
					}
				});
				
				modalInstance.result.then(function (selectedItem) {
					$scope.myAppScopeProvider.getData();
			    }, function () {});
			},
			getData : function(row) {
	        	$http.get('/admin/getAdminManagerList').success(function(data) {
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
				{ field: 'id', visible: false, displayName: 'id' }, 
				{ field: 'userId', displayName: '아이디', width: '40%', headerCellClass: 'text-center'},
				{ field: 'userName', displayName: '이름', width: '40%', headerCellClass: 'text-center'},
				{ field: 'update', displayName: '수정' , width: '20%', headerCellClass: 'text-center', cellClass: 'text-center', cellTemplate: '<div><button class="btn btn-primary btn-xs grid-align-venter" type="button" ng-click="grid.appScope.showInfo(row)">수정</button></div>' }
			]
		};
		
		// 입력 폼 오픈
		$scope.open = function () {
	    	$scope.myAppScopeProvider.showInfo();
	    };
      
	    // 리스트 조회
	    $scope.myAppScopeProvider.getData();
	})
	.controller('AdminModal', function ($scope, $uibModalInstance, $http, $window, admin) {
		$scope.admin = admin;
		$scope.confirmStyle = "btn-warning";
		$scope.idConfirmValue = '';
		$scope.delFlag = false;
	    if (typeof admin === "undefined") {
	    	$scope.delFlag = false;
	    } else {
	    	$scope.delFlag = true;
	    }
	    
	    // 저장
	    $scope.submitForm = function() {
	    	if ($("#password").val() != $("#passwordConfirm").val()) {
	    		alert('패스워드를 다시 입력 바랍니다.');
	    		$scope.admin.password = '';
	    		$scope.admin.passwordConfirm = '';
	    		return;
	    	}
	    	if ($scope.confirmStyle == "btn-warning" && !$scope.delFlag) {
	    		alert('아이디 확인을 바랍니다.');
	    		return;
	    	}  
		    $http({
		      method  : 'POST',
		      url     : '/admin/save',
		      data    : $scope.admin //forms user object
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
					url     : '/admin/del',
					data    : $scope.admin //forms user object
				}).success(function(response) {
					$uibModalInstance.close('del');
				});
	    	}
	    	
	  	};
	  	
	    // 아이디 확인
	    $scope.idConfirm = function () {
	    	if ($scope.admin.userId != '') {
	    		$http({
					method  : 'GET',
					url     : '/admin/getAdminByUserId',
					params  : $scope.admin //forms user object
				}).success(function(response) {
					$scope.idConfirmValue = $scope.admin.userId;
					if (response.length == 0) {
						$scope.confirmStyle = "btn-success";
					} else {
						$scope.confirmStyle = "btn-warning";
					}
					
				});
	    	}
	    	
	    };
	    // 아이디 값이 바뀌면
	    $scope.userIdChange = function () {
	    	console.log('$scope.idConfirmValue=>' + $scope.idConfirmValue);
	    	console.log('$scope.admin.userIde=>' + $scope.admin.userId);
	    	if ($scope.admin.userId != $scope.idConfirmValue) {
	    		$scope.confirmStyle = "btn-warning";
	    	} else {
	    		$scope.confirmStyle = "btn-success";
	    	}
	    }
	    
	    // 닫기
	    $scope.cancel = function () {
	  	  $uibModalInstance.dismiss('cancel');
	  	};
	});
 
}());      
