package org.cloud.activiti.redis.exception;

public class PrincipalInstanceException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "We need a field to identify this Cache Object in Redis. "
            + "So you need to defined an id field which you can get unique id to identify this principal. "
            + "For example, if you use UserInfo as Principal class, the id field maybe userId, userName, email, etc. "
            + "For example, getUserId(), getUserName(), getEmail(), etc.\n"
            + "Default value is \"id\", that means your principal object has a method called \"getId()\"";

    @SuppressWarnings("rawtypes")
    public PrincipalInstanceException(Class clazz, String idMethodName) {
        super(clazz + " must has getter for field: " + idMethodName + "\n" + MESSAGE);
    }

    @SuppressWarnings("rawtypes")
    public PrincipalInstanceException(Class clazz, String idMethodName, Exception e) {
        super(clazz + " must has getter for field: " + idMethodName + "\n" + MESSAGE, e);
    }
}
