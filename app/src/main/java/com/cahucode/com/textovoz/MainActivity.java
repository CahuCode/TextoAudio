package com.cahucode.com.textovoz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView txtIn, txtOut;
    private EditText txtAudio;
    private Button btnAudioText, btnTextoAudio;
    private TextoAudio reproducir;

    @Override
    protected void onStop() {
        super.onStop();
        if (reproducir != null)
            reproducir.ttsStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (reproducir != null)
            reproducir.ttsDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reproducir = new TextoAudio(MainActivity.this);

        txtIn = findViewById(R.id.txt_audioIn);
        txtOut = findViewById(R.id.txt_audioOut);
        txtAudio = findViewById(R.id.txt_audio);
        btnAudioText = findViewById(R.id.btnAudioTexto);
        btnTextoAudio = findViewById(R.id.btnTextoAudio);

        btnAudioText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hablar();
            }
        });

        btnTextoAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reproducir.audio(txtAudio.getText().toString().trim());
            }
        });
    }

    //Reconocer audio
    public void hablar() {
        Intent hablar = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        hablar.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        hablar.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        startActivityForResult(hablar, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<String> preguntasTexto = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String pregunta = preguntasTexto.get(0);
            buscarRespuesta(pregunta);
        }
    }

    private void buscarRespuesta(String pregunta) {
        String respuesta = "";
        for (PreguntaRespuesta item : Respuestas()) {
            if (item.getPregunta().contains(pregunta)) {
                respuesta = item.getRespuesta();
                break;
            }
        }
        if (respuesta.equals("")){
            Toast.makeText(this, "No existe una coincidencia, pruebe con preguntas como: Hola o información", Toast.LENGTH_SHORT).show();
        } else {
           txtIn.setText(pregunta);
           txtOut.setText(respuesta);
            reproducir.audio(respuesta);
        }
    }

    private ArrayList<PreguntaRespuesta> Respuestas() {
        ArrayList<PreguntaRespuesta> respuestas = new ArrayList<>();
        respuestas.add(new PreguntaRespuesta("Hola", "Hola, la funcionalidad del app es correcta"));
        respuestas.add(new PreguntaRespuesta("información", "El aplicativo captura un comando de voz mediante el intent ACTION_RECOGNIZE_SPEECH y responde en base a un texto predefinido, ademas ocupa TEXT TO SPEECH (TTS) para pasar el texto a audio"));
        return respuestas;
    }


}