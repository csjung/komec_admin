'use strict';
var app = angular.module('app', ['ui.tree', 'ngRoute', 'ui.bootstrap', 'ui.grid', 'ngAnimate', 'ngCkeditor', 'angularFileUpload']);

app.config(function($routeProvider, $locationProvider) {
	
	$routeProvider
		.when('/admin', {templateUrl: 'views/admin/admin.html',controller: 'AdminController'})
		.when('/site', {templateUrl: 'views/site/site.html',controller: 'SiteController'})
		.when('/group', {templateUrl: 'views/group/group.html',controller: 'GroupController'})
		.when('/menu', {templateUrl: 'views/menu/menu.html',controller: 'MenuController'})
		.when('/contents', {templateUrl: 'views/contents/contents.html',controller: 'ContentsController'})
		.when('/boardConfig', {templateUrl: 'views/boardConfig/boardConfig.html',controller: 'BoardConfigController'})
		.otherwise({redirectTo: '/admin'});
});

/*
app.directive('ckEditor', function () {
  return {
    require: '?ngModel',
    link: function (scope, elm, attr, ngModel) {
      var ck = CKEDITOR.replace(elm[0]);
      if (!ngModel) return;
      ck.on('instanceReady', function () {
        ck.setData(ngModel.$viewValue);
      });
      function updateModel() {
        scope.$apply(function () {
          ngModel.$setViewValue(ck.getData());
        });
      }
      //ck.on('change', updateModel);
      ck.on('key', updateModel);
      ck.on('dataReady', updateModel);
      ck.on('pasteState', updateModel);
      
      ngModel.$render = function (value) { 
    	if (ngModel.$viewValue == null) {
    		ck.setData('');
    	} else {
    		ck.setData(ngModel.$viewValue);
    	} 
      };
    }
  };
});
*/

app.filter('useStateFilter', function () {
  return function (value) {
	  if (value == 'USE') {
		  return '사용';
	  } else {
		  return '사용안함';
	  }
    
  };
});

app.filter('boardTypeFilter', function () {
	  return function (value) {
		  if (value == 'COMMON') {
			  return '일반';
		  } else if (value == 'IMAGE') {
			  return '이미지 게시판';
		  } else {
			  return '달력 게시판';
		  }
	    
	  };
	});

