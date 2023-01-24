package com.addyai.addyaiservice.services.campaign;

import com.addyai.addyaiservice.models.CampaignDetails;
import com.addyai.addyaiservice.models.documents.CampaignDocument;
import com.addyai.addyaiservice.models.documents.CampaignMetricsDocument;
import com.addyai.addyaiservice.repos.CampaignMetricsRepository;
import com.addyai.addyaiservice.repos.CampaignRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepository;

    private final CampaignMetricsRepository campaignMetricsRepository;

    public CampaignServiceImpl(CampaignRepository campaignRepository, CampaignMetricsRepository campaignMetricsRepository) {
        this.campaignRepository = campaignRepository;
        this.campaignMetricsRepository = campaignMetricsRepository;
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

    @Override
    public List<CampaignMetricsDocument> fetchMetricsByDateRange(String customerId, String campaignResourceName, String startDate, String endDate) throws ParseException {
        Date sDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date eDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

        return campaignMetricsRepository.findAllMetricsInDateRange(campaignResourceName, sDate, eDate);
    }
}
