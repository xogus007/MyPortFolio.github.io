package com.lkw.searchbar.unlogin.model.mapdto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdressDto {
    @SerializedName("documents")
    @Expose
    public List<Document> documentsList = null;

    @SerializedName("meta")
    @Expose
    public Meta meta;

    public static class Document {
        @SerializedName("address")
        private Address address;
        @SerializedName("address_name")
        private String address_name;
        @SerializedName("x")
        private String x;
        @SerializedName("y")
        private String y;

        public static class Address {
            @SerializedName("h_code")
            private String h_code;

            public String getH_code() {
                return h_code;
            }

            public void setH_code(String h_code) {
                this.h_code = h_code;
            }
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public String getAddress_name() {
            return address_name;
        }

        public void setAddress_name(String address_name) {
            this.address_name = address_name;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }
    }


    public static class Meta {
        @SerializedName("is_end")
        private boolean is_end;
        @SerializedName("pageable_count")
        private int pageable_count;
        @SerializedName("total_count")
        private int total_count;

        public boolean isIs_end() {
            return is_end;
        }

        public void setIs_end(boolean is_end) {
            this.is_end = is_end;
        }

        public int getPageable_count() {
            return pageable_count;
        }

        public void setPageable_count(int pageable_count) {
            this.pageable_count = pageable_count;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }
    }
}

