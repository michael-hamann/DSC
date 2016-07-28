/**
 * Created by ketchupthemes.com
 */
;
(function () {
    //Do code
    var ReferringPagesMainCtrl = function ($scope, $interval, asyncService) {
        $interval.cancel(stop);
        //Controller Code
        var getLog = function () {
            $interval.cancel(stop);
            stop = null;
            asyncService.getLog().success(function (data) {
                $scope.logs = data;
                stop = $interval(getLog, asyncService.config.interval);
            });
        }
        var stop = $interval(getLog, asyncService.config.interval);
        $scope.del = function (id) {
            $interval.cancel(stop);
            asyncService.deleteLog({'ID': id}).success(function (data) {
                stop = $interval(getLog, asyncService.config.interval);
            });
        }

        $scope.info = function (id) {
        }

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
    var ReferringPages = angular.module('ReferringPages');

    //Controller Declaration
    ReferringPages.controller('ReferringPagesMainCtrl', ['$scope', '$interval', 'asyncService', ReferringPagesMainCtrl]);
}());