package com.bhagwat.retail.community.entity;

import com.bhagwat.retail.community.enums.CommunityType;
import com.bhagwat.retail.community.enums.InterestCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.suggest.Completion;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "communities")
//@Setting(settingPath = "/elasticsearch/settings/community-settings.json")  // Optional for custom analyzers
public class CommunityDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String communityName;

    @Field(type = FieldType.Keyword)
    private String communityUid;

    @Field(type = FieldType.Keyword)
    private CommunityType type;

    @Field(type = FieldType.Keyword)
    private InterestCategory interestCategory;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String location;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime createdDate;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime updatedDate;

    @Field(type = FieldType.Keyword)
    private Set<String> keywords;

    @CompletionField(maxInputLength = 100)
    private Completion suggest;

    @Transient // This field will not be persisted in Elasticsearch
    private float score;
}
