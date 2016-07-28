/**
 * Created by ketchupthemes.com
 */

;(function(){
	//Do code
	var AnchorDistributionMainCtrl = function($scope, $interval, asyncService){
	    var getAnchors = function () {
            asyncService.getAnchorTextDistribution().success(function(data){
                $scope.distribution = data;
            });
        };

        $scope.changeOrder = function (order) {
            if (order == $scope.column) {
                $scope.column = '-' + order;
            }
            else {
                $scope.column = order;
            }
        };

        var clock = $interval(getAnchors, asyncService.config.interval);
	}


	
	//Module Referense
	var AnchorDistribution = angular.module('AnchorDistribution');
	
	//Controller Declaration
   AnchorDistribution.controller('AnchorDistributionMainCtrl', ['$scope', '$interval', 'asyncService', AnchorDistributionMainCtrl]);
}());