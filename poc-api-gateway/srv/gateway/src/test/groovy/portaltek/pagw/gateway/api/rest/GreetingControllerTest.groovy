package portaltek.pagw.gateway.api.rest


import spock.lang.Specification
import spock.lang.Subject

class GreetingControllerTest extends Specification {

   @Subject
   GreetingController controller = new GreetingController()
   //GreetingController controller = Spy(constructorArgs: [])

   def admin_msg = "Hello Authenticated Admin"
   def user_msg = "Hello Authenticated User"

   def "GreetingAdmin should return admin msg"() {
      given:
      when:
      def reply = controller.greetingAdmin()

      then:
      //false == true
      reply.body.message == admin_msg
   }


   def "greetingUser should return user msg"() {
      given:
      when:
      def reply = controller.greetingUser()

      then:
      reply.body.message == user_msg
   }
}