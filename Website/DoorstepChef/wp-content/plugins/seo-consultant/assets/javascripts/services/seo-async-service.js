/**
 * Created by ketchupthemes.com
 */

;(function(){
	//Code
    var Referrers = angular.module('Referrers');
    var BrokenLinks = angular.module('BrokenLinks');
    var Stats = angular.module('Statistics');
    var ReferringPages = angular.module('ReferringPages');
    var BlockedIPs = angular.module('BlockedIPs');
    var AnchorDistribution = angular.module('AnchorDistribution');


    var SeoAsyncService = function ($http, $interval) {

        var config = {
            'interval' : 2500
        }
        var makeRequest = function (data) {
            var request = {
                url: seovars.admin,
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'},
                params: {action: 'seocons_handle_request'},
                'data': serializeData(data)
            };

            return $http(request).success(function (data) {
                return data;
            });
        };

        var getHttpReferers = function (filter) {
            return makeRequest({
                'operation': 'getDomains',
                'access_key': seovars.access_key,
                'data': serializeData(filter)
            });
        }

        var getLog = function () {
            return makeRequest({
                'operation': 'getLog',
                'access_key': seovars.access_key,
                'data': serializeData({})
            });
        };

        var deleteIndex = function (data) {
            return makeRequest({
                'operation': 'deleteIndex',
                'access_key': seovars.access_key,
                'data': serializeData(data)
            });
        };

        var deleteLog = function(data) {
            return makeRequest({
                'operation': 'deleteLog',
                'access_key': seovars.access_key,
                'data': serializeData(data)
            });
        };

        var blockIP = function (data) {
            return makeRequest({
                'operation': 'blockByID',
                'access_key': seovars.access_key,
                'data': serializeData(data)
            });
        };

        var unblockIP = function (data) {
            return makeRequest({
                'operation': 'unBlockByID',
                'access_key': seovars.access_key,
                'data': serializeData(data)
            });
        };

        var getFollowStats = function () {
            return makeRequest({
                'operation' : 'getStatisticsOfFollow',
                'access_key' : seovars.access_key,
                'data' : serializeData({})
            });
        };

        var getBacklinksQnt = function () {
            return makeRequest({
                'operation' : 'getStaticsticsOfBacklinksQnt',
                'access_key' : seovars.access_key,
                'data' : serializeData({})
            });
        };

        var getDomainsQnt = function () {
            return makeRequest({
                'operation' : 'getStaticsticsOfRefDomainsQnt',
                'access_key' : seovars.access_key,
                'data' : serializeData({})
            });
        }

        var getAnchorTextDistribution = function () {
            return makeRequest({
                'operation' : 'getStaticsticsOfAnchorTextDistribution',
                'access_key' : seovars.access_key,
                'data' : serializeData({})
            });
        }

        function serializeData(data) {
            // If this is not an object, defer to native stringification.
            if (!angular.isObject(data)) {
                return ( ( data == null ) ? "" : data.toString() );
            }
            var buffer = [];
            // Serialize each key in the object.
            for (var name in data) {
                if (!data.hasOwnProperty(name)) {
                    continue;
                }
                var value = data[name];
                buffer.push(encodeURIComponent(name) + "=" + encodeURIComponent(( value == null ) ? "" : value));
            }
            // Serialize the buffer and clean it up for transportation.
            var source = buffer
                .join("&")
                .replace(/%20/g, "+");
            return ( source );
        }


        return {
            config : config,
            getHttpReferers: getHttpReferers,
            blockIP: blockIP,
            unblockIP: unblockIP,
            getLog: getLog,
            deleteIndex : deleteIndex,
            deleteLog : deleteLog,
            getFollowStats : getFollowStats,
            getBacklinksQnt : getBacklinksQnt,
            getDomainsQnt : getDomainsQnt,
            getAnchorTextDistribution : getAnchorTextDistribution
        }
    }



    //Bind asyncServive for the modules
    Referrers.factory('asyncService', ['$http', '$interval', SeoAsyncService]);
    BrokenLinks.factory('asyncService', ['$http', '$interval', SeoAsyncService]);
    Stats.factory('asyncService', ['$http', '$interval', SeoAsyncService]);
    ReferringPages.factory('asyncService', ['$http', '$interval', SeoAsyncService]);
    BlockedIPs.factory('asyncService', ['$http', '$interval', SeoAsyncService]);
    AnchorDistribution.factory('asyncService', ['$http', '$interval', SeoAsyncService]);
}());





