package bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

public class Bot extends TelegramLongPollingBot {
    //создаем две константы, присваиваем им значения токена и имя бота соответсвтенно
    //вместо звездочек подставляйте свои данные
    final private String BOT_TOKEN = "5471621260:AAETI4rqzoMdnYTFNGBECwAbFlMCTXKM274";
    final private String BOT_NAME = "@MeshEvgFirst_bot";

    Storage storage;
    Rate rate;
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    SendMessage outMess = new SendMessage();
    Message inMess;
    String response;



    public Bot() throws IOException {
        initKeyboard ();
        storage = new Storage();
        rate = new Rate();

    }




    void initKeyboard () {
        //Создаем объект будущей клавиатуры и выставляем нужные настройки
        replyKeyboardMarkup.setResizeKeyboard(true); //подгоняем размер
        replyKeyboardMarkup.setOneTimeKeyboard(false); //скрываем после использования

        //Создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();




        //Создаем один ряд кнопок и добавляем его в список
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRows.add(keyboardRow);

        //Добавляем одну кнопку с текстом "Просвяти" наш ряд
        keyboardRow.add(new KeyboardButton("Просвяти"));
        keyboardRow.add(new KeyboardButton("Курс"));

        //добавляем лист с одним рядом кнопок в главный объект
        replyKeyboardMarkup.setKeyboard(keyboardRows);

    }






        @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }





    @Override
    public void onUpdateReceived(Update update) {


        try{
            if(update.hasMessage() && update.getMessage().hasText())  {

                //Извлекаем из объекта сообщение пользователя
                inMess = update.getMessage();
                //Достаем из inMess id чата пользователя
                String chatId = inMess.getChatId().toString();
                //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
                response = parseMessage(inMess.getText());


                //Создаем объект класса SendMessage - наш будущий ответ пользователю

                //Добавляем в наше сообщение id чата а также наш ответ
                outMess.setChatId(chatId);
                outMess.setText(response);
                outMess.setReplyMarkup(replyKeyboardMarkup);


                //Отправка в чат
                execute(outMess);

            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public String parseMessage(String textMsg) {
       response = "Сообщение не распознано";


        //Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
        if(textMsg.equals("/start")) {
            response = "Приветствую! Это мой первый бот! \nОн знает много цитат. Жми /get чтобы получить случайную из них.\n" +
                    "А так же ты можешь узнать курс валют /rate";
        }

        if (textMsg.equals("/get") || textMsg.equals("Просвяти")) {
            response = storage.getRandQuote();
        }

        if(textMsg.equals("/rate") || textMsg.equals("Курс")) {

            try {
                response = rate.output();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return response;
    }

}
