/**
 * Created by ketchupthemes.com
 */


;(function(){
	//Do code
	var StatsMainCtrl = function($scope, $interval, asyncService){
		$scope.LostBackLinks = 0;
		$scope.LostRefDomains = 0;

		var getFollowStats = function(){
			asyncService.getFollowStats().success(function(data){
				var _temp = data;
				for(item in _temp){
					_temp[item].NOFOLLOW = Math.round( _temp[item].NOFOLLOW * 100 );
					_temp[item].FOLLOW = Math.round( _temp[item].FOLLOW * 100 );
				}
				$scope.followstats = _temp[0];
			});
		}

		var getBacklinksQnt = function(){
			asyncService.getBacklinksQnt().success(function(data){
				$scope.linksQnt = data[0].LINKS;
			});
		}

		var getDomainsQnt = function(){
			asyncService.getDomainsQnt().success(function(data){
				$scope.domainsQnt = data[0].DOMAINS;
			});
		}

		var stop = $interval(function(){
			getFollowStats();
			getBacklinksQnt();
			getDomainsQnt();
		}, asyncService.config.interval);

	}
	
	//Module Referense
	var Statistics = angular.module('Statistics');
	
	//Controller Declaration
	Statistics.controller('StatsMainCtrl', ['$scope', '$interval', 'asyncService', StatsMainCtrl]);
}());
