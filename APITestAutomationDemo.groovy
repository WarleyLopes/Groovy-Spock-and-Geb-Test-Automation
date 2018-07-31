@Grapes([
    @Grab("org.spockframework:spock-core:1.1-groovy-2.4"),
    @Grab("org.codehaus.groovy:http-builder:0.4.0")
])

import groovyx.net.http.RESTClient
import spock.lang.Specification

class ApiTest extends Specification {
	
	def "Check if a simple API calling works"(){
		given: "an API endpoint to be called"
			RESTClient restClient = new RESTClient("https://jsonplaceholder.typicode.com/todos/1", JSON)
		
		when: "asking for a response"
		def response = restClient.get(
			requestContentType: JSON
		)
		
		then: "Status should be 200"
		response.status == 200

		and: "Body contains proper values"
		assert response.data[0].userId == 1
		assert response.data[0].id == 1
		assert response.data[0].title == 'delectus aut autem'
		assert response.data[0].completed == false
		assert response.data.size == 4
	}
}