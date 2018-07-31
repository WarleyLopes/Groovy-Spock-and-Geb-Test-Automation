@Grapes([
    @Grab("org.codehaus.geb:geb-core:0.7.2"),
    @Grab("org.seleniumhq.selenium:selenium-support:3.13.0"),
    @Grab("org.seleniumhq.selenium:selenium-chrome-driver:3.13.0"),
    @Grab("org.spockframework:spock-core:1.1-groovy-2.4"),
    @Grab("org.gebish:geb-spock:2.1")
])

import geb.Browser
import geb.driver.CachingDriverFactory
import geb.spock.GebSpec
import geb.spock.GebReportingSpec
import geb.Page

//After running the script for the first time with GroovyConsole remove the two below lines
System.setProperty('webdriver.chrome.driver','/webdrivers/win32/chromedriver.exe')
System.setProperty('geb.build.reportsDir','/reports')

class BairesDevHomePage extends page{
	static url = "https://www.bairesdev.com/"
	static at = { title == "BairesDev | Nearshore Software Development to Latin America" }
    static content = {
        contactNameField { $("#nf-field-5") }
        contactEmailField { $("#nf-field-6") }
        contactMsgField { $("#nf-field-7") }
		contactSendButton { $("#nf-field-8") }
		contactNameFieldRequiredError { $("#nf-error-5").find("div") }
		contactEmailFieldRequiredError { $("#nf-error-6").find("div") }
		contactMsgFieldRequiredError { $("#nf-error-7").find("div") }
    }
	
	void sendContactMessage(name,email,message) {
		contactNameField = name
		contactEmailField = email
		contactMsgField = message
		contactSendButton.click()
	}
}

class BairesDevHomePageSpec extends GebReportingSpec {
    def setup() {}		// runs before every feature method
    def cleanup() {}	// runs after every feature method
    def setupSpec() {}	// runs before the first feature method
    
    def cleanupSpec() {	// runs after the last feature method
         resetBrowser()
         CachingDriverFactory.clearCacheAndQuitDriver()
    }
    
    def "Check if BairesDev Contact form has basic obligatory rules for its fields"(){
        given:
		to BairesDevHomePage
		
		when: "trying to submit the contact form with empty fields"
			at BairesDevHomePage
            sendContactMessage("","","")
        
        then: "the errors for required fields should appear"
			waitFor{contactNameFieldRequiredError.displayed}
			assert contactNameFieldRequiredError.displayed
			assert contactEmailFieldRequiredError.displayed
			assert contactMsgFieldRequiredError.displayed
    }
}