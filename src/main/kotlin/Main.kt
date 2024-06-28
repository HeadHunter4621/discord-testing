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
        .enableIntents(GatewayIntent.MESSAGE_CONTENT) // Enable MESSAGE_CONTENT intent
        .setActivity(Activity.playing("Ping Pong"))
        .addEventListeners(MessageListener())
        .build()

    jda.guilds.forEach { guild ->
        guild.updateCommands().addCommands(
            Commands.slash("echo", "Repeats messages back to you.")
                .addOption(OptionType.STRING, "message", "The message to repeat.")
                .addOption(OptionType.INTEGER, "times", "The number of times to repeat the message.")
                .addOption(OptionType.BOOLEAN, "ephemeral", "Whether or not the message should be sent as an ephemeral message.") ,
            Commands.slash("ping", "Says Pong!")
        ).queue()
    }

    jda.awaitReady()
    println("Bot is ready!")

}

class MessageListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        val guild = event.guild
        println("Recieved a message: ${event.message.contentRaw}")
        if (event.author.isBot) return

        val message = event.message.contentRaw
        if (message == "?ping") {
            println("Recieved ping command")
            event.channel.sendMessage("Pong!").queue()

        /*guild.updateCommands().addCommands(
            Commands.slash("echo", "Repeats messages back to you.")
                .addOption(OptionType.STRING, "message", "The message to repeat.")
                .addOption(OptionType.INTEGER, "times", "The number of times to repeat the message.")
                .addOption(OptionType.BOOLEAN, "ephemeral", "Whether or not the message should be sent as an ephemeral message.")

        ).queue()

        guild.updateCommands().addCommands(
            Commands.slash("ping", "Says Pong!")
        ).queue()*/
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

