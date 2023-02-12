package com.acq.collection.acqcollectionbook.common.log;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
@Service
public class AccessLogServiceImpl extends EgovAbstractServiceImpl implements AccessLogService {
    @Override
    public void setUserAccessLog(HttpServletRequest request, String methodName) {

    }
}
