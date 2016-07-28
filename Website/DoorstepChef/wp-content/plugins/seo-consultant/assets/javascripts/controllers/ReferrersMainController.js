/**
 * Created by ketchupthemes.com
 */
;(function(){
	//Do code
	var ReferrersMainCtrl = function($scope, $interval, asyncService){
		//Controller Code
        var getRefs = function(){
            $interval.cancel(stop);
            stop = null;
            asyncService.getHttpReferers({'is_blocked' : 0}).success(function (data) {
                var _temp = data;
                $scope.domains = {};
                if(data) {
                    for (cnt in _temp) {
                        _temp[cnt].ID = parseInt(_temp[cnt].ID);
                    }
                    $scope.domains = _temp;
                }
                stop = $interval(getRefs,asyncService.config.interval);
            });
        }
        var stop = $interval(getRefs,asyncService.config.interval);

        $scope.del = function (id) {
            $interval.cancel(stop);
            asyncService.deleteIndex({'ID': id}).success(function (data) {
               if(data){
                   stop = $interval(getRefs,asyncService.config.interval);
               }
            });
        }

        $scope.block = function (id) {
            $interval.cancel(stop);
            asyncService.blockIP({'ID' : id}).success(function(data){
                stop = $interval(getRefs,asyncService.config.interval);
            });
        };

        $scope.info = function (id, domain_name, ip) {
            var anchor = document.getElementById('infobox');
            $scope.domain_id = id;
            $scope.heading = domain_name;
            $scope.subheading = ip;

            //Trigger Click and Configure Modal
            anchor.click();
            var tbwindow = document.getElementById('TB_window');
            tbwindow.style.backgroundImage = 'none';
        };

        $scope.changeOrder = function (order) {
            if (order == $scope.column) {
                $scope.column = '-' + order;
            }
            else {
                $scope.column = order;
            }
        };


	}
	
	//Module Referense
	var Referrers = angular.module('Referrers');
	
	//Controller Declaration
   Referrers.controller('ReferrersMainCtrl', ['$scope', '$interval', 'asyncService', ReferrersMainCtrl]);
}());
