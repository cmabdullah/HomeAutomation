package com.abdullah.home.automation.dto.request;

import java.util.Objects;

public class SearchKeyword {

    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "SearchKeyword{" +
                "keyword='" + keyword + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchKeyword)) return false;
        SearchKeyword that = (SearchKeyword) o;
        return Objects.equals(getKeyword(), that.getKeyword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKeyword());
    }
}
