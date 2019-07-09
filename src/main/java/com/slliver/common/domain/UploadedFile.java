package com.slliver.common.domain;

import java.util.Arrays;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/15 11:13
 * @version: 1.0
 */
public class UploadedFile {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 4120024284373588453L;

    // 文件大小
    private int length;
    // 文件内容
    private byte[] bytes;
    // 文件名称
    private String name;
    // 文件类型
    private String type;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(bytes);
        result = prime * result + length;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UploadedFile other = (UploadedFile) obj;
        if (!Arrays.equals(bytes, other.bytes))
            return false;
        if (length != other.length)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UploadedFile [length=" + length + ", bytes="
                + Arrays.toString(bytes) + ", name=" + name + ", type=" + type
                + "]";
    }
}
