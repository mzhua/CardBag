package com.wonders.xlab.cardbag.data.entity;

import java.util.List;

/**
 * Created by hua on 16/8/31.
 */
public class CardSearchEntity extends LCBaseEntity{

    private List<ResultsEntity> results;

    public List<ResultsEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public static class ResultsEntity {
        private String card_name;
        private String card_img_url;
        private String objectId;

        public String getCard_name() {
            return card_name;
        }

        public void setCard_name(String card_name) {
            this.card_name = card_name;
        }

        public String getCard_img_url() {
            return card_img_url;
        }

        public void setCard_img_url(String card_img_url) {
            this.card_img_url = card_img_url;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }
    }
}
