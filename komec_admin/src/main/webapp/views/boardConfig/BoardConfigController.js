(function () {
	'use strict';
  
	angular.module('app').controller('BoardConfigController', function ($scope, $uibModal, $http) {
    	
		$scope.boardConfigProvider = {
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
					$scope.boardConfigProvider.getData();
			    }, function () {});
			},
			getData : function(row) {
	        	$http.get('/boardConfig/getBoardConfigList').success(function(data) {
	      	      $scope.grid.data = data;
	      	    });
	        },
	        toBoard : function(row) {
	        	var modalInstance = $uibModal.open({
					animation: true,
					templateUrl: 'views/boardConfig/boardList.html',
					controller: 'BoardListModal',
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
	        }
			
		};
		$scope.grid = {
			onRegisterApi: function(gridApi){ 
			    $scope.gridApi = gridApi;
			},
			appScopeProvider: $scope.boardConfigProvider,
			enableColumnMenus: false,
			columnDefs: [
				{ field: 'id', displayName: '번호', width: '5%', headerCellClass: 'text-center', cellClass: 'text-center'},
				{ field: 'name', displayName: '게시판명', width: '40%', headerCellClass: 'text-center'},
				{ field: 'remarks', displayName: '비고', width: '15%', headerCellClass: 'text-center'},
				{ field: 'boardType', displayName: '게시판 종류', width: '10%', headerCellClass: 'text-center', cellClass: 'text-center', cellFilter: 'boardTypeFilter'},
				{ field: 'useState', displayName: '사용유무', width: '10%', headerCellClass: 'text-center', cellClass: 'text-center', cellFilter: 'useStateFilter'},
				{ field: 'update', displayName: '수정' , width: '10%', headerCellClass: 'text-center', cellClass: 'text-center', cellTemplate: '<div><button class="btn btn-primary btn-xs grid-align-venter" type="button" ng-click="grid.appScope.showInfo(row)">수정</button></div>' },
				{ field: 'toBoard', displayName: '미리보기' , width: '10%', headerCellClass: 'text-center', cellClass: 'text-center', cellTemplate: '<div><button class="btn btn-primary btn-xs grid-align-venter" type="button" ng-click="grid.appScope.toBoard(row)">보기</button></div>' }
			]
		};
		
		// 입력 폼 오픈
		$scope.open = function () {
			$scope.boardConfig = {};
			$scope.boardConfig.boardType = 'COMMON';
			$scope.boardConfig.useEditState = 'USE';
			$scope.boardConfig.useState = 'USE';
			$scope.boardConfig.pagePerCnt = 10;
			$scope.boardConfig.boardCategorys = [];
			
	    	$scope.boardConfigProvider.showInfo();
	    };
      
	    // 리스트 조회
	    $scope.boardConfigProvider.getData();
	})
	.controller('BoardConfigModal', function ($scope, $uibModalInstance, $http, $window, boardConfig) {
		$scope.boardConfig = boardConfig;
	    // 저장
	    $scope.submitForm = function() {
		    $http({
		      method  : 'POST',
		      url     : '/boardConfig/save',
		      data    : $scope.boardConfig 
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
					data    : $scope.boardConfig 
				}).success(function(response) {
					$uibModalInstance.close('del');
				});
	    	}
	  	};
	  	
	  	$scope.addCate = function() {
	  	  var boardCategory = {};
	  	  boardCategory.name = $scope.category;
	  	  boardCategory.boardConfigId = $scope.boardConfig.id;	
	  	  if ($scope.boardConfig.boardCategorys) {
	  		if ($scope.boardConfig.boardCategorys.indexOf($scope.category)==-1){
			  $scope.boardConfig.boardCategorys.push( boardCategory );
			}
	  	  } else {
	  		$scope.boardConfig.boardCategorys = [];
	  		$scope.boardConfig.boardCategorys.push( boardCategory );
	  	  }
		  $scope.category = "";
		};
		  // remove an item
	    $scope.removeCate = function ( $index ) {
	      $scope.boardConfig.boardCategorys.splice( $index, 1 );
	    };
	    
	    // 닫기
	    $scope.cancel = function () {
	  	  $uibModalInstance.dismiss('cancel');
	  	};
	})
	
	.controller('BoardListModal', function ($scope, $uibModalInstance, $http, $window, $sce, boardConfig) {
		$scope.viewFlag = true;
		$scope.boardConfig = boardConfig;
		$scope.currentPage = 1;
	    $scope.pageSize = 10;
	    $scope.maxSize = 10;
	    $scope.q = '';
	    
		$http.get('/boardData/getBoardDataListByConfigId?id=' + boardConfig.id).success(function(data) {
	      $scope.boards = data;
	      $scope.totalItems = data.length;
	    });
		
	    // 신규
	    $scope.create = function () {
	    	$scope.boardData = {};
	    	$scope.boardData.boardConfigId = boardConfig.id;
	    	$scope.viewFlag = false;
	  	};
	  	
	  	$scope.boardView = function (item) {
	  		$scope.boardData = item;
	  		$scope.boardData.contents = $sce.trustAsHtml(item.contents);
	    	$scope.viewFlag = false;
	  	}
	  	
	    // 저장
	    $scope.save = function() {
		    $http({
		      method  : 'POST',
		      url     : '/boardData/save',
		      data    : $scope.boardData 
		     })
		      .success(function(response) {
		    	  $scope.boardData = response; 
		    	  alert('정상 처리 되었습니다.');
		      });
	    };
	    
	    // 삭제
	    $scope.del = function () {
	    	var delConfirm = $window.confirm('정말 삭제 하시겠습니까 ?');
	    	if (delConfirm) {
	    		$http({
					method  : 'POST',
					url     : '/boardData/del',
					data    : $scope.boardData 
				}).success(function(response) {
					$scope.viewFlag = true;
				});
	    	}
	  	};
	  	
	    // 돌아가기
	    $scope.back = function () {
	    	$http.get('/boardData/getBoardDataListByConfigId?id=' + boardConfig.id).success(function(data) {
	  	      $scope.boards = data;
	  	      $scope.totalItems = data.length;
	  	    });
	    	$scope.viewFlag = true;
	  	};
		  	
		// 닫기
	    $scope.cancel = function () {
	  	  $uibModalInstance.dismiss('cancel');
	  	};
	  	
	    // 에디터 셋팅
		$scope.editorOptions = {
			height : 250,
			uiColor: '#cceaee'
		};
		
	})
	
	;
 
}());      
