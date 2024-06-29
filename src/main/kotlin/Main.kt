import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File

fun main() {
    val token: String = File("discord-token.txt").readText()

    val jda = JDABuilder.createDefault(token)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .setActivity(Activity.listening("all of the sounds"))
        .addEventListeners(MessageListener())
        .build()

    jda.updateCommands().addCommands(
        Commands.slash("ping", "Says Pong!"),
        Commands.slash("echo", "Repeats messages back to you.")
            .addOption(OptionType.STRING, "message", "The message to repeat."),
        Commands.slash("add", "Adds 2 numbers together")
            .addOption(OptionType.NUMBER, "number-1", "The first number")
            .addOption(OptionType.NUMBER, "number-2", "The second number"),
        Commands.slash("repeat", "Repeats the name of the pressed button")
    ).queue()
    println("Bot is ready!")
}

class MessageListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        println("Received a message from ${event.message.author}")


        val message = event.message.contentRaw

        if (event.author.isBot) {
            println("Received bot message (ignored)")
        }else if (message == "!ping") {
                println("Received ping command")
                event.channel.sendMessage("Pong!").queue()
        }else if (message == "Hi") {
            println("Received greeting")
            event.channel.sendMessage("Haiiiiii !!!1!1!!1!!!111!111111111! :3:3:333333:3 <3<3<3 :3").queue()
        }
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when(event.name) {
            "echo" -> event.reply(event.getOption("message")!!.asString).queue()
            "ping" -> event.reply("Pong!").queue()
            "repeat" -> event.reply("Click a button!")
                .addActionRow(
                    Button.primary("One", "One"),
                    Button.primary("Two", "Two")
                ).queue()
        }
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        when(event.componentId){
            "One" -> event.reply("One").queue()
            "Two" -> event.reply("Two").queue()
        }
    }
}