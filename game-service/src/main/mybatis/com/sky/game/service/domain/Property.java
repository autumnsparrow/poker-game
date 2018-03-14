package com.sky.game.service.domain;

import java.io.Serializable;

public class Property implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4088887944819279899L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column property.id
     *
     * @mbggenerated
     */

    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column property.name
     *
     * @mbggenerated
     */
    private PropertyTypes name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column property.description
     *
     * @mbggenerated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column property.icon_id
     *
     * @mbggenerated
     */
    private Long iconId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property.id
     *
     * @return the value of property.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property.id
     *
     * @param id the value for property.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property.name
     *
     * @return the value of property.name
     *
     * @mbggenerated
     */
    public PropertyTypes getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property.name
     *
     * @param name the value for property.name
     *
     * @mbggenerated
     */
    public void setName(PropertyTypes name) {
        this.name = name == null ? PropertyTypes.Undefined : name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property.description
     *
     * @return the value of property.description
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property.description
     *
     * @param description the value for property.description
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column property.icon_id
     *
     * @return the value of property.icon_id
     *
     * @mbggenerated
     */
    public Long getIconId() {
        return iconId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column property.icon_id
     *
     * @param iconId the value for property.icon_id
     *
     * @mbggenerated
     */
    public void setIconId(Long iconId) {
        this.iconId = iconId;
    }
}