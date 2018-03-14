/**
 * 
 */
package com.sky.game.service.domain;

/**
 * @author Administrator
 *
 */
public class PropertyShow {

	/**
	 * 
	 */
	public PropertyShow() {
		// TODO Auto-generated constructor stub
	}
    String name;
    int value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public PropertyShow(String name, int value) {
		super();
		this.name = name;
		this.value = value;
	}
	@Override
	public String toString() {
		return "PropertyShow [name=" + name + ", value=" + value + "]";
	}
    
}
