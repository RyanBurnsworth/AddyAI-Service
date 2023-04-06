package com.addyai.addyaiservice.controller.block.impl;

import com.addyai.addyaiservice.controller.block.BlockController;
import com.addyai.addyaiservice.models.Block;
import com.addyai.addyaiservice.models.requests.MetricsByDateRequest;
import com.addyai.addyaiservice.services.block.BlockService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/{customerId}/block")
public class BlockControllerImpl implements BlockController {
    private final BlockService blockService;

    @Override
    @GetMapping("/account")
    public ResponseEntity<Block> getAccountBlock(@PathVariable String customerId,
                                                 @RequestParam String startDate,
                                                 @RequestParam String endDate) {
        MetricsByDateRequest metricsByDateRequest = new MetricsByDateRequest();
        metricsByDateRequest.setStartDate(startDate);
        metricsByDateRequest.setEndDate(endDate);
        metricsByDateRequest.setCustomerId(customerId);
        metricsByDateRequest.setResourceType("account");
        metricsByDateRequest.setResourceId("");

        Block block = blockService.fetchUIBlock(metricsByDateRequest);
        return new ResponseEntity<>(block, HttpStatus.OK);
    }
}
