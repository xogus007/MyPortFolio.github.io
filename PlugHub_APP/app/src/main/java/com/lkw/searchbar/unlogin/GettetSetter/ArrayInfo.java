package com.lkw.searchbar.unlogin.GettetSetter;

import java.util.ArrayList;

public class ArrayInfo {
    private static ArrayInfo instance;

    private ArrayList<String> addr = null;
    private ArrayList<String> csNm = null;
    private ArrayList<String> cpTp = null;
    private ArrayList<String> chargeTp = null;
    private ArrayList<String> spStat = null;
    private ArrayList<String> statUpdateDatetime = null;
    private ArrayList<String> Lat = null;
    private ArrayList<String> Longi = null;
    private ArrayList<String> cpId = null;
    private ArrayList<String> csId = null;
    private ArrayList<String> cpNm = null;

    private ArrayInfo() {
        // private 생성자
    }

    public static ArrayInfo getInstance() {
        if (instance == null) {
            instance = new ArrayInfo();
        }
        return instance;
    }

    public ArrayList<String> getCpId() {
        return cpId;
    }

    public void setCpId(ArrayList<String> cpId) {
        this.cpId = cpId;
    }

    public ArrayList<String> getCsId() {
        return csId;
    }

    public void setCsId(ArrayList<String> csId) {
        this.csId = csId;
    }

    public ArrayList<String> getCpNm() {
        return cpNm;
    }

    public void setCpNm(ArrayList<String> cpNm) {
        this.cpNm = cpNm;
    }

    public ArrayList<String> getAddr() {
        return addr;
    }

    public void setAddr(ArrayList<String> addr) {
        this.addr = addr;
    }

    public ArrayList<String> getCsNm() {
        return csNm;
    }

    public void setCsNm(ArrayList<String> csNm) {
        this.csNm = csNm;
    }

    public ArrayList<String> getCpTp() {
        return cpTp;
    }

    public void setCpTp(ArrayList<String> cpTp) {
        this.cpTp = cpTp;
    }

    public ArrayList<String> getChargeTp() {
        return chargeTp;
    }

    public void setChargeTp(ArrayList<String> chargeTp) {
        this.chargeTp = chargeTp;
    }

    public ArrayList<String> getSpStat() {
        return spStat;
    }

    public void setSpStat(ArrayList<String> spStat) {
        this.spStat = spStat;
    }

    public ArrayList<String> getStatUpdateDatetime() {
        return statUpdateDatetime;
    }

    public void setStatUpdateDatetime(ArrayList<String> statUpdateDatetime) {
        this.statUpdateDatetime = statUpdateDatetime;
    }

    public ArrayList<String> getLat() {
        return Lat;
    }

    public void setLat(ArrayList<String> lat) {
        Lat = lat;
    }

    public ArrayList<String> getLongi() {
        return Longi;
    }

    public void setLongi(ArrayList<String> longi) {
        Longi = longi;
    }
}
