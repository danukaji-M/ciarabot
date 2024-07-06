package org.ciara;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoreBot extends TelegramLongPollingBot {

    static boolean hasMsg = false;
    static boolean hasDoc =false;
    static boolean hasKeyBoard = false;
    static HashMap <Integer,String> categoryMap = new HashMap<>();
    static int offset;
    DBConnection connection = new DBConnection();
    static int catId;
    static boolean category = false;


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

        if(update.hasCallbackQuery()){
            EditKeyBoard(update.getCallbackQuery());
        }

        Message msg = update.getMessage();
        if(msg.hasText()) {
            if(msg.getText().equals("/start")){
                hasMsg = true;
                hasDoc = false;
                hasKeyBoard = false;
                //Insert Msg Handling Part
                SendKeyBoard(msg);
            }
        }else if(msg.hasDocument()){
            hasMsg = false;
            hasDoc = true;
            hasKeyBoard = false;
            System.out.println("has Doc.");
            //insert Document Handling Part
            String docId = msg.getDocument().getFileId();
            System.out.println(docId);
            if(category){
                docHandler(docId);
            }

        }
    }

    private void docHandler(String docId){
        FileHandling fileHandling = new FileHandling();
        String FoldPath = fileHandling.FolderHandling("Torrent");
    }

    private InlineKeyboardMarkup createInlineKeyboardMarkup(int page) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        int limit = 5;
        int offset = (page - 1) * limit;

        try {
            ResultSet rs = connection.executeQuery("SELECT * FROM category LIMIT " + limit + " OFFSET " + offset);

            while (rs.next()) {
                Integer id = rs.getInt("c_id");
                String category = rs.getString("category");
                List<InlineKeyboardButton> row = new ArrayList<>();
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(category);
                inlineKeyboardButton.setCallbackData(String.valueOf(id));
                row.add(inlineKeyboardButton);
                buttons.add(row);

                // Assuming categoryMap is declared and initialized elsewhere
                categoryMap.put(id, category);
            }

            List<InlineKeyboardButton> rowInline = getInlineKeyboardButtons(page);
            buttons.add(rowInline);

            markupInline.setKeyboard(buttons);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

        return markupInline;
    }

    private static @NotNull List<InlineKeyboardButton> getInlineKeyboardButtons(int page) {
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton nextButton = new InlineKeyboardButton();
        InlineKeyboardButton previousButton = new InlineKeyboardButton();

        nextButton.setText("Next");
        nextButton.setCallbackData("next:" + (page + 1));

        previousButton.setText("Previous");
        previousButton.setCallbackData("prev:" + (page - 1));


        if(page > 1){
            rowInline.add(previousButton);
        }
        rowInline.add(nextButton);
        return rowInline;
    }

    private void MsgHandler(String msg){

    }

    private void keyBoardHandler(String docId){

    }
    private void DownloadTorrent(Message msg, String path){

    }

    private void SendKeyBoard(Message msg){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(msg.getChatId());
        sendMessage.setText("Select Film Categories: ");
        InlineKeyboardMarkup markupInline = createInlineKeyboardMarkup(1);
        sendMessage.setReplyMarkup(markupInline);
        try{
            execute(sendMessage);
        }catch (TelegramApiException e){
            throw new RuntimeException(e);
        }
    }
    private void EditKeyBoard(CallbackQuery callbackQuery) {
        String callBackData = callbackQuery.getData();
        String chatId = callbackQuery.getMessage().getChatId().toString();
        int messageId = callbackQuery.getMessage().getMessageId();

        // Handle callback query data
        if (callBackData.startsWith("prev:")) {
            int prevPage = Integer.parseInt(callBackData.split(":")[1]);
            InlineKeyboardMarkup newMarkup = createInlineKeyboardMarkup(prevPage);
            EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
            editMarkup.setChatId(chatId);
            editMarkup.setMessageId(messageId);
            editMarkup.setReplyMarkup(newMarkup);
            try {
                execute(editMarkup);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (callBackData.startsWith("next:")) {
            int nextPage = Integer.parseInt(callBackData.split(":")[1]);
            InlineKeyboardMarkup newMarkup = createInlineKeyboardMarkup(nextPage);
            EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
            editMarkup.setChatId(chatId);
            editMarkup.setMessageId(messageId);
            editMarkup.setReplyMarkup(newMarkup);
            try {
                execute(editMarkup);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            category = true;
            catId = Integer.parseInt(callbackQuery.getData());
            String category = categoryMap.get(catId);
            SendTextMessage("Send Your .Torrent File: You Selected category is - " + category, chatId);
        }
    }
    private void SendTextMessage(String text, String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try{
            execute(sendMessage);
        }catch (TelegramApiException e){
            throw new RuntimeException(e);
        }
    }
}
