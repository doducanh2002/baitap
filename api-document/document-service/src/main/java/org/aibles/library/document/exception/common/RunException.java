package org.aibles.library.document.exception.common;

public class RunException extends RuntimeException {

    private int status = 0;
    private String message = "";
//    private Map<String, String> param;

    public RunException() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public void addParam(String key , String value ) {
//        if(param == null) {
//            param = new HashMap<>();
//        }
//
//        param.put(key, value);
//    }


}
