/**
 * Created by ketchupthemes.com
 */

;(function(){
    var blockedIPsMainCtrl = function ($scope, $interval, asyncService) {

        var getBlocked = function () {
            $interval.cancel(stop);
            stop = null;
            asyncService.getHttpReferers({'is_blocked' : 1}).success(function (data) {
                var _temp = data;
                $scope.domains = _temp;
                if(data) {
                    for (cnt in _temp) {
                        _temp[cnt].ID = parseInt(_temp[cnt].ID);
                    }
                    $scope.domains = _temp;
                }
                stop = $interval(getBlocked,asyncService.config.interval);
            });
        };
        var stop = $interval(getBlocked,asyncService.config.interval);

        $scope.unblock = function(id){
            $interval.cancel(stop);
            asyncService.unblockIP({'ID' : id}).success(function(data){
                stop = $interval(getBlocked,asyncService.config.interval);
            });
        };

        $scope.del = function (id) {
            $interval.cancel(stop);
            asyncService.deleteIndex({'ID': id}).success(function (data) {
                if(data){
                    stop = $interval(getRefs,asyncService.config.interval);
                }
            });
        }

        $scope.changeOrder = function (order) {
            if (order == $scope.column) {
                $scope.column = '-' + order;
            }
            else {
                $scope.column = order;
            }
        }

    }


    var blockedIPs = angular.module('BlockedIPs');
    blockedIPs.controller('BlockedIPsMainCtrl', ['$scope', '$interval', 'asyncService', blockedIPsMainCtrl]);
}());

