import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File

fun main() {
    val token: String = File("discord-token.txt").readText()

    val jda = JDABuilder.createDefault(token)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .setActivity(Activity.playing("Ping Pong"))
        .addEventListeners(MessageListener())
        .build()

    jda.updateCommands().addCommands(
        Commands.slash("ping", "Says Pong!"),
        Commands.slash("echo", "Repeats messages back to you.")
            .addOption(OptionType.STRING, "message", "The message to repeat."),
        Commands.slash("add", "Adds 2 numbers together")
            .addOption(OptionType.NUMBER, "number-1", "The first number")
            .addOption(OptionType.NUMBER, "number-2", "The second number")
    ).queue()
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

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if(event.name == "echo"){
            println("Echo command received")
            event.reply(event.getOption("message")!!.asString).queue()
            println("Echo command finished")
        }else if(event.name == "ping"){
            println("Ping command received")
            event.reply("Pong!").queue()
            println("Ping command finished")
        }
    }
}