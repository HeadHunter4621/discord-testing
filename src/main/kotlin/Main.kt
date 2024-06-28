import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.io.File

fun main() {
    val token: String = File("token.txt").readText()

    val jda = JDABuilder.createDefault(token)
        .setActivity(Activity.playing("Kotlin Bot"))
        .addEventListeners(MessageListener())
        .build()

    jda.awaitReady()
    println("Bot is ready!")
}

class MessageListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        println("Recieved a message: ${event.message.contentRaw}")
        if (event.author.isBot) return

        val message = event.message.contentRaw
        if (message == "?ping") {
            println("Recieved ping command")
            event.channel.sendMessage("Pong!").queue()
        }
    }
}

