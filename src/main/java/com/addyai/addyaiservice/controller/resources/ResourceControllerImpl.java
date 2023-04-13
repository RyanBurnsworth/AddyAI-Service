package com.addyai.addyaiservice.controller.resources;

import com.addyai.addyaiservice.models.AccountBasics;
import com.addyai.addyaiservice.services.fetch.FetchingService;
import com.addyai.addyaiservice.services.proxy.ProxyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/{customerId}/resource")
public class ResourceControllerImpl implements ResourceController {
    private final FetchingService fetchingService;
    private final ProxyService proxyService;

    public ResourceControllerImpl(FetchingService fetchingService, ProxyService proxyService) {
        this.fetchingService = fetchingService;
        this.proxyService = proxyService;
    }

    @Override
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/basics")
    public ResponseEntity<AccountBasics> getAccountBasics(@PathVariable String customerId) {
        AccountBasics accountBasics = fetchingService.getAccountBasics(customerId);
        return ResponseEntity.ok(accountBasics);
    }

    @Override
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/ad")
    public ResponseEntity<Void> createAd(@PathVariable String customerId,
                                         @RequestBody AccountBasics.Ad ad) {
        ResponseEntity<Void> response = proxyService.createAd(customerId, ad);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return ResponseEntity.accepted().build();
        } else {
            return response;
        }
    }

    @Override
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("validate/ad")
    public ResponseEntity<List<String>> validateAdResource(@PathVariable String customerId,
                                                           @RequestBody AccountBasics.Ad ad) {
        List<String> errors = this.proxyService.validateAd(customerId, ad);

        return new ResponseEntity<>(errors, HttpStatus.OK);
    }
}
