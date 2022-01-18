package com.example.pharmacyapplication;

public class DrugAll {
    private Integer drugid;
    private String drugname;
    private Float drugprice;
    private String brandname;
    private String categoryname;

    public Integer getDrugid() {
        return drugid;
    }

    public void setDrugid(Integer drugid) {
        this.drugid = drugid;
    }

    public String getDrugname() {
        return drugname;
    }

    public void setDrugname(String drugname) {
        this.drugname = drugname;
    }

    public Float getDrugprice() {
        return drugprice;
    }

    public void setDrugprice(Float drugprice) {
        this.drugprice = drugprice;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
