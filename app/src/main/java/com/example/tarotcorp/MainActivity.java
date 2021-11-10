package com.example.tarotcorp;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tarotcorp.ui.main.MainFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//importacion de librerias necesarias para manejar los Buttons, editText, etc
//imports necessarios para manejar los eventos de los botones
//imports necesarios para manejar archivos, arraylist y funciones de random

public class MainActivity extends AppCompatActivity {
//documenta este codigo
    //declaracion de funcion onCreate
    //sirve para inicializar los componentes de la actividad
    //la actividad se crea cuando se inicia
    //la actividad se destruye cuando se cierra

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    //funcion onConfigurationChanged, sirve para manejar la orientacion de la pantalla
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(this, "onConfigurationChanged", Toast.LENGTH_SHORT).show();
    }
    /*
        funcion irAtras, sirve para el metodo onClick de los botones en los frames correspondientes
        te permite ir a la actividad anterior
        cada boton esta asociado a un frame y cada 1 tiene un metodo onClick
    */
    @SuppressLint("NonConstantResourceId")
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
    //se declaran los arraylist de forma global para poder usarlos en todas las funciones sin que cambie sus datos

    ArrayList<String> emails = new ArrayList<>();
    ArrayList<String> numeros = new ArrayList<>();
    ArrayList<String> contraseñas = new ArrayList<>();
    ArrayList<String> validarContraseña = new ArrayList<>();
    //se declaran los EditText de forma global para poder usarlos en todas las funciones sin que cambie sus datos
    /*
    se usa null ya que no se puede inicializar con valores predefinidos ya que no existen hasta el momento de que el usuario
    abra la actividad
    */
    EditText textEmail = null;
    EditText textNumeros = null;
    EditText textPassword = null;
    EditText textPasswordConfirm = null;
    /*
        se crea la funcion registroUsuarios para que sea llamada por el boton con id registro
        dentro de esta vista se crean los EditText para que el usuario pueda ingresar sus datos una vez complete
        el formulario el usuario presiona el boton registrarse el cual esta en un case y llama a la logica de registro
    */
    @SuppressLint("NonConstantResourceId")
    public void registroUsuarios(View v){

        switch(v.getId()){

            case R.id.registro:
                setContentView(R.layout.registro);
                break;

            case R.id.registrarse:
            //se puede crear una funcion que contenga todas las validaciones de los campos y que se llame en el case
            //lo cual genera un codigo mas limpio
            //por el momento se deja así para no alargar el tiempo de entrega, en una futura actualizacion se puede llevar a cabo la implementacion de lo dicho 
            //a dia de hoy 31/10/2021
                textEmail = findViewById(R.id.email);
                textNumeros = findViewById(R.id.Numero);
                textPassword = findViewById(R.id.Password);
                textPasswordConfirm = findViewById(R.id.PasswordConfirm);
                boolean txtEmail = textEmail.getText().toString().isEmpty();
                boolean txtNumeros = textNumeros.getText().toString().isEmpty();
                boolean txtPassword = textPassword.getText().toString().isEmpty();
                boolean txtValidarPassword = textPasswordConfirm.getText().toString().isEmpty();

                //este condicional es para validar que el usuario ingrese un correo electronico ya que con las variables boolean van a retornar 
                //true si el campo esta vacio o false si el campo no esta vacio
                //en caso de que sea true se muestra un mensaje de error advirtiendo que el email esta en uso  y sino lo esta se procede a agregar al arraylist
                if(!txtEmail && !txtNumeros && !txtPassword && !txtValidarPassword){
                    if(validarSiExiste(emails, textEmail.getText().toString()) && validarSiExiste(numeros, textNumeros.getText().toString()) ){
                        Toast.makeText(this, "El email: " + textEmail.getText().toString() + "y numero: " + textNumeros.getText().toString() + " Ya existe.", Toast.LENGTH_SHORT).show();
                    }else{
                        String email = textEmail.getText().toString();
                        String numero = textNumeros.getText().toString();
                        if(validarEmail(email)) {
                            emails.add(email);
                        }else{
                            Toast.makeText(this, "El email: " + email + " no es valido.", Toast.LENGTH_SHORT).show();
                        }
                        if(validarNumero(numero)){
                            numeros.add(numero);
                        }else{
                            Toast.makeText(this, "El numero: " + numero + " no es valido.", Toast.LENGTH_SHORT).show();
                        }
                        if(textPassword.getText().toString().equals(textPasswordConfirm.getText().toString())){
                            String contraseña = textPassword.getText().toString();
                            String contraseñaConfirm = textPasswordConfirm.getText().toString();

                            contraseña = encriptar(contraseña);
                            contraseñaConfirm = encriptar(contraseñaConfirm);

                            contraseñas.add(contraseña);
                            validarContraseña.add(contraseñaConfirm);

                            Toast.makeText(this, "Usuario Registrado!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, "Las contraseñas deben ser iguales!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(this, "Rellena todos los campos!.", Toast.LENGTH_SHORT).show();
                }
                break;
                //en el caso del boton ingresar se espera que el usuario ingrese su correo electronico y su contraseña
                //hay una validacion la cual permite saber si el correo ingresado ya esta en el arraylist de emails, sino es así
                //muestra un mensaje de error advirtiendo que el email no existe
            case R.id.ingresar:
                textEmail = findViewById(R.id.email);
                textNumeros = findViewById(R.id.Numero);
                textPassword = findViewById(R.id.Password);
                textPasswordConfirm = findViewById(R.id.PasswordConfirm);
                txtEmail = textEmail.getText().toString().isEmpty();
                txtPassword = textPassword.getText().toString().isEmpty();
                if(txtEmail){
                    Toast.makeText(this, "Ingrese su correo electronico!", Toast.LENGTH_SHORT).show();
                }
                if(txtPassword){
                    Toast.makeText(this, "Ingrese su contraseña!", Toast.LENGTH_SHORT).show();
                }else if(!txtEmail && !txtPassword) {
                    if(!validarSiExiste(emails, textEmail.getText().toString())){
                        Toast.makeText(this, "El email: " + textEmail.getText().toString() + " no existe.", Toast.LENGTH_SHORT).show();
                    }else{
                        if(validarEmail(textEmail.getText().toString())) {
                            String email = textEmail.getText().toString();
                            String contraseña = textPassword.getText().toString();
                            contraseña = encriptar(contraseña);
                            if(validarSiExiste(contraseñas, contraseña)){
                                Toast.makeText(this, "Bienvenido!", Toast.LENGTH_SHORT).show();
                                setContentView(R.layout.menuprincipal);
                            }else{
                                Toast.makeText(this, "La contraseña es incorrecta!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(this, "El email: " + textEmail.getText().toString() + " no es valido.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(this, "Rellena todos los campos!.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    //funcion para encriptar la contraseña
    public String encriptar(String contraseña){
        String passEncriptada = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(contraseña.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            passEncriptada = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passEncriptada;
    }

    //funcion para validar un correo electronico regex
    public boolean validarEmail(String email){
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
    //funcion para validar un numero telefonico regex
    public boolean validarNumero(String numero){
        Pattern pattern = Pattern.compile("^[0-9]{10}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(numero);
        return matcher.find();
    }


    //con la funcion validarSiExiste permite saber que el flujo del programa sea el adecuado, en este campo nosotros tenemos
    //mostrando un mensaje el cual pues al usuario no va entender porque sale
    //pero nos sirve a nosotros para saber si hace la validacion correctamente
    //tambien manejamos las excepciones que puedan salir en el flujo del programa
    //en este caso se usa el try catch para que el programa no se detenga si hay una excepcion y no se cierre inmediatamente
    //en caso de que no se haya podido validar el correo electronico se muestra un mensaje de error advirtiendo que el email no existe
    //en este momento usamos una excepcion global ya que no encontramos en las pruebas errores espesificos que se puedan presentar
    //pero decidimos manejarlas para que el programa no se detenga como tal.
    public boolean validarSiExiste(ArrayList<String> datos, String textoClaro){
        try {
            if (datos.contains(textoClaro)) {
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

    //en este caso creamos ImageViews para que el usuario pueda ver las imagenes que se muestran en el juego
    //TextView para la interpretacion de cada imagen
    //random el cual genera numeros aleatorios para que el usuario pueda ver las imagenes
    //y todas estas son globales para que el usuario pueda ver las imagenes y las interpretaciones
    ImageView tarot = null;
    TextView interpretacion = null;
    Random aleatorio = new Random(System.currentTimeMillis());
    int intAletorio = 0;
    //la funcion mostrarImagen permite mostrar las imagenes y las interpretaciones en el juego
    //intAleatorio es una variable que nos permite generar numeros aleatorios para que el usuario pueda ver las imagenes
    //aleatorio.setSeed permite que cuando el usuario vuelve hacia atras se vuelva a mostrar una imagen diferente y no se muestre la misma con su interpretacion
    //por el momento se tienen 3 casos diferentes para que el usuario pueda ver las imagenes y sus interpretaciones
    //pero se pueden agregar mas casos para que el usuario pueda ver mas imagenes y sus interpretaciones
    @SuppressLint("NonConstantResourceId")
    public void mostrarImagen(View v){

        intAletorio = aleatorio.nextInt(3)+1;
        aleatorio.setSeed(System.currentTimeMillis());
        //en este primer switch lo que se hace es buscar el nombre de las imagenes en este caso imageView1, imageView2, imageView3
        //para pasarlas al segundo switch y que este pueda mostrarlas adecuadamente
        switch(intAletorio){
            case 1:
                tarot = findViewById(R.id.imageView1);
                interpretacion = findViewById(R.id.interpretacion1);
                break;
            case 2:
                tarot = findViewById(R.id.imageView2);
                interpretacion = findViewById(R.id.interpretacion2);
                break;
            case 3:
                tarot = findViewById(R.id.imageView3);
                interpretacion = findViewById(R.id.interpretacion3);
                break;
        }

        //este segundo switch nos permite mostrar las imagenes y las interpretaciones en el juego en una nueva vista
        switch (v.getId()){
            case R.id.barajarCartas:
                tarot.setVisibility(View.VISIBLE);
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
    //esta funcion mostrarDestino refleja las imagenes y las interpretaciones en el juego y es llamada desde la funcion mostrarImagen
    public void mostrarDestino(ImageView num, TextView inter){
        num.setVisibility(View.VISIBLE);
        inter.setVisibility(View.VISIBLE);
    }
    //esta funcion permite entrar a la vista tarotDiario
    @SuppressLint("NonConstantResourceId")
    public void tarotDiarioActive(View v){
        if (v.getId() == R.id.tarotDiario) {
            setContentView(R.layout.tarotdiario);
        }
    }
//y esta funcion permite entrar a la vista tiradaClasica
    @SuppressLint("NonConstantResourceId")
    public void tiradaClasica(View v){
        if (v.getId() == R.id.tiradaClasica) {
            setContentView(R.layout.tiradaclasica);
        }
    }
}