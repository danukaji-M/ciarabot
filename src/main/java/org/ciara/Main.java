package org.ciara;


import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    public static void main(String[] args) {
        coreBotThread coreBotThread = new coreBotThread();
        coreBotThread.start();
    }
}

class coreBotThread extends Thread{
    @Override
    public void run(){
        DefaultBotOptions botOptions = new DefaultBotOptions();
        botOptions.setBaseUrl("http://localhost:1300/bot");
        CoreBot bot = new CoreBot(botOptions);
        try{
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        }catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}