package com.spring.Journal.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Quotes {

        @JsonProperty("q")
        private String quote;

        @JsonProperty("a")
        private String author;

        public String getQuote() { return quote; }
        public void setQuote(String value) { this.quote = value; }

        public String getAuthor() { return author; }
        public void setAuthor(String value) { this.author = value; }

}
