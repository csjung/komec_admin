(function () {
	'use strict';
  
	angular.module('app').controller('BoardController', function ($scope, $http, $location, $window, $uibModal, FileUploader) {
		$scope.boardConfig = {};
		$scope.boardConfig.id = $location.search().boardConfigId;
		$scope.currentPage = 1;
	    $scope.pageSize = 10;
	    $scope.maxSize = 10;
	    $scope.q = '';
		// 게시판 정보 조회
		$http.get('/boardConfig/getBoardConfig?id=' + $scope.boardConfig.id).success(function(data) {
			$scope.boardConfig = data;
			$http.get('/boardData/getBoardDataListByConfigId?id=' + $scope.boardConfig.id).success(function(data) {
		      $scope.boards = data;
		      $scope.totalItems = data.length;
		    });
        });
		
		// 돌아가기
		$scope.back = function (scope) {
			$location.url('/boardConfig');
        };
        var uploader = new FileUploader({
            url: '/common/UploadFile?dir=board'
        });
        $scope.boardView = function (item) {
        	var modalInstance = $uibModal.open({
				animation: true,
				templateUrl: 'views/board/boardForm.html',
				controller: 'BoardEditModal',
				size: 'lg',
			    windowClass: 'big-modal',
				resolve: {
					boardData: item,
					boardConfig: $scope.boardConfig,
					uploader: uploader
				}
			});
        	modalInstance.result.then(function (selectedItem) {
	    		$http.get('/boardData/getBoardDataListByConfigId?id=' + $scope.boardConfig.id).success(function(data) {
	  		      $scope.boards = data;
	  		      $scope.totalItems = data.length;
	  		    });
		    }, function () {});
	  	};
	  	
	    // 신규
	    $scope.create = function () {
	    	var boardData = {};
	    	boardData.boardConfigId = $scope.boardConfig.id;
	    	var modalInstance = $uibModal.open({
				animation: true,
				templateUrl: 'views/board/boardForm.html',
				controller: 'BoardEditModal',
				size: 'lg',
			    windowClass: 'big-modal',
				resolve: {
					boardData: boardData,
					boardConfig: $scope.boardConfig,
					uploader: uploader
				}
			});
	    	modalInstance.result.then(function (selectedItem) {
	    		$http.get('/boardData/getBoardDataListByConfigId?id=' + $scope.boardConfig.id).success(function(data) {
	  		      $scope.boards = data;
	  		      $scope.totalItems = data.length;
	  		    });
		    }, function () {});
	  	};
		
	})
	.controller('BoardEditModal', function ($scope, $uibModalInstance, $http, $window, boardData, boardConfig, uploader) {
		$scope.boardConfig = boardConfig;
		$scope.boardData = boardData;
		$scope.uploader = uploader;
		uploader.clearQueue();
		$scope.boardFiles = []; 
		// 에디터 셋팅
		$scope.editorOptions = {
			height : 250,
			uiColor: '#cceaee'
		};
		
		$http.get('/boardFiles/getFiles?id=' +  $scope.boardData.id).success(function(data) {
            $scope.boardFiles = data;
            uploader.clearQueue();
        });
		
		// 저장
	    $scope.save = function() {
		    $http({
		      method  : 'POST',
		      url     : '/boardData/save',
		      data    : $scope.boardData 
		     }).success(function(response) {
		    	 angular.forEach($scope.boardFiles, function(value, key) {
		    		 value.boardDataId = response.id;
	    		     console.log(value);
	    		 });
		    	 $http({
				      method  : 'POST',
				      url     : '/boardFiles/saveList',
				      data    : $scope.boardFiles 
				     }).success(function(response) {
				    	  $uibModalInstance.close('save');
				     });
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
					$uibModalInstance.close('del');
				});
	    	}
	  	};
		
		// 닫기
	    $scope.cancel = function () {
	  	  $uibModalInstance.dismiss('cancel');
	  	};
	  	
	  	$scope.uploader.filters.push({
            name: 'customFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                return this.queue.length < 10;
            }
        });
	  	
	    // CALLBACKS
        uploader.onCompleteItem = function(fileItem, response, status, headers) {
        	var fileInfo = {};
        	fileInfo.phyFileName = response;
        	fileInfo.logFileName = fileItem.file.name;
        	fileInfo.fileType = fileItem.file.type;
        	fileInfo.fileSize = fileItem.file.size;
        	fileInfo.filePath = '/board';
        	
            $http({
                 method  : 'POST',
                 url     : '/fileInfo/save',
                 data    : fileInfo
                })
                .success(function(response) {
                	var boardFiles = {};
                	boardFiles.fileInfoId = response.id;
                	boardFiles.fileInfo = fileInfo;
                	$scope.boardFiles.push(boardFiles);
                });
        };
        
        uploader.onCompleteAll = function() {
        	uploader.clearQueue();
        };
        
        $scope.fileRemove = function (item) {
		    $http({
		         method  : 'POST',
		         url     : '/boardFiles/del',
		         data    : {
		                     "id": item.id,
		                     "fileInfoId": item.fileInfoId,
		                   }
		        })
		        .success(function(response) {
		           $scope.boardFiles.splice($scope.boardFiles.indexOf(item), 1);
		        });
		 };
	})
	;	
 
}()); 