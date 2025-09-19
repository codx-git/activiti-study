package com.example.activiti.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // JSON序列化时忽略null值字段
public class ApiResponse<T> {

    // 状态码 (200成功, 500失败等)
    private int code;

    // 业务状态 (SUCCESS/ERROR)
    private String status;

    // 提示信息
    private String message;

    // 时间戳
    private LocalDateTime timestamp;

    // 响应数据
    private T data;

    // 分页信息 (可选)
    private Pagination pagination;

    private ApiResponse(int code, String status, String message, T data, Pagination pagination) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
        this.pagination = pagination;
        this.timestamp = LocalDateTime.now();
    }

    // 成功响应 (带数据)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "SUCCESS", "操作成功", data, null);
    }

    // 成功响应 (带数据和分页)
    public static <T> ApiResponse<T> success(T data, Pagination pagination) {
        return new ApiResponse<>(200, "SUCCESS", "操作成功", data, pagination);
    }

    // 失败响应 (自定义错误码和消息)
    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, "ERROR", message, null, null);
    }

    // 分页信息内部类
    @Data
    public static class Pagination {
        private long total;      // 总条数
        private int pageSize;    // 每页大小
        private int pageNum;     // 当前页码
        private long totalPages; // 总页数
    }
}