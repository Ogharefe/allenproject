package com.allen.product.domain.util;

import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class SkuGeneration {

    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SKU_LENGTH = 8;
    private final Random random = new Random();

    public String generateRandomSku() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SKU_LENGTH; i++) {
            int index = random.nextInt(CHAR_POOL.length());
            sb.append(CHAR_POOL.charAt(index));
        }
        return sb.toString();
    }
}
