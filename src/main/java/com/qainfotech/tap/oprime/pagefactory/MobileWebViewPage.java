package com.qainfotech.tap.oprime.pagefactory;

import com.qainfotech.custom.exceptions.MyOwnNoSuchContextException;

/**
 * 
 * @author nimit.jain
 * 
 */

public class MobileWebViewPage extends Page{
	
	/**
	 * 
	 * @param session
	 * @param pageYamlFile
	 * @throws Exception
	 */
	public MobileWebViewPage(TestSession session, String pageYamlFile) throws Exception{
        super(session, pageYamlFile);
    }
    
	/**
	 * @throws MyOwnNoSuchContextException
	 */
    @Override
    public Boolean isDisplayed() throws MyOwnNoSuchContextException{
        switchToPageContext();
        return super.isDisplayed();
    }
    
}
