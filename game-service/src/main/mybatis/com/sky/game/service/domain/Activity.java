package com.sky.game.service.domain;

public class Activity {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.description
     *
     * @mbggenerated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.start_time
     *
     * @mbggenerated
     */
    private String startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.stop_time
     *
     * @mbggenerated
     */
    private String stopTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.icon_id
     *
     * @mbggenerated
     */
    private Long iconId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.type
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity.item_id
     *
     * @mbggenerated
     */
    private Long itemId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.id
     *
     * @return the value of activity.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.id
     *
     * @param id the value for activity.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.name
     *
     * @return the value of activity.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.name
     *
     * @param name the value for activity.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.description
     *
     * @return the value of activity.description
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.description
     *
     * @param description the value for activity.description
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.start_time
     *
     * @return the value of activity.start_time
     *
     * @mbggenerated
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.start_time
     *
     * @param startTime the value for activity.start_time
     *
     * @mbggenerated
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.stop_time
     *
     * @return the value of activity.stop_time
     *
     * @mbggenerated
     */
    public String getStopTime() {
        return stopTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.stop_time
     *
     * @param stopTime the value for activity.stop_time
     *
     * @mbggenerated
     */
    public void setStopTime(String stopTime) {
        this.stopTime = stopTime == null ? null : stopTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.icon_id
     *
     * @return the value of activity.icon_id
     *
     * @mbggenerated
     */
    public Long getIconId() {
        return iconId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.icon_id
     *
     * @param iconId the value for activity.icon_id
     *
     * @mbggenerated
     */
    public void setIconId(Long iconId) {
        this.iconId = iconId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.type
     *
     * @return the value of activity.type
     *
     * @mbggenerated
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.type
     *
     * @param type the value for activity.type
     *
     * @mbggenerated
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity.item_id
     *
     * @return the value of activity.item_id
     *
     * @mbggenerated
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity.item_id
     *
     * @param itemId the value for activity.item_id
     *
     * @mbggenerated
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}