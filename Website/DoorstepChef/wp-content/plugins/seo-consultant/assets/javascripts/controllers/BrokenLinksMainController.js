/**
 * Created by ketchupthemes.com
 */

;(function(){
	//Do code
	var BrokenLinksMainCtrl = function($scope, asyncService){
		$scope.links = {};
		$scope.NoBrokenLinksMessage = 'No Broken Links Detected yet!';
	}

	//Module Referense
	var BrokenLinks = angular.module('BrokenLinks');

	//Controller Declaration
   BrokenLinks.controller('BrokenLinksMainCtrl', ['$scope', 'asyncService', BrokenLinksMainCtrl]);
}());