package com.example.storeservice.restfullapi.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

    @Entity
    @Table(name = "Reviews")
    public class Reviews implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id reviewer")
        private int id;

        @Column(name = "name reviewer")
        private String name;

        @Column(name = "product")
        private String product;

        @Column(name = "review product")
        private String review;

        @Column(name = "date review")
        private Date date;

        public Reviews() {
        }

        public Reviews(int id, String name, String product, String review, Date date) {
            this.id = id;
            this.name = name;
            this.product = product;
            this.review = review;
            this.date = date;
        }

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

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Reviews)) return false;
            Reviews reviews = (Reviews) o;
            return getId() == reviews.getId();
        }

        @Override
        public int hashCode() {
            return 31;
        }
    }

