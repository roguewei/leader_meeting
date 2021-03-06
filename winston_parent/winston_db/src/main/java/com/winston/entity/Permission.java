package com.winston.entity;

import java.io.Serializable;

public class Permission implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.pername
     *
     * @mbg.generated
     */
    private String pername;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.perurl
     *
     * @mbg.generated
     */
    private String perurl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.type
     *
     * @mbg.generated
     */
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.parentid
     *
     * @mbg.generated
     */
    private Integer parentid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table permission
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.id
     *
     * @return the value of permission.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.id
     *
     * @param id the value for permission.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.pername
     *
     * @return the value of permission.pername
     *
     * @mbg.generated
     */
    public String getPername() {
        return pername;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.pername
     *
     * @param pername the value for permission.pername
     *
     * @mbg.generated
     */
    public void setPername(String pername) {
        this.pername = pername == null ? null : pername.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.perurl
     *
     * @return the value of permission.perurl
     *
     * @mbg.generated
     */
    public String getPerurl() {
        return perurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.perurl
     *
     * @param perurl the value for permission.perurl
     *
     * @mbg.generated
     */
    public void setPerurl(String perurl) {
        this.perurl = perurl == null ? null : perurl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.type
     *
     * @return the value of permission.type
     *
     * @mbg.generated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.type
     *
     * @param type the value for permission.type
     *
     * @mbg.generated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.parentid
     *
     * @return the value of permission.parentid
     *
     * @mbg.generated
     */
    public Integer getParentid() {
        return parentid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.parentid
     *
     * @param parentid the value for permission.parentid
     *
     * @mbg.generated
     */
    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }
}