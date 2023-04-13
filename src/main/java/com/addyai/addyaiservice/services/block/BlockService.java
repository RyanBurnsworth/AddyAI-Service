package com.addyai.addyaiservice.services.block;

import com.addyai.addyaiservice.models.Block;
import com.addyai.addyaiservice.models.requests.MetricsByDateRequest;

public interface BlockService {
    Block fetchUIBlock(MetricsByDateRequest request);
}
