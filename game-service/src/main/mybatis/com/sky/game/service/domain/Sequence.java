package com.sky.game.service.domain;

public class Sequence {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SEQUENCE.SEQ_NAME
     *
     * @mbggenerated
     */
    private String seqName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SEQUENCE.SEQ_COUNT
     *
     * @mbggenerated
     */
    private Long seqCount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SEQUENCE.SEQ_NAME
     *
     * @return the value of SEQUENCE.SEQ_NAME
     *
     * @mbggenerated
     */
    public String getSeqName() {
        return seqName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SEQUENCE.SEQ_NAME
     *
     * @param seqName the value for SEQUENCE.SEQ_NAME
     *
     * @mbggenerated
     */
    public void setSeqName(String seqName) {
        this.seqName = seqName == null ? null : seqName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SEQUENCE.SEQ_COUNT
     *
     * @return the value of SEQUENCE.SEQ_COUNT
     *
     * @mbggenerated
     */
    public Long getSeqCount() {
        return seqCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SEQUENCE.SEQ_COUNT
     *
     * @param seqCount the value for SEQUENCE.SEQ_COUNT
     *
     * @mbggenerated
     */
    public void setSeqCount(Long seqCount) {
        this.seqCount = seqCount;
    }
}