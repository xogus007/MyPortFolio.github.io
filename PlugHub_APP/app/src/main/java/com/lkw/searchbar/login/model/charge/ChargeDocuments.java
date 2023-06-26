package com.lkw.searchbar.login.model.charge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChargeDocuments {
    @SerializedName("addr")
    @Expose
    private String addr;

    @SerializedName("chargeTp")
    @Expose
    private Integer chargeTp;

    @SerializedName("cpId")
    @Expose
    private Integer cpId;

    @SerializedName("cpNm")
    @Expose
    private String cpNm;

    @SerializedName("cpStat")
    @Expose
    private Integer cpStat;

    @SerializedName("cpTp")
    @Expose
    private Integer cpTp;

    @SerializedName("csId")
    @Expose
    private Integer csId;

    @SerializedName("csNm")
    @Expose
    private String csNm;

    @SerializedName("lat")
    @Expose
    private Double lat;

    @SerializedName("longi")
    @Expose
    private Double longi;

    @SerializedName("statUpdateDatetime")
    @Expose
    private String statUpdateDatetime;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getChargeTp() {
        return chargeTp;
    }

    public void setChargeTp(Integer chargeTp) {
        this.chargeTp = chargeTp;
    }

    public Integer getCpId() {
        return cpId;
    }

    public void setCpId(Integer cpId) {
        this.cpId = cpId;
    }

    public String getCpNm() {
        return cpNm;
    }

    public void setCpNm(String cpNm) {
        this.cpNm = cpNm;
    }

    public Integer getCpStat() {
        return cpStat;
    }

    public void setCpStat(Integer cpStat) {
        this.cpStat = cpStat;
    }

    public Integer getCpTp() {
        return cpTp;
    }

    public void setCpTp(Integer cpTp) {
        this.cpTp = cpTp;
    }

    public Integer getCsId() {
        return csId;
    }

    public void setCsId(Integer csId) {
        this.csId = csId;
    }

    public String getCsNm() {
        return csNm;
    }

    public void setCsNm(String csNm) {
        this.csNm = csNm;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public String getStatUpdateDatetime() {
        return statUpdateDatetime;
    }

    public void setStatUpdateDatetime(String statUpdateDatetime) {
        this.statUpdateDatetime = statUpdateDatetime;
    }
}
