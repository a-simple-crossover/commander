package com.master.commander.user.config;

public class JsonViewConfig {
    // 内部视图，用于服务层之间的数据传输，包含所有字段
    public static class Internal {}
    
    // 外部视图，用于返回给前端，包含脱敏后的数据
    public static class External {}
} 