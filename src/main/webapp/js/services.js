'use strict';

var services = angular.module('snmsadmin.services', ['ngResource']);


services.factory('UserService', function($resource) {
	
	return $resource('rest/admin/user/:action', {},
			{
				authenticate: {
					method: 'POST',
					params: {'action' : 'authenticate'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				},
			}
		);
});

