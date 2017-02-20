package com.qainfotech.tap.oprime.pagefactory;

import com.jcabi.aspects.Loggable;
import com.qainfotech.custom.exceptions.MyOwnNoSuchContextException;
import com.qainfotech.tap.oprime.TestSession;

/**
 * 
 * @author nimit.jain (QAInfotech)
 *
 */

@Loggable
public class NativeViewPage extends Page{
	
	/**
	 * Method is used create session.
	 * @param session
	 * @param pageYamlFile
	 * @throws Exception
	 */
	public NativeViewPage(TestSession session, String pageYamlFile) throws Exception {
		super(session, pageYamlFile);
	}
	
	/**
	 * Method is used to switch to Native context
	 * @throws MyOwnNoSuchContextException 
	 */
	public Boolean isDisplayed() throws MyOwnNoSuchContextException{
        switchToNativeContext();
        return super.isDisplayed();
    }
	
}
