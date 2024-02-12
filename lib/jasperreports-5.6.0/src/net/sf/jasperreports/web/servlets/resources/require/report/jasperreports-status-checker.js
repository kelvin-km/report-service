define(["jquery"], function($) {
	var StatusChecker = function(loader, updateInterval) {
        this.loader = loader;
        // timers
        this.timeoutId = null;

        this.updateInterval = updateInterval;
	};

	StatusChecker.prototype = {

        checkPageModified: function(page, pageTimestamp) {
            var it = this,
                deferred = $.Deferred();

            it._timedCheckPageModified(false, page, pageTimestamp, deferred, null);

            return deferred.promise();
        },

        cancelCheckPageModified: function() {
            clearTimeout(this.timeoutId);
        },

        // internal functions
        _timedCheckPageModified: function(booleanDone, pageNo, pageTimestamp, deferredObject, statusResult) {
            var it = this;
            if (!booleanDone) {
            	it.timeoutId = setTimeout(function() {
        			it._getPageModifiedStatus(pageNo, pageTimestamp, deferredObject);
        		}, it.updateInterval);
            } else {
                deferredObject.resolve(statusResult);
            }
        },

        _getPageModifiedStatus: function(page, pageTimestamp, deferredObject) {
            var it = this;
            return it.loader.getStatusForPage(page, pageTimestamp)
                .then(function(jsonData, textStatus, jqHXR) {
                    var booleanDone;
                    if(it.loader.config.stopOnFinishOnly) {
                        booleanDone = (jsonData.result.status == "finished");
                    } else {
                        booleanDone = (jsonData.result.pageModified || jsonData.result.status == "finished");
                    }
                    !booleanDone && it.loader.setPageUpdateStatus && it.loader.setPageUpdateStatus(jsonData);
                    it._timedCheckPageModified(booleanDone, page, pageTimestamp, deferredObject, jsonData.result);
                });
        }
	};
	
	return StatusChecker;
});
