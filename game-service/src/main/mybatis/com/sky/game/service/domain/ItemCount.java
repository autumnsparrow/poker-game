package com.sky.game.service.domain;

public class ItemCount {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item_count.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item_count.all_num
     *
     * @mbggenerated
     */
    private Integer allNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item_count.left_num
     *
     * @mbggenerated
     */
    private Integer leftNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item_count.today_num
     *
     * @mbggenerated
     */
    private Integer todayNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item_count.item_id
     *
     * @mbggenerated
     */
    private Long itemId;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column item_count.item_id
     *
     * @mbggenerated
     */
    private int type;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item_count.id
     *
     * @return the value of item_count.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item_count.id
     *
     * @param id the value for item_count.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item_count.all_num
     *
     * @return the value of item_count.all_num
     *
     * @mbggenerated
     */
    public Integer getAllNum() {
        return allNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item_count.all_num
     *
     * @param allNum the value for item_count.all_num
     *
     * @mbggenerated
     */
    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item_count.left_num
     *
     * @return the value of item_count.left_num
     *
     * @mbggenerated
     */
    public Integer getLeftNum() {
        return leftNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item_count.left_num
     *
     * @param leftNum the value for item_count.left_num
     *
     * @mbggenerated
     */
    public void setLeftNum(Integer leftNum) {
        this.leftNum = leftNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item_count.today_num
     *
     * @return the value of item_count.today_num
     *
     * @mbggenerated
     */
    public Integer getTodayNum() {
        return todayNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item_count.today_num
     *
     * @param todayNum the value for item_count.today_num
     *
     * @mbggenerated
     */
    public void setTodayNum(Integer todayNum) {
        this.todayNum = todayNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column item_count.item_id
     *
     * @return the value of item_count.item_id
     *
     * @mbggenerated
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column item_count.item_id
     *
     * @param itemId the value for item_count.item_id
     *
     * @mbggenerated
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
    
}