'use strict';




// Declare app level module which depends on filters, and services
angular.module('myApp', ['myApp.filters','snmsadmin.services','ngCookies','ngResource','ui.bootstrap','ngGrid','ngRoute','myApp.directives','ui.bootstrap.datetimepicker','angularFileUpload']).
  config(['$routeProvider', '$locationProvider','$httpProvider', function($routeProvider, $locationProvider,$httpProvider) {
    $routeProvider.when("/preytimes", {templateUrl: "views/prey.html", controller: PreyAdminController});
    $routeProvider.when("/holidays", {templateUrl: "views/holidays.html", controller: HolidaysAdminController});
    $routeProvider.when("/news", {templateUrl: "views/news.html", controller: NewsAdminController});
	$routeProvider.when('/login', {
		templateUrl: 'views/login.html',
		controller: LoginController
	});
	$routeProvider.otherwise({
		redirectTo: '/news'
	});
	/* Register error provider that shows message on failed requests or redirects to login page on
	 * unauthenticated requests */
    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
	        return {
	        	'responseError': function(rejection) {
	        		var status = rejection.status;
	        		var config = rejection.config;
	        		var method = config.method;
	        		var url = config.url;
	      
	        		if (status == 401) {
	        			$location.path( "/login" );
	        		} else {
	        			$rootScope.error = method + " on " + url + " failed with status " + status;
	        		}
	              
	        		return $q.reject(rejection);
	        	}
	        };
	    }
    );
    
    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
     * as soon as there is an authenticated user */
    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
        return {
        	'request': function(config) {
        		var isRestCall = config.url.indexOf('rest') == 0;
        		if (isRestCall && angular.isDefined($rootScope.authToken)) {
        			var authToken = $rootScope.authToken;
        			if (true) {
        				config.headers['X-Auth-Token'] = authToken;
        			}
        		}
        		return config || $q.when(config);
        	}
        };
    }
);
    
    //$locationProvider.html5Mode(true);
  }]).run(function($rootScope, $location, $cookieStore, UserService) {
		
		/* Reset error when a new view is loaded */
		$rootScope.$on('$viewContentLoaded', function() {
			delete $rootScope.error;
		});
		
		$rootScope.hasRole = function(role) {
			
			if ($rootScope.user === undefined) {
				return false;
			}
			
			if ($rootScope.user.roles[role] === undefined) {
				return false;
			}
			
			return $rootScope.user.roles[role];
		};
		
		$rootScope.logout = function() {
			delete $rootScope.user;
			delete $rootScope.authToken;
			$cookieStore.remove('authToken');
			$location.path("/login");
		};
		
		 /* Try getting valid user from cookie or go to login page */
		var originalPath = $location.path();
		$location.path("/login");
		var authToken = $cookieStore.get('authToken');
		if (authToken !== undefined) {
			$rootScope.authToken = authToken;
			UserService.get(function(user) {
				$rootScope.user = user;
				$location.path(originalPath);
			});
		}
		
		$rootScope.initialized = true;
	});