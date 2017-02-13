package com.qainfotech.tap.oprime.pagefactory;

import java.util.Map;

/**
 *
 * @author Nimit Jain
 */
public class YamlElement {

    public YamlElement container;
    public String findBy;
    public String locator;
    public Boolean asynchronous;
    public Integer timeout;
    public Boolean isExpected;
    
    public YamlElement(String name, Map elements){
        /** defaults **/
        this.isExpected = false;
        this.timeout = 0;
        this.asynchronous = false;
        this.container = null;
        
        /** load element properties **/
        Map<String, Object> element = (Map<String,Object>)elements.get(name);
        this.findBy = element.get("findBy").toString();
        this.locator = element.get("locator").toString();
        if(element.containsKey("timeout")){
            this.timeout = new Integer(element.get("timeout").toString());
        }
        if(element.containsKey("asynchronous")){
            this.asynchronous = Boolean.getBoolean(element.get("asynchronous").toString());
        }
        if(element.containsKey("expected")){
            if(element.get("expected").toString().equals("true")){
                this.isExpected = true;
            }
        }
        
        /** load container **/
        if(name.equals("container")){
            this.container = null;
        }else{
            if(elements.containsKey("container")){
                this.container = new YamlElement("container", elements);
            }else{
                this.container = null;
            }
        }
    }
}
