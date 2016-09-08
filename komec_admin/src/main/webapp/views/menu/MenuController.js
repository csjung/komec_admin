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
			$http.get('/site/getMenusForSite?id=' + item.id).success(function(data) {
	            $scope.menus = data;
	        });
	    };
		
		$scope.toggle = function (scope) {
          scope.toggle();
        };
	    
        // 새 메뉴 추가
		$scope.newSubItem = function (scope) {
			var nodeData = scope.$modelValue;
			var menu = {};
			menu.menus = [];
			menu.useState = 'USE';
			menu.isAnonymous = 'YES';
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
        
        // 메뉴 저장
        $scope.saveMenu = function () {
        	if ($scope.menu.boardConfig) {
        		$scope.menu.boardConfigId = $scope.menu.boardConfig.id;
        	} else {
        		$scope.menu.boardConfigId = null;
        		$scope.menu.boardConfig = null;
        	}
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
        
        // 메뉴 삭제
        $scope.removeItem = function (scope) {
        	var nodeData = scope.$modelValue;
            var delConfirm = $window.confirm('하위정보도 모두 삭제 됩니다.\n정말 삭제 하시겠습니까 ?');
            if (delConfirm) {
            	if (nodeData.menus) {
            		$scope.deepDel(nodeData.menus);
            	}
            	$http({
                    method  : 'POST',
                    url     : '/menu/del',
                    data    : nodeData
                   })
                   .success(function(response) {
                   })
                   .error(function(data, status){
                       alert('삭제 처리에 실패했습니다.');
                   });
            	scope.remove();
                $scope.menuFlag = false;
            }
        };
        
        $scope.deepDel = function(dst) {
    	    angular.forEach(dst, function(obj) {
    	        if (obj !== dst) {
	        	  $http({
                    method  : 'POST',
                    url     : '/menu/del',
                    data    : obj
                   })
                   .success(function(response) {
                   })
                   .error(function(data, status){
                       alert('삭제 처리에 실패했습니다.');
                   });
    	           if (obj.menus) {
    	        		$scope.deepDel(obj.menus);
    	           }
    	        }
    	    });
    	};
    	
    	// 게시판 정보 조회
    	$scope.getBoardConfig = function(val) {
    	    return $http.get('/boardConfig/getBoardConfigListForLike', {
    	      params: {
    	    	  filter: val,
    	      }
    	    }).then(function(response){
    	      return response.data;
    	    });
    	};
    	
    	// drag and drop
        $scope.treeOptions = {
        	beforeDrop : function (e) {
        		var source = e.source;
                var dest = e.dest;
                if (source && source.index != dest.index) {
                	var scObj = source.nodeScope.$modelValue;
                    var deObj = source.nodesScope.$modelValue[dest.index];
                    console.log(scObj);
                    console.log(deObj);
                    var scSort = scObj.sort;
                    var deSort = deObj.sort;
                    scObj.sort = deSort;
                    deObj.sort = scSort;
                    $http({
                        method  : 'POST',
                        url     : '/menu/save',
                        data    : scObj 
                       })
                       .success(function(response) {
                       });
                    $http({
                        method  : 'POST',
                        url     : '/menu/save',
                        data    : deObj 
                       })
                       .success(function(response) {
                       });
                }
        	}
        };

    	  
	})
	;
 
}());      
