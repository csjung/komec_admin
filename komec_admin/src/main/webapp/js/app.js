'use strict';
var app = angular.module('app', ['ui.tree', 'ngRoute', 'ui.bootstrap', 'ui.grid', 'ngAnimate', 'ngCkeditor', 'angularFileUpload', 'ngSanitize']);

app.config(function($routeProvider, $locationProvider) {
	
	$routeProvider
		.when('/admin', {templateUrl: 'views/admin/admin.html',controller: 'AdminController'})
		.when('/site', {templateUrl: 'views/site/site.html',controller: 'SiteController'})
		.when('/group', {templateUrl: 'views/group/group.html',controller: 'GroupController'})
		.when('/menu', {templateUrl: 'views/menu/menu.html',controller: 'MenuController'})
		.when('/contents', {templateUrl: 'views/contents/contents.html',controller: 'ContentsController'})
		.when('/boardConfig', {templateUrl: 'views/boardConfig/boardConfig.html',controller: 'BoardConfigController'})
		.when('/bannerZone', {templateUrl: 'views/bannerZone/bannerZone.html',controller: 'BannerZoneController'})
		.when('/banner', {templateUrl: 'views/banner/banner.html',controller: 'BannerController'})
		.otherwise({redirectTo: '/admin'});
});

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

app.filter('startFrom', function() {
    return function(input, start) {
    	if (input) {
    		start = +start; //parse to int
            return input.slice(start);
    	}
    }
});