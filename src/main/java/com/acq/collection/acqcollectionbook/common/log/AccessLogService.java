package com.acq.collection.acqcollectionbook.common.log;

import javax.servlet.http.HttpServletRequest;

public interface AccessLogService {
    void setUserAccessLog(HttpServletRequest request, String methodName);
}
