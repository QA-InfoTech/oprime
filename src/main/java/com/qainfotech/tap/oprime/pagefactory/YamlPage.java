package com.qainfotech.tap.oprime.pagefactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Nimit Jain
 */
public class YamlPage {
    
    public String expectedTitle;
    public String path;
    public Boolean asynchronous;
    public Integer timeout;
    public Map<String, Object> page;
    public Map<String, YamlElement> yamlElements;
    public String context;
    
    public YamlPage(String root, String pageFile) throws Exception{
        
        String yamlFilePath = root+"/"+pageFile+".yaml";
        String testSuiteYamlContent;
        if(YamlPage.class.getResourceAsStream("/" + root+"/"+pageFile+".yaml")!=null){
            testSuiteYamlContent = IOUtils.toString(
                    YamlPage.class.getResourceAsStream("/" + root+"/"+pageFile+".yaml"));
        }else{
            testSuiteYamlContent = IOUtils.toString(
                    YamlPage.class.getResourceAsStream("/" + root+"/common"+pageFile+".yaml"));
        }

        /** defaults **/
        this.timeout = 0;
        this.asynchronous = false;
        this.context = null;
        
        this.yamlElements = new HashMap();
        Yaml yaml = new Yaml();
        this.page = (Map<String, Object>)yaml.load(testSuiteYamlContent);

        if(page.containsKey("expectedTitle")){
            this.expectedTitle = page.get("expectedTitle").toString();
        }
        if(page.containsKey("path")){
            this.path = page.get("path").toString();
        }
        if(page.containsKey("asynchronous")){
            this.asynchronous = Boolean.valueOf(page.get("asynchronous").toString());
        }
        if(page.containsKey("timeout")){
            this.timeout = new Integer(page.get("timeout").toString());
        }
        if(page.containsKey("context")){
            this.context = page.get("context").toString();
        }
        
        /** init yaml elements **/
        Map<String, Map> elements = (Map<String, Map>)page.get("elements");
        for(int index=0; index<elements.size(); index++){
            String key = (String)elements.keySet().toArray()[index];
            yamlElements.put(key, new YamlElement(key, elements));
        }
    }
    
    public YamlElement element(String name){
        try{
            return yamlElements.get(name);
        }catch(NullPointerException e){
            throw new NullPointerException("Element '"+name+"' does not exist in Page Spec file");
        }
    }
    
    public YamlElement container(){
        return element("container");
    }
    
    public List<YamlElement> expectedElements(){
        List<YamlElement> expected = new ArrayList();
        for(String elementName:yamlElements.keySet()){
            if(yamlElements.get(elementName).isExpected){
                expected.add(yamlElements.get(elementName));
            }
        }
        return expected;
    }
}
