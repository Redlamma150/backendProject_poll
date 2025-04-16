package com.example.backendProject.model;

public class ResponseDTO<T> {
    private String description;
    private T data;

    public ResponseDTO(String description, T data) {
        this.description = description;
        this.data = data;
    }

    public ResponseDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "description='" + description + '\'' +
                ", data=" + data +
                '}';
    }
}
