package com.slliver.common.exception;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/5 13:44
 * @version: 1.0
 */
public class RQException extends RuntimeException {

    private static final long serialVersionUID = 842505820282048314L;

    public static final int CATEGORY_SYSTEM = 0;
    public static final int CATEGORY_CUSTOM = 1;

    /**
     * 异常类型
     */
    protected int category;

    /**
     * 异常信息
     */
    protected String message = null;

    /**
     * 异常对象
     */
    protected Throwable throwable = null;

    /**
     * @param message
     *            异常信息
     */
    public RQException(String message) {
        this(message, null);
    }

    /**
     * @param category
     *            异常类型
     * @param message
     *            异常信息
     */
    public RQException(int category, String message) {
        this(category, message, null);
    }

    /**
     * @param message
     *            异常信息
     * @param throwable
     *            异常对象
     */
    public RQException(String message, Throwable throwable) {
        this(CATEGORY_SYSTEM, message, throwable);
    }

    /**
     * @param category
     *            异常类型
     * @param message
     *            异常信息
     * @param throwable
     *            异常对象
     */
    public RQException(int category, String message, Throwable throwable) {
        this.category = category;
        this.message = message;
        this.throwable = throwable;
    }

    /**
     * 取得异常类型
     *
     * @return 异常类型
     */
    public int getCategory() {
        return category;
    }

    /**
     * 取得异常信息
     *
     * @return 异常信息
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 取得异常对象
     *
     * @return 异常对象
     */
    public Throwable getThrowable() {
        return throwable;
    }

}
