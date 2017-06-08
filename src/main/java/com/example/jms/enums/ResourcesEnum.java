package com.example.jms.enums;

/**
 * Enumeration of the configured resources for the application.
 * @author Gabriel
 *
 */
public enum ResourcesEnum {

	QUEUE001("Q","QUEUE_001","QUEUE 001 in ActiveMQ"),
	QUEUE002("Q","QUEUE_002","QUEUE 002 in ActiveMQ"),
	TOPIC001("T","TOPIC_001","TOPIC 001 in ActiveMQ"),
	TOPIC002("T","TOPIC_002","TOPIC 002 in ActiveMQ"),
	TOPIC003("T","TOPIC_003","TOPIC 003 in ActiveMQ");

	public static String QUEUE_TYPE="Q";
	public static String TOPIC_TYPE="T";
	
	private String type;
	private String code;
    private String name;

    /**
     * Creates a new resource.
     * @param type
     * @param code
     * @param name
     */
    private ResourcesEnum(String type, String code, String name) {
    	this.type = type;
    	this.code = code;
        this.name = name;
    }
    
    /**
     * Gets the type.
     * @return
     */
    public String getType() {
    	return type;
    }

    /**
     * Gets the code.
     * @return
     */
    public String getCode() {
    	return code;
    }
    
    /**
     * Gets the name.
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets a resource for the given code.
     * @param code
     * @return
     */
    public static ResourcesEnum getResourceByCode(final String code) {
    	for(ResourcesEnum resource:ResourcesEnum.values()) {
    		if (resource.getCode().equals(code)) {
    			return resource;
    		}
    	}
    	
    	return null;
    }
}
