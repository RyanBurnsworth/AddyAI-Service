package com.addyai.addyaiservice.models.assets;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SitelinkDetails.class, name = "sitelinks"),
        @JsonSubTypes.Type(value = CallExtensionDetails.class, name = "call")
})
public abstract class AssetDetails {
    private long assetId;
    private String assetName;
    private int assetType;
    private int assetSource;

    public long getAssetId() {
        return assetId;
    }

    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public int getAssetType() {
        return assetType;
    }

    public void setAssetType(int assetType) {
        this.assetType = assetType;
    }

    public int getAssetSource() {
        return assetSource;
    }

    public void setAssetSource(int assetSource) {
        this.assetSource = assetSource;
    }
}
