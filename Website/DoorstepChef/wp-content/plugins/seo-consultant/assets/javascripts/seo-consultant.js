;
(function () {

    //Initialize Modules
    var MainModule = angular.module('MainModule', []);
    var referers = angular.module('Referrers', ['MainModule']);
    var brokenLinks = angular.module('BrokenLinks', ['MainModule']);
    var stats = angular.module('Statistics', ['MainModule']);
    var referringPages = angular.module('ReferringPages', ['MainModule']);
    var blockedIPs = angular.module('BlockedIPs', ['MainModule']);
    var AnchorDistribution = angular.module('AnchorDistribution', ['MainModule']);


    //Directives
    var AskButton = function () {
        return {
            restrict: 'E',
            template: '<hr /><div class="ask-the-pros"><p style="font-size: 17px; font-weight: bold;">Have Questions?</p><label>Check Our FB Group</label><a href="https://www.facebook.com/groups/ifeedgr" target="_blank" class="button button-primary button-large seo-button warning">Ask Now</a></div>'
        };
    }

    MainModule.directive('askButton', AskButton);
}());

