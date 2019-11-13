package care.data4life.integration.app.testUtils

object SmsSender {
    // Find your Account Sid and Auth Token at twilio.com/console
    val ACCOUNT_SID = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
    val AUTH_TOKEN = "your_auth_token"

    @JvmStatic
    fun main(args: Array<String>) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN)

        val message = Message
                .creator(PhoneNumber("+14159352345"), // to
                        PhoneNumber("+14158141829"), // from
                        "Where's Wallace?")
                .create()

        System.out.println(message.getSid())
    }
}