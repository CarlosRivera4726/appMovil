package com.example.tarotcorp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;

import com.example.tarotcorp.ui.main.MainFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(this, "onConfigurationChanged", Toast.LENGTH_SHORT).show();
    }
    public void irAtras(View v){
        switch(v.getId()){
            case R.id.goBack1:
                setContentView(R.layout.main_activity);
                break;

            case R.id.goBack2:
                setContentView(R.layout.menuprincipal);
                break;

            case R.id.goBack3:
                setContentView(R.layout.tarotdiario);
                break;
            case R.id.goBack4:
                setContentView(R.layout.tiradaclasica);
                break;


        }

    }
    @SuppressLint("NonConstantResourceId")
    //arraylist
    ArrayList<String> emails = new ArrayList<>();
    ArrayList<String> numeros = new ArrayList<>();
    ArrayList<String> contraseña = new ArrayList<>();
    ArrayList<String> validarContraseña = new ArrayList<>();
    //variables
    EditText textEmail = null;
    EditText textNumeros = null;
    EditText textPassword = null;
    EditText textPasswordConfirm = null;

    public void registroUsuarios(View v){
        switch(v.getId()){
            case R.id.registro:
                setContentView(R.layout.registro);
                break;

            case R.id.registrarse:
                textEmail = (EditText) findViewById(R.id.email);
                textNumeros = (EditText) findViewById(R.id.Numero);
                textPassword = (EditText) findViewById(R.id.Password);
                textPasswordConfirm = (EditText) findViewById(R.id.PasswordConfirm);
                boolean txtEmail = textEmail.getText().toString().isEmpty();
                boolean txtNumeros = textNumeros.getText().toString().isEmpty();
                boolean txtPassword = textPassword.getText().toString().isEmpty();
                boolean txtValidarPassword = textPasswordConfirm.getText().toString().isEmpty();

                if(!txtEmail && !txtNumeros && !txtPassword && !txtValidarPassword){
                    if(validarSiExiste(emails, textEmail.getText().toString()) || validarSiExiste(numeros, textNumeros.getText().toString()) ){
                        Toast.makeText(this, "El email: " + textEmail.getText().toString() + "y numero: " + textNumeros.getText().toString() + " Ya existe.", Toast.LENGTH_SHORT).show();
                    }else{
                        emails.add(textEmail.getText().toString());
                        numeros.add(textNumeros.getText().toString());
                        if(textPassword.getText().toString().equals(textPasswordConfirm.getText().toString())){
                            contraseña.add(textPassword.getText().toString());
                            validarContraseña.add(textPasswordConfirm.getText().toString());
                            Toast.makeText(this, "Usuario Registrado!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, "Las contraseñas deben ser iguales!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(this, "Rellena todos los campos!.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ingresar:
                //System.out.println(emails);
                //System.out.println(textEmail.getText().toString());
                if(emails.isEmpty() || textEmail.getText().toString().isEmpty()){
                    Toast.makeText(this, "Ingresa los datos primero", Toast.LENGTH_SHORT).show();
                }else {
                    if (validarSiExiste(emails, textEmail.getText().toString())) {
                        setContentView(R.layout.menuprincipal);
                        textEmail.setText(null);
                    } else {
                        Toast.makeText(this, "Por favor registre el correo.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
        }
    }
    public boolean validarSiExiste(ArrayList<String> datos, String textoClaro){
        try {
            if (datos.contains(textoClaro)) {
                Toast.makeText(this, "Existe!", Toast.LENGTH_SHORT).show();
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            setContentView(R.layout.registro);

            Toast.makeText(this, "No existe el usuario!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    //int valorDado = (int) Math.random()*3+1;
    ImageView tarot = null;
    TextView interpretacion = null;
    Random aleatorio = new Random(System.currentTimeMillis());
    int intAletorio = 0;

    public void mostrarImagen(View v){

        intAletorio = aleatorio.nextInt(3)+1;
        aleatorio.setSeed(System.currentTimeMillis());

        switch(intAletorio){
            case 1:
                tarot = (ImageView) findViewById(R.id.imageView1);
                interpretacion = (TextView) findViewById(R.id.interpretacion1);
                break;
            case 2:
                tarot = (ImageView) findViewById(R.id.imageView2);
                interpretacion = (TextView) findViewById(R.id.interpretacion2);
                break;
            case 3:
                tarot = (ImageView) findViewById(R.id.imageView3);
                interpretacion = (TextView) findViewById(R.id.interpretacion3);
                break;
        }

        switch (v.getId()){
            case R.id.barajarCartas:
                tarot.setVisibility(v.VISIBLE);
                Toast.makeText(this, "Toca la imagen para mostrarla en otra vista!.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.imageView1:
                setContentView(R.layout.imagenes);
                mostrarDestino(findViewById(R.id.imageView1), findViewById(R.id.interpretacion1));
                break;
            case R.id.imageView2:
                setContentView(R.layout.imagenes);
                mostrarDestino(findViewById(R.id.imageView2), findViewById(R.id.interpretacion2));
                break;
            case R.id.imageView3:
                setContentView(R.layout.imagenes);
                mostrarDestino(findViewById(R.id.imageView3), findViewById(R.id.interpretacion3));
                break;


        }
    }
    public void mostrarDestino(ImageView num, TextView inter){
        num.setVisibility(View.VISIBLE);
        inter.setVisibility(View.VISIBLE);
    }
    @SuppressLint("NonConstantResourceId")
    public void tarotDiarioActive(View v){
        switch (v.getId()){

            case R.id.tarotDiario:
                setContentView(R.layout.tarotdiario);
                break;
        }
    }

    public void tiradaClasica(View v){
        switch(v.getId()){
            case R.id.tiradaClasica:
                setContentView(R.layout.tiradaclasica);
                break;
        }
    }
}