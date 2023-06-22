package com.lkw.searchbar.unlogin.model.research;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lkw.searchbar.unlogin.model.category_search.Document;
import com.lkw.searchbar.unlogin.model.category_search.Meta;

import java.util.List;

public class SearchResult {
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("documents")
    @Expose
    private List<Document> documents = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
