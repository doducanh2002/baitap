package com.example.storeservice.restfullapi.dto;

import java.util.Date;

public class ProductDto {

        private int id;

        private String name;

        private int price;

        private Date expired;

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public int getPrice() {
                return price;
        }

        public void setPrice(int price) {
                this.price = price;
        }

        public Date getExpired() {
                return expired;
        }

        public void setExpired(Date expired) {
                this.expired = expired;
        }

}
