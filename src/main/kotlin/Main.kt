import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

fun main() {
    val token = "YOUR_DISCORD_BOT_TOKEN"

    val jda = JDABuilder.createDefault(token)
        .setActivity(Activity.playing("Kotlin Bot"))
        .addEventListeners(MessageListener())
        .build()
}

class MessageListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return

        val message = event.message.contentRaw
        if (message == "!ping") {
            event.channel.sendMessage("Pong!").queue()
        }
    }
}
