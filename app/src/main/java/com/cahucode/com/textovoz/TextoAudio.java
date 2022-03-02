package com.cahucode.com.textovoz;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

/**
 * Creado por Carlos_Code el 02/03/2022.
 * carlos.japon.code@gmail.com
 */

class TextoAudio implements TextToSpeech.OnInitListener {
    private Context context;
    private TextToSpeech tts;

    public TextoAudio(Context context) {
        this.context = context;
        tts = new TextToSpeech(context, this);
    }

    public void audio(String texto){
        // QUEUE_ADD = reproducir audios uno despues de etros.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(texto, TextToSpeech.QUEUE_ADD, null, null);
        } else {
            tts.speak(texto, TextToSpeech.QUEUE_ADD, null);
        }
        tts.setSpeechRate(0.0f);
        tts.setPitch(0.0f);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.getDefault());
            if (status == TextToSpeech.LANG_MISSING_DATA || status == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, "Idima no soportado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Inicilizacion TTS fallida!", Toast.LENGTH_SHORT).show();
        }
    }

    public void ttsStop(){
        if (tts != null) {
            tts.stop();
        }
    }
    public void ttsDestroy(){
        if (tts != null) {
            tts.shutdown();
        }
    }
}
