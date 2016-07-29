(function () {
	'use strict';
  
	angular.module('app').controller('ContentsController', function ($scope, $log, $http, $location, $window) {
    
		$scope.menuFlag = false;
		$scope.menus = [];
		$scope.menu = {};
		$scope.contentHist = {};
		$scope.contentHistList = [];
		
		$scope.delpolytime = new Date();
		$scope.popup1 = {
            opened: false
        };
		$scope.calOpen = function() {
            $scope.popup1.opened = true;
        };
        
        $scope.deployHour = 1;
        $scope.options = {
          hstep: [1, 2, 3, 4, 5, 6 ,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23],
        };
		
		// 에디터 셋팅
		$scope.editorOptions = {
			height : 400,
			uiColor: '#cceaee'
		};
		
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
			$http.get('/site/getMenusForSite?id=' + item.id).success(function(data) {
	            $scope.menus = data;
	        });
	    };
		
		$scope.toggle = function (scope) {
          scope.toggle();
        };
        
        // 
        $scope.getContentHistList = function () {
	        $http.get('/contents/getContentHistList?contentsId=' + $scope.menu.contentsId).success(function(data) {
	            $scope.contentHistList = data;
	            if (data.length > 0) {
	            	$scope.contentHist = data[0];
	            }
	        });
        }
        
		// 콘텐츠 편집
		$scope.edit = function (scope) {
			$scope.menu = scope.$modelValue;
            if ($scope.menu.contentsId != 0 && $scope.menu.contentsId != null) {
            	$scope.getContentHistList();
            } else {
            	$scope.contentHist = {};
        		$scope.contentHistList = [];
            }
            $scope.contentsFlag = true;
            $window.scrollTo(0, 0);
        };
        
        // 즉시배포
        $scope.immediately = function () {
        	$scope.contentHist.id = null;
        	$scope.contentHist.upId = null;
        	$scope.contentHist.upDate = null;
        	$http({
                method  : 'POST',
                url     : '/contents/save',
                data    : $scope.contentHist
               })
               .success(function(response) {
            	   // 배포정보 저장
            	   $http({
         	            method  : 'POST',
         	            url     : '/depolySchedule/save',
         	            data    : {contentHistId : response.id, depolyState: 'COMPLETE', completeDate: response.upDate} 
         	           })
         	           .success(function(response) {	        	  
         	           });
            	   if ($scope.menu.contentsId == 0 || $scope.menu.contentsId == null) { // 메뉴 정보에 콘텐츠 ID 추가
            		   $scope.menu.contentsId = response.contentsId;
            		   $http({
           	            method  : 'POST',
           	            url     : '/menu/save',
           	            data    : $scope.menu 
           	           })
           	           .success(function(response) {	        	  
           	           });
            	   }
            	   
            	   alert('정상 처리 되었습니다.');
            	   $scope.getContentHistList();
                   
               })
               .error(function(data, status){
                   alert('데이터 저장이 실패했습니다.');
               });
        };
        
        // 예약배포
        $scope.scheduleDeploy = function () {
        	if ($scope.deployDate == null) {
        		alert('배포 예약일을 선택 해 주세요');
        		return false;
        	}
        	$scope.contentHist.id = null;
        	$scope.contentHist.upId = null;
        	$scope.contentHist.upDate = null;
        	$http({
                method  : 'POST',
                url     : '/contents/saveSchedule',
                data    : $scope.contentHist
               })
               .success(function(response) {
            	   // 배포정보 저장
            	   $http({
         	            method  : 'POST',
         	            url     : '/depolySchedule/save',
         	            data    : {contentHistId : response.id, depolyState: 'WAITING', completeDate: response.upDate, deployDate: $scope.deployDate, deployHour: $scope.deployHour} 
         	           })
         	           .success(function(response) {	        	  
         	           });
            	   
            	   alert('정상 처리 되었습니다.');
            	   $scope.getContentHistList();
                   
               })
               .error(function(data, status){
                   alert('데이터 저장이 실패했습니다.');
               });
        };
        
        
        
        // 메뉴가 콘텐츠인 놈만 표시
        $scope.contentFilter = function (item) {
        	return (item.menuType == null || item.menuType == 'CONTENT');
        };
		
	})
	;
 
}());      
