package com.addyai.addyaiservice.controller.block;

import com.addyai.addyaiservice.models.Block;
import org.springframework.http.ResponseEntity;

public interface BlockController {
    ResponseEntity<Block> getAccountBlock(String customerId, String startDate, String endDate);
}
