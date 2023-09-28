package com.addyai.addyaiservice.controller.details;

import com.addyai.addyaiservice.models.AccountDetails;
import com.addyai.addyaiservice.services.fetch.FetchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/{customerId}")
public class DetailsControllerImpl implements DetailsController {
    private final FetchingService fetchingService;

    public DetailsControllerImpl(FetchingService fetchingService) {
        this.fetchingService = fetchingService;
    }

    @Override
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDetails>> fetchAllAccountDetails(@PathVariable String customerId) {
        List<AccountDetails> accountDetailsList = new ArrayList<>();

        AccountDetails accountDetails = this.fetchingService.getAccountDetails(customerId);
        accountDetailsList.add(accountDetails);
        return ResponseEntity.ok(accountDetailsList);
    }

    @Override
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/")
    public ResponseEntity<AccountDetails> fetchSingleAccountDetails(@PathVariable String customerId) {
        AccountDetails accountDetails = this.fetchingService.getAccountDetails(customerId);
        return ResponseEntity.ok(accountDetails);
    }
}
