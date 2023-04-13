package com.addyai.addyaiservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountBasics {

    List<ParentResource> campaigns;
    List<ChildResource> adGroups;
    List<FinalUrl> finalUrls;
    List<Keyword> top10KeywordsByAdGroup;
    List<Ad> topAdsByAdGroup;
    List<Extension> extensions;

    @Getter
    @Setter
    public static class ParentResource {
        String name;
        String resourceName;
    }

    @Getter
    @Setter
    public static class ChildResource {
        String name;

        String parentResourceName;

        String resourceName;
    }

    @Getter
    @Setter
    public static class FinalUrl {
        String parentResourceName;

        String finalUrl;
    }

    @Getter
    @Setter
    public static class Keyword {
        String resourceName;

        String parentResourceName;

        String text;
    }

    @Getter
    @Setter
    public static class Ad {
        String resourceName;

        String parentResourceName;

        String headline1;

        String headline2;

        String headline3;

        String description1;

        String description2;

        String path1;

        String path2;

        String finalUrl;

        int clicks;

        String ctr;

        String cpc;

        int conversions;

        String conversionRate;
    }

    @Getter
    @Setter
    public static class Extension {
        String resourceName;

        String parentResourceName;

        String type;

        String link;

        String text;
    }
}
