(function () {
	'use strict';
  
	angular.module('app').controller('BoardConfigController', function ($scope, $uibModal, $http) {
    	
		$scope.myAppScopeProvider = {
			showInfo : function(row) {
				var modalInstance = $uibModal.open({
					animation: true,
					templateUrl: 'views/boardConfig/boardConfigForm.html',
					controller: 'BoardConfigModal',
					size: 'lg',
				    windowClass: 'big-modal',
					resolve: {
						boardConfig: function () {
							if (row) {
			        			return row.entity;
			        		}  else {
			        			return $scope.boardConfig;
			        		}
						}
					}
				});
				
				modalInstance.result.then(function (selectedItem) {
					$scope.myAppScopeProvider.getData();
			    }, function () {});
			},
			getData : function(row) {
	        	$http.get('/boardConfig/getBoardConfigList').success(function(data) {
	      	      $scope.grid.data = data;
	      	    });
	        },
	        toBoard : function(row) {
	        	
	        }
			
		};
		$scope.grid = {
			onRegisterApi: function(gridApi){ 
			    $scope.gridApi = gridApi;
			},
			appScopeProvider: $scope.myAppScopeProvider,
			enableColumnMenus: false,
			columnDefs: [
				{ field: 'id', displayName: '번호', width: '5%', headerCellClass: 'text-center'},
				{ field: 'name', displayName: '게시판명', width: '45%', headerCellClass: 'text-center'},
				{ field: 'boardType', displayName: '게시판 종류', width: '20%', headerCellClass: 'text-center', cellFilter: 'boardTypeFilter'},
				{ field: 'useState', displayName: '사용유무', width: '10%', headerCellClass: 'text-center', cellClass: 'text-center', cellFilter: 'useStateFilter'},
				{ field: 'update', displayName: '수정' , width: '10%', headerCellClass: 'text-center', cellClass: 'text-center', cellTemplate: '<div><button class="btn btn-primary btn-xs grid-align-venter" type="button" ng-click="grid.appScope.showInfo(row)">수정</button></div>' },
				{ field: 'toBoard', displayName: '미리보기' , width: '10%', headerCellClass: 'text-center', cellClass: 'text-center', cellTemplate: '<div><button class="btn btn-primary btn-xs grid-align-venter" type="button" ng-click="grid.appScope.toBoard(row)">수정</button></div>' }
			]
		};
		
		// 입력 폼 오픈
		$scope.open = function () {
			$scope.boardConfig = {};
			$scope.boardConfig.boardType = 'COMMON';
			$scope.boardConfig.useEditState = 'USE';
			$scope.boardConfig.useState = 'USE';
			$scope.boardConfig.pagePerCnt = 10;
			$scope.boardConfig.categorys = [];
			
	    	$scope.myAppScopeProvider.showInfo();
	    };
      
	    // 리스트 조회
	    $scope.myAppScopeProvider.getData();
	})
	.controller('BoardConfigModal', function ($scope, $uibModalInstance, $http, $window, boardConfig) {
		$scope.boardConfig = boardConfig;
	    // 저장
	    $scope.submitForm = function() {
		    $http({
		      method  : 'POST',
		      url     : '/boardConfig/save',
		      data    : $scope.boardConfig //forms user object
		     })
		      .success(function(response) {
		    	  $uibModalInstance.close('save');
		      });
	    };
	    
	    // 삭제
	    $scope.del = function () {
	    	var delConfirm = $window.confirm('정말 삭제 하시겠습니까 ?');
	    	if (delConfirm) {
	    		$http({
					method  : 'POST',
					url     : '/boardConfig/del',
					data    : $scope.boardConfig //forms user object
				}).success(function(response) {
					$uibModalInstance.close('del');
				});
	    	}
	    	
	  	};
	  	
	  	$scope.addCate = function() {
		  if ($scope.boardConfig.categorys.indexOf($scope.category)==-1){
			  $scope.boardConfig.categorys.push( $scope.category );
		  }
		  $scope.category = "";
		};
		  // remove an item
	    $scope.removeCate = function ( idx ) {
	      $scope.boardConfig.categorys.splice( idx, 1 );
	    };
	    
	    // 닫기
	    $scope.cancel = function () {
	  	  $uibModalInstance.dismiss('cancel');
	  	};
	});
 
}());      
