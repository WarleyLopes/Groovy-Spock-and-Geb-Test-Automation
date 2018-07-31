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

//After running the script for the first time with GroovyConsole remove the two below lines
System.setProperty('webdriver.chrome.driver','/webdrivers/win32/chromedriver.exe')
System.setProperty('geb.build.reportsDir','/reports')

class simpleNavigationSpec extends GebReportingSpec {
    def setup() {}		// runs before every feature method
    def cleanup() {}	// runs after every feature method
    def setupSpec() {}	// runs before the first feature method
    
    def cleanupSpec() {	// runs after the last feature method
         resetBrowser()
         CachingDriverFactory.clearCacheAndQuitDriver()
    }
    
    def "should check if BairesDev Glassdoor rating is above 4.0"(){
        when: "I look for BairesDev Glassdoor page"
            go "bairesdev reviews glassdoor"
			
		and: "I click on the link for the desired page"
			waitFor{$("a:contains('BairesDev Reviews | Glassdoor')",0).displayed}
			$("a:contains('BairesDev Reviews | Glassdoor')",0).click()
        
        then: "The rating should be above 4.0"
			waitFor{$("div.ratingNum",0).displayed}
			$("div.ratingNum",0)>4
    }
}