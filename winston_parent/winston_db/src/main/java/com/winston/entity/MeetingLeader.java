package com.winston.entity;

import java.io.Serializable;

public class MeetingLeader implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column meeting_leader.m_id
     *
     * @mbg.generated
     */
    private Integer mId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column meeting_leader.l_id
     *
     * @mbg.generated
     */
    private Integer lId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column meeting_leader.type
     *
     * @mbg.generated
     */
    private Integer type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column meeting_leader.m_name
     *
     * @mbg.generated
     */
    private String mName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column meeting_leader.e_id
     *
     * @mbg.generated
     */
    private Integer eId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table meeting_leader
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column meeting_leader.m_id
     *
     * @return the value of meeting_leader.m_id
     *
     * @mbg.generated
     */
    public Integer getmId() {
        return mId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column meeting_leader.m_id
     *
     * @param mId the value for meeting_leader.m_id
     *
     * @mbg.generated
     */
    public void setmId(Integer mId) {
        this.mId = mId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column meeting_leader.l_id
     *
     * @return the value of meeting_leader.l_id
     *
     * @mbg.generated
     */
    public Integer getlId() {
        return lId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column meeting_leader.l_id
     *
     * @param lId the value for meeting_leader.l_id
     *
     * @mbg.generated
     */
    public void setlId(Integer lId) {
        this.lId = lId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column meeting_leader.type
     *
     * @return the value of meeting_leader.type
     *
     * @mbg.generated
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column meeting_leader.type
     *
     * @param type the value for meeting_leader.type
     *
     * @mbg.generated
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column meeting_leader.m_name
     *
     * @return the value of meeting_leader.m_name
     *
     * @mbg.generated
     */
    public String getmName() {
        return mName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column meeting_leader.m_name
     *
     * @param mName the value for meeting_leader.m_name
     *
     * @mbg.generated
     */
    public void setmName(String mName) {
        this.mName = mName == null ? null : mName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column meeting_leader.e_id
     *
     * @return the value of meeting_leader.e_id
     *
     * @mbg.generated
     */
    public Integer geteId() {
        return eId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column meeting_leader.e_id
     *
     * @param eId the value for meeting_leader.e_id
     *
     * @mbg.generated
     */
    public void seteId(Integer eId) {
        this.eId = eId;
    }
}