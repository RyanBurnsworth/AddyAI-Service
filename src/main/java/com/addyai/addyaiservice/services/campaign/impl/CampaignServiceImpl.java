package com.addyai.addyaiservice.services.campaign.impl;

import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.documents.CampaignDocument;
import com.addyai.addyaiservice.repos.CampaignRepository;
import com.addyai.addyaiservice.services.campaign.CampaignService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepository;

    public CampaignServiceImpl(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public List<CampaignDetails> fetchAllCampaignDetails(String customerId) {
        List<CampaignDetails> campaignDetailsList = new ArrayList<>();
        List<CampaignDocument> campaignDocumentList
                = campaignRepository.findAllCampaignDocumentsByCustomerId(customerId);

        campaignDocumentList.forEach(campaignDocument -> {
            campaignDetailsList.add(campaignDocument.getCampaignDetails());
        });

        return campaignDetailsList;
    }

    @Override
    public CampaignDetails fetchCampaignDetailsByName(String customerId, String campaignName) {
        CampaignDocument campaignDocument
                = campaignRepository.findCampaignDocumentByName(customerId, campaignName);

        return campaignDocument.getCampaignDetails();
    }
}
