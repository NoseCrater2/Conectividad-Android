package com.constantine.smsapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText numeroTelefono, mensaje;
    Button enviarMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numeroTelefono = findViewById(R.id.txtNumeroTel);
        mensaje = findViewById(R.id.txtMensaje);
        enviarMensaje = findViewById(R.id.btnEnviarMensaje);

        enviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero = numeroTelefono.getText().toString();
                String msj = mensaje.getText().toString();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(numero, null, msj, pIntent, null);

                Toast.makeText(getApplicationContext(), "Se ha enviado el mensaje!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
