(function () {
	'use strict';
  
	angular.module('app').controller('MenuController', function ($scope, $log, $http, $location, $window) {
    
		$scope.menuFlag = false;
		$scope.menus = [];
		
		// 사이트 정보 조회
		$http.get('/site/getSiteList').success(function(data) {
            $scope.sites = data;
            if (data.length > 0) {
            	$scope.setSite(data[0]);
            }
        });
		
		// 사이트 선택 시
		$scope.setSite = function (item) {
	        // 메뉴 정보 조회
			$http.get('/site/getSite?id=' + item.id).success(function(data) {
	            $scope.menus = data;
	        });
	    };
		
		$scope.toggle = function (scope) {
          scope.toggle();
        };
	    
        // 새 메뉴 추가
		$scope.newSubItem = function (scope) {
			var nodeData = scope.$modelValue;
			$log.log(nodeData);
			var menu = {};
			menu.useState = 'USE';
			if (nodeData.upperId === undefined) {
				menu.siteId = nodeData.id;
			} else {
				$log.log(nodeData.upperId);
				menu.upperId = nodeData.id;
				menu.siteId = nodeData.siteId;
			}
			$http({
	            method  : 'POST',
	            url     : '/menu/save',
	            data    : menu 
	           })
	           .success(function(response) {	        	  
	              nodeData.menus.push(response);
	              $scope.menu = response;
	              $scope.menuFlag = true;
	           });
		};
		
		// 메뉴 편집
		$scope.edit = function (scope) {
            var nodeData = scope.$modelValue;
            $scope.menu = nodeData;
            $scope.menuFlag = true;
            $window.scrollTo(0, 0);
        };
        
        $scope.saveMenu = function () {
        	$http({
                method  : 'POST',
                url     : '/menu/save',
                data    : $scope.menu
               })
               .success(function(response) {
                   alert('정상 처리 되었습니다.');
               })
               .error(function(data, status){
                   alert('데이터 저장이 실패했습니다.');
               });
        };
		
	})
	
	;
 
}());      
