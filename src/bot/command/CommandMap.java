package bot.command;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bot.command.compteur.BasicCommands;
import bot.command.compteur.CompteurCommands;
import bot.compteur.CompteurBotDiscord;
import bot.compteur.CompteurLogger;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandMap {

	private static CommandMap INSTANCE = null; 
	private final Map<String, CommandBean> commands = new HashMap<>();
	private final String tag = "!";
	private CompteurLogger LOG = CompteurLogger.getLogger(this.getClass().getName());
	private CompteurBotDiscord compteurBotDiscord;
	
	private CommandMap(CompteurBotDiscord compteurBotDiscord) {
		this.compteurBotDiscord = compteurBotDiscord;
		registerCommand(new BasicCommands(this.compteurBotDiscord));
		registerCommand(new CompteurCommands(compteurBotDiscord));
	}
	
	public static CommandMap getCommandMap(CompteurBotDiscord compteurBotDiscord) {
		if(INSTANCE == null)  INSTANCE = new CommandMap(compteurBotDiscord);
		return INSTANCE;
	}
   
    public String getTag() {
        return tag;
    }
   
    public Collection<CommandBean> getCommands(){
        return commands.values();
    }
   
    public void registerCommands(Object...objects){
        for(Object object : objects) registerCommand(object);
    }
   
    public void registerCommand(Object object){
        for(Method method : object.getClass().getDeclaredMethods()){
            if(method.isAnnotationPresent(Command.class)){
                Command command = method.getAnnotation(Command.class);
                method.setAccessible(true);
                CommandBean commandBean = new CommandBean(command.name(), command.description(), command.type(), object, method);
                commands.put(command.name(), commandBean);
            }
        }
    }
   
    @SuppressWarnings("unchecked")
	public void commandConsole(String command){
        Object[] object = getCommand(command);
        if(object[0] == null) {
        	LOG.warning("Unknown command.");
        	return;
        }
        
        ExecutorType executorType = ((CommandBean)object[0]).getExecutorType();
        if(executorType != ExecutorType.CONSOLE && executorType != ExecutorType.ALL){
            LOG.warning("Bad executor command.");
            return;
        }
        
        try{
            execute(((CommandBean)object[0]), command, (ArrayList<String>)object[1], null);
        }catch(Exception exception){
            LOG.error("The method "+((CommandBean)object[0]).getMethod().getName()+" isn't correctly initialized.");
        }
    }
   
    @SuppressWarnings("unchecked")
	public void commandUser(User user, String command, Message message){
        Object[] object = getCommand(command);
        if(object[0] == null) {
        	LOG.warning("Unknown command.");
        	return;
        }
        ExecutorType executorType = ((CommandBean)object[0]).getExecutorType();
        if( executorType != ExecutorType.ALL && executorType != ExecutorType.USER) {
            LOG.warning("Bad executor command.");
        	return ;
        }
        try{
            execute(((CommandBean)object[0]), command,(ArrayList<String>)object[1], message);
        }catch(Exception exception){
            LOG.error("The method "+((CommandBean)object[0]).getMethod().getName()+" isn't correctly initialized.");
        }
    }
   
    private Object[] getCommand(String command){
        String[] commandSplit = command.split(" ");
        List<String> args = new ArrayList<>();
        for(int i = 1; i < commandSplit.length; i++) {
        	String argument  = commandSplit[i];
        	if(!"".equals(argument) && argument !=null) {
        		args.add(argument);
        	}
        }
        CommandBean commandBean = commands.get(commandSplit[0]);
        return new Object[]{commandBean, args};
    }
   
    private void execute(CommandBean commandBean, String command, List<String> args, Message message) throws Exception{
        Parameter[] parameters = commandBean.getMethod().getParameters();
        Object[] objects = new Object[parameters.length];
        for(int i = 0; i < parameters.length; i++){
            if(parameters[i].getType() == (new ArrayList<String>()).getClass()) objects[i] = args;
            else if(parameters[i].getType() == User.class) objects[i] = message == null ? null : message.getAuthor();
            else if(parameters[i].getType() == TextChannel.class) objects[i] = message == null ? null : message.getTextChannel();
            else if(parameters[i].getType() == PrivateChannel.class) objects[i] = message == null ? null : message.getPrivateChannel();
            else if(parameters[i].getType() == Guild.class) objects[i] = message == null ? null : message.getGuild();
            else if(parameters[i].getType() == String.class) objects[i] = command;
            else if(parameters[i].getType() == Message.class) objects[i] = message;
            else if(parameters[i].getType() == JDA.class) objects[i] = compteurBotDiscord.getJDA();
            else if(parameters[i].getType() == MessageChannel.class) objects[i] = message.getChannel();
        }
        commandBean.getMethod().invoke(commandBean.getObject(), objects);
    }
}
