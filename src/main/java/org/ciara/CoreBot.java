package org.ciara;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CoreBot extends TelegramLongPollingBot {

    public CoreBot(DefaultBotOptions options){
        super(options);
        System.out.println("Initializing core bot");
    }


    @Override
    public String getBotUsername() {
        return "Ciara Core Bot";
    }

    @Override
    public String getBotToken() {
        return "7378808698:AAHltdFa3AAq1bNhkeup1XCn-BtotcXeqVs";
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("Received update: " + update);
    }
}
