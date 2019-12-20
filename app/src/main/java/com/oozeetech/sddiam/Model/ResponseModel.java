package com.oozeetech.sddiam.Model;

public class ResponseModel {
    private String returnCode,ApiStatus,StatusMsg,SuccessResult,returnMsg,returnValue;
    private DataModel data;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getApiStatus() {
        return ApiStatus;
    }

    public void setApiStatus(String apiStatus) {
        ApiStatus = apiStatus;
    }

    public String getStatusMsg() {
        return StatusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        StatusMsg = statusMsg;
    }

    public String getSuccessResult() {
        return SuccessResult;
    }

    public void setSuccessResult(String successResult) {
        SuccessResult = successResult;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }
}
