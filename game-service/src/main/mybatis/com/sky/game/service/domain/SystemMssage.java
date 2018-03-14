package com.sky.game.service.domain;

import java.util.Date;

public class SystemMssage {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_message.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_message.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_message.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_message.start_time
     *
     * @mbggenerated
     */
    private Date startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_message.stop_time
     *
     * @mbggenerated
     */
    private Date stopTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_message.type
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_message.id
     *
     * @return the value of system_message.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_message.id
     *
     * @param id the value for system_message.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_message.title
     *
     * @return the value of system_message.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_message.title
     *
     * @param title the value for system_message.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_message.content
     *
     * @return the value of system_message.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_message.content
     *
     * @param content the value for system_message.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_message.start_time
     *
     * @return the value of system_message.start_time
     *
     * @mbggenerated
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_message.start_time
     *
     * @param startTime the value for system_message.start_time
     *
     * @mbggenerated
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_message.stop_time
     *
     * @return the value of system_message.stop_time
     *
     * @mbggenerated
     */
    public Date getStopTime() {
        return stopTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_message.stop_time
     *
     * @param stopTime the value for system_message.stop_time
     *
     * @mbggenerated
     */
    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_message.type
     *
     * @return the value of system_message.type
     *
     * @mbggenerated
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_message.type
     *
     * @param type the value for system_message.type
     *
     * @mbggenerated
     */
    public void setType(Integer type) {
        this.type = type;
    }
}