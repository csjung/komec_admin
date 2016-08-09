(function () {
	'use strict';
  
	angular.module('app').controller('BannerZoneController', function ($scope, $uibModal, $http, $location) {
    	
		$scope.myAppScopeProvider = {
			showInfo : function(row) {
				var modalInstance = $uibModal.open({
					animation: true,
					templateUrl: 'views/bannerZone/bannerZoneForm.html',
					controller: 'BannerZoneModal',
					resolve: {
						bannerZone: function () {
							if (row) {
			        			return row.entity;
			        		}  else {
			        			$scope.bannerZone = {};
			        			$scope.bannerZone.useState = 'USE';
			        			return $scope.bannerZone;
			        		}
						},
						sites: function () {
							return $scope.sites;
						}
					}
				});
				
				modalInstance.result.then(function (selectedItem) {
					$scope.myAppScopeProvider.getData();
			    }, function () {});
			},
			showBanner : function(row) {
				$location.url('/banner?bannerZoneId=' + row.entity.id);
			},
			
			getData : function(row) {
	        	$http.get('/bannerZone/getBannerZoneList').success(function(data) {
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
				{ field: 'site.name', displayName: '사이트', width: '20%', headerCellClass: 'text-center'},
				{ field: 'title', displayName: '타이틀', width: '40%', headerCellClass: 'text-center'},
				{ field: 'useState', displayName: '사용유무', width: '10%', headerCellClass: 'text-center', cellClass: 'text-center', cellFilter: 'useStateFilter' },
				{ field: 'update', displayName: '수정' , width: '10%', headerCellClass: 'text-center', cellClass: 'text-center', cellTemplate: '<div><button class="btn btn-primary btn-xs grid-align-venter" type="button" ng-click="grid.appScope.showInfo(row)">수정</button></div>' },
				{ field: 'update', displayName: '배너관리' , width: '10%', headerCellClass: 'text-center', cellClass: 'text-center', cellTemplate: '<div><button class="btn btn-primary btn-xs grid-align-venter" type="button" ng-click="grid.appScope.showBanner(row)">배너관리</button></div>' }
			]
		};
		
		// 입력 폼 오픈
		$scope.open = function () {
	    	$scope.myAppScopeProvider.showInfo();
	    };
	    // 리스트 조회
	    $scope.myAppScopeProvider.getData();
	    $scope.sites = [];
		$http.get('/site/getSiteList').success(function(data) {
	        $scope.sites = data;
	    });
	})
	.controller('BannerZoneModal', function ($scope, $uibModalInstance, $http, $window, bannerZone, sites) {
		$scope.bannerZone = bannerZone;
		$scope.sites = sites;
		if ($scope.bannerZone.site) {
			$scope.site = $scope.bannerZone.site;
		}
	    
	    // 저장
	    $scope.save = function() {
	    	$scope.bannerZone.siteId = $scope.site.id; 
		    $http({
		      method  : 'POST',
		      url     : '/bannerZone/save',
		      data    : $scope.bannerZone 
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
					url     : '/bannerZone/del',
					data    : $scope.bannerZone 
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
