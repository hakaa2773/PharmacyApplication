package com.example.pharmacyapplication;

public class Customer {
    private String custid;
    private String custfirstname;
    private String custlastname;
    private String custphone;
    private String custemail;
    private String custaddress;

    public Customer() {
    }

    public Customer(String custid, String custfirstname, String custlastname, String custphone, String custemail) {
        this.custid = custid;
        this.custfirstname = custfirstname;
        this.custlastname = custlastname;
        this.custphone = custphone;
        this.custemail = custemail;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getCustfirstname() {
        return custfirstname;
    }

    public void setCustfirstname(String custfirstname) {
        this.custfirstname = custfirstname;
    }

    public String getCustlastname() {
        return custlastname;
    }

    public void setCustlastname(String custlastname) {
        this.custlastname = custlastname;
    }

    public String getCustphone() {
        return custphone;
    }

    public void setCustphone(String custphone) {
        this.custphone = custphone;
    }

    public String getCustemail() {
        return custemail;
    }

    public void setCustemail(String custemail) {
        this.custemail = custemail;
    }

    public String getCustaddress() {
        return custaddress;
    }

    public void setCustaddress(String custaddress) {
        this.custaddress = custaddress;
    }
}
