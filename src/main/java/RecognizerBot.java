import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Voice;
import org.telegram.telegrambots.meta.generics.BotSession;

public class RecognizerBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "MyRecognizerBot";
    }

    @Override
    public String getBotToken() {
        return "2031500557:AAEzCqHJVCLDUELNRFkIBUQRy31nMC70w3A";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasVoice()) {
            Voice voice = update.getMessage().getVoice();
            Long chatId = update.getMessage().getChatId();

            System.out.println(voice.toString());
            System.out.println(voice.getDuration());
            System.out.println(voice.getFileId());
            System.out.println(voice.getFileSize());
            System.out.println(voice.getFileUniqueId());
            System.out.println(voice.getMimeType());

            GetFile file = new GetFile();
            file.setFileId(voice.getFileId());

            try {
                File tFile = execute(file);
                downloadFile(tFile, new java.io.File(voice.getFileUniqueId() + ".ogg"));
                SendMessage sendMessage = new SendMessage(chatId.toString(), "path");
                execute(sendMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
