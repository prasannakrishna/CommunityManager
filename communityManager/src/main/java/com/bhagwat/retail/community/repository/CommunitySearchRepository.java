package com.bhagwat.retail.community.repository;

import com.bhagwat.retail.community.entity.CommunityDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunitySearchRepository extends ElasticsearchRepository<CommunityDocument, String> {
    List<CommunityDocument> findByKeywordsContaining(String keyword);


    @Query("""
            {
              "bool": {
                "must": [
                  {
                    "match": {
                      "keywords": {
                        "query": "?0",
                        "fuzziness": "AUTO"
                      }
                    }
                  },
                  {
                    "term": {
                      "interestCategory.keyword": "?1"
                    }
                  }
                ]
              }
            }
            """)
    List<CommunityDocument> findByKeywordsAndInterestCategory(String keyword, String interestCategory);

    @Query("""
{
  "function_score": {
    "query": {
      "bool": {
        "must": [
          {
            "bool": {
              "should": [
                { "match": { "keywords": { "query": "?0[0]", "fuzziness": "AUTO" } } },
                { "match": { "keywords": { "query": "?0[1]", "fuzziness": "AUTO" } } },
                { "match": { "keywords": { "query": "?0[2]", "fuzziness": "AUTO" } } },
                { "match": { "keywords": { "query": "?0[3]", "fuzziness": "AUTO" } } },
                { "match": { "keywords": { "query": "?0[4]", "fuzziness": "AUTO" } } },
                { "match": { "keywords": { "query": "?0[5]", "fuzziness": "AUTO" } } },
                { "match": { "keywords": { "query": "?0[6]", "fuzziness": "AUTO" } } }
              ],
              "minimum_should_match": 1
            }
          },
          {
            "term": {
              "type.keyword": "?1"
            }
          },
          {
            "term": {
              "interestCategory.keyword": "?2"
            }
          }
        ]
      }
    },
    "score_mode": "sum"
  }
}
""")
    List<CommunityDocument> searchTopCommunitiesByKeywordsTypeAndCategory(List<String> keywords, String type, String interestCategory);

}
