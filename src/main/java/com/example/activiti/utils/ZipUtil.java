package com.example.activiti.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class ZipUtil {

    // ZIP文件魔术数（前4字节）
    private static final byte[] ZIP_MAGIC_NUMBER = {0x50, 0x4B, 0x03, 0x04};

    /**
     * 校验MultipartFile是否为有效的ZIP文件
     * @param file 上传的文件
     * @param checkCRC 是否校验文件CRC（更严格，耗时更长）
     * @return 校验结果（true=有效）
     * @throws IOException 流操作异常
     */
    public static boolean isValidZip(MultipartFile file) throws IOException {
        // 1. 检查文件是否为空
        if (file.isEmpty()) {
            return false;
        }

        // 2. 检查文件后缀（可选，辅助判断）
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".zip")) {
            return false;
        }

        // 3. 验证ZIP魔术数（文件头）
        try (InputStream is = file.getInputStream()) {
            byte[] header = new byte[4];
            int read = is.read(header);
            if (read != 4) { // 读取的字节数不足4，不是有效文件
                return false;
            }
            for (int i = 0; i < 4; i++) {
                if (header[i] != ZIP_MAGIC_NUMBER[i]) {
                    return false; // 魔术数不匹配，不是ZIP文件
                }
            }
        }

        return true;
    }

}
