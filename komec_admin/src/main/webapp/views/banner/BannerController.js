(function () {
	'use strict';
  
	angular.module('app').controller('BannerController', function ($scope, $uibModal, $http, $location, $window, FileUploader) {
		$scope.banners = [];
		$scope.bannerZoneId = $location.search().bannerZoneId;
		
		$scope.targetTypes = ["_self", "_parent", "_top", "_blank"];
		
		
		$scope.popup1 = {
            opened: false
        };
		$scope.popup2 = {
            opened: false
        };
		
		$scope.calOpen1 = function() {
            $scope.popup1.opened = true;
        };
        $scope.calOpen2 = function() {
            $scope.popup2.opened = true;
        };
		
		// 배너 정보 조회
		$http.get('/bannerZone/getBannerZone?id=' + $scope.bannerZoneId).success(function(data) {
            var banner = {};
            banner.title = data.title;
			$scope.banners.push(banner);
            $http.get('/banner/getBannerList?bannerZoneId=' + $scope.bannerZoneId).success(function(data) {
            	banner.banners = data;
            });
        });
		
		
		// 새 배너 추가
		$scope.newSubItem = function (scope) {
			var nodeData = scope.$modelValue;
			var banner = {};
			banner.bannerZoneId = $scope.bannerZoneId;
			banner.useState = 'USE';
			$http({
	            method  : 'POST',
	            url     : '/banner/save',
	            data    : banner 
	           })
	           .success(function(response) {	        	  
	              nodeData.banners.push(response);
	              $scope.banner = response;
	              $scope.bannerFlag = true;
	           });
		};
		
		// 배너 편집
		$scope.edit = function (scope) {
            var nodeData = scope.$modelValue;
            $scope.banner = nodeData;
            $scope.bannerFlag = true;
            $window.scrollTo(0, 0);
        };
        
        // 배너 저장
        $scope.save = function () {
        	$http({
                method  : 'POST',
                url     : '/banner/save',
                data    : $scope.banner
               })
               .success(function(response) {
                   alert('정상 처리 되었습니다.');
               })
               .error(function(data, status){
                   alert('데이터 저장이 실패했습니다.');
               });
        };
        
        // 배너 삭제
        $scope.removeItem = function (scope) {
        	var nodeData = scope.$modelValue;
            var delConfirm = $window.confirm('정말 삭제 하시겠습니까 ?');
            if (delConfirm) {
            	$http({
                    method  : 'POST',
                    url     : '/banner/del',
                    data    : nodeData
                   })
                   .success(function(response) {
                   })
                   .error(function(data, status){
                       alert('삭제 처리에 실패했습니다.');
                   });
            	scope.remove();
                $scope.bannerFlag = false;
            }
        };
        
        // 돌아가기
		$scope.back = function (scope) {
			$location.url('/bannerZone');
        };
        
        
        // drag and drop
        $scope.treeOptions = {
        	beforeDrop : function (e) {
        		var source = e.source;
                var dest = e.dest;
                if (source && source.index != dest.index) {
                	var scObj = source.nodeScope.$modelValue;
                    var deObj = source.nodesScope.$modelValue[dest.index];
                    var scSort = scObj.sort;
                    var deSort = deObj.sort;
                    scObj.sort = deSort;
                    deObj.sort = scSort;
                    $http({
                        method  : 'POST',
                        url     : '/banner/save',
                        data    : scObj 
                       })
                       .success(function(response) {
                       });
                    $http({
                        method  : 'POST',
                        url     : '/banner/save',
                        data    : deObj 
                       })
                       .success(function(response) {
                       });
                }
        	}
        };
        
        var uploader = $scope.uploader = new FileUploader({
            url: '/common/UploadFile?dir=banner'
        });
           
        uploader.filters.push({
            name: 'customFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                return this.queue.length < 1;
            }
        });
        
        // CALLBACKS
        uploader.onCompleteItem = function(fileItem, response, status, headers) {
        	var fileInfo = {};
        	fileInfo.phyFileName = response;
        	fileInfo.logFileName = fileItem.file.name;
        	fileInfo.fileType = fileItem.file.type;
        	fileInfo.fileSize = fileItem.file.size;
        	fileInfo.filePath = '/banner';
        	
            $http({
                 method  : 'POST',
                 url     : '/fileInfo/save',
                 data    : fileInfo
                })
                .success(function(response) {
                	$scope.banner.fileInfoId = response.id;
                	$scope.fileInfo = response; 
                	uploader.clearQueue();
        		    $http({
        		      method  : 'POST',
        		      url     : '/banner/save',
        		      data    : $scope.banner 
        		     })
        		      .success(function(response) {
        		      });
                });
        };
        
        // 파일 삭제
        $scope.fileRemove = function () {
            $http({
                 method  : 'POST',
                 url     : '/fileInfo/delById?id=' + $scope.banner.fileInfoId,
                })
                .success(function(response) {
                	$scope.banner.fileInfoId = null;
                	$http({
          		      method  : 'POST',
          		      url     : '/banner/save',
          		      data    : $scope.banner 
          		     })
          		      .success(function(response) {
          		      });
                });
         };
		
	})
	;	
 
}());      
