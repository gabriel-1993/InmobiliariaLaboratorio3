package com.ulpsoft.inmobiliarialaboratorio3.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulpsoft.inmobiliarialaboratorio3.model.CrearInmuebleRespuesta;
import com.ulpsoft.inmobiliarialaboratorio3.request.ApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleCrearViewModel extends AndroidViewModel {

    Context context;
    //foto
    private MutableLiveData<Uri> mAvatar;
    private MutableLiveData<String> mIdInmueble;

    private String avatar;


    public InmuebleCrearViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }

    public LiveData<Uri> getMAvatar(){
        if(mAvatar == null){
            mAvatar = new MutableLiveData<>();
        }
        return mAvatar;
    }

    public MutableLiveData<String> getmIdInmueble() {
        if(mIdInmueble==null){
            mIdInmueble = new MutableLiveData<>();
        }
        return mIdInmueble;
    }

    public void crearInmueble(String token, String direccionn, String tipoo, String usoo, String ambientesString, String precioString) {

        // Validación de datos vacíos
        if (token.isEmpty() || direccionn.isEmpty() || tipoo.isEmpty() || usoo.isEmpty() || ambientesString.isEmpty() || precioString.isEmpty()) {
            Toast.makeText(context, "Datos vacíos: revise los datos ingresados", Toast.LENGTH_LONG).show();
            return;
        }

        // Validación de dirección: mínimo 5 caracteres, debe contener letras, números y espacios
        if (direccionn.length() < 5 || !direccionn.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\s).{5,}$")) {
            Toast.makeText(context, "La dirección debe tener al menos 5 caracteres y contener letras, espacios y números (por ej., 'San Martin 1343').", Toast.LENGTH_LONG).show();
            return;
        }

        int ambientess;

        // Validación de ambientes (solo números enteros positivos)
        try {
            ambientess = Integer.parseInt(ambientesString);
            if (ambientess <= 0) {
                Toast.makeText(context, "El número de ambientes debe ser un número entero positivo.", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(context, "El número de ambientes debe ser un número entero válido.", Toast.LENGTH_LONG).show();
            return;
        }

        // Validación de tipo (solo letras y espacios)
        if (!tipoo.matches("[a-zA-Z\\s]+")) {
            Toast.makeText(context, "El tipo solo puede contener letras y espacios.", Toast.LENGTH_LONG).show();
            return;
        }

        // Validación de uso (solo letras y espacios)
        if (!usoo.matches("[a-zA-Z\\s]+")) {
            Toast.makeText(context, "El uso solo puede contener letras y espacios.", Toast.LENGTH_LONG).show();
            return;
        }

        Double precioo;

        // Validación de precio (solo números)
        try {
            precioo = Double.parseDouble(precioString.replace(",", ".")); // Cambiar coma por punto si es necesario
            if (precioo <= 0) {
                Toast.makeText(context, "El precio debe ser un número positivo.", Toast.LENGTH_LONG).show();
                return;
            }

            // Llamada al API
            Call<CrearInmuebleRespuesta> call = ApiClient.getApiInmobiliaria().agregarInmueble("Bearer " + token, direccionn, String.valueOf(ambientess), tipoo, usoo, String.valueOf(precioo));

            // Ejecutar la llamada a la API
            call.enqueue(new Callback<CrearInmuebleRespuesta>() {
                @Override
                public void onResponse(Call<CrearInmuebleRespuesta> call, Response<CrearInmuebleRespuesta> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(context, "Inmueble agregado exitosamente, puede agregar una foto.", Toast.LENGTH_LONG).show();

                        CrearInmuebleRespuesta inmuebleRespuesta = response.body();
                        String idInmueble = inmuebleRespuesta.getInmuebleId();
                        //System.out.println("ID del inmueble agregado: " + idInmueble); // Agregar log del ID
                        mIdInmueble.postValue(idInmueble);

                    } else {
                        Toast.makeText(context, "Error al agregar inmueble: " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<CrearInmuebleRespuesta> call, Throwable t) {
                    Toast.makeText(context, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } catch (NumberFormatException e) {
            Toast.makeText(context, "El precio debe ser un número válido.", Toast.LENGTH_LONG).show();
        }
    }



    public void recibirFoto(ActivityResult result){

        if(result.getResultCode() == RESULT_OK){
            Intent data = result.getData();
            Uri uri = data.getData();

            Log.d("URI de la foto:", uri.toString());


            avatar = uri.toString();
            Log.d("result foto:", avatar);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                context.getContentResolver().takePersistableUriPermission (uri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            mAvatar.setValue(uri);
        }
    }


    public void actualizarAvatar() {
        if (mAvatar.getValue() == null) {
            Toast.makeText(context, "No se ha seleccionado ninguna imagen para actualizar.", Toast.LENGTH_LONG).show();
            return;
        }

        Uri avatarUri = mAvatar.getValue();

        // Crear un archivo temporal a partir del Uri
        File avatarFile = createTemporaryFileFromUri(avatarUri);
        if (avatarFile == null) {
            Toast.makeText(context, "Error: no es posible enviar imagen al servidor.", Toast.LENGTH_LONG).show();
           // Log.e("Error:", "Archivo temporal no creado");
            return;
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), avatarFile);
        MultipartBody.Part avatarPart = MultipartBody.Part.createFormData("avatarFile", avatarFile.getName(), requestFile);

        // Crear el RequestBody para el idInmueble
        String idInmuebleValue = mIdInmueble.getValue();
        if (idInmuebleValue == null) {
            Toast.makeText(context, "El ID del inmueble no está disponible.", Toast.LENGTH_LONG).show();
            return;
        }

        // Convertir a int
        int idInmuebleInt;
        try {
            idInmuebleInt = Integer.parseInt(idInmuebleValue);
        } catch (NumberFormatException e) {
            Toast.makeText(context, "El ID del inmueble no es válido.", Toast.LENGTH_LONG).show();
            return;
        }
        RequestBody idInmueble = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(idInmuebleInt));


        Call<ResponseBody> call = ApiClient.getApiInmobiliaria().modificarAvatarInmueble("Bearer " + ApiClient.Leer(context),  idInmueble, avatarPart);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Foto modificada con exito.", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        String errorMessage = response.errorBody().string(); // Captura el cuerpo de la respuesta de error
                        Log.d("Error response:", errorMessage);
                        Toast.makeText(context, "Error: no es posible enviar imagen al servidor.", Toast.LENGTH_LONG).show();
                        //Toast.makeText(context, "Error: no es posible enviar imagen al servidor." + response.code(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error al procesar el error de respuesta.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Error de conexion al intentar actualizar la foto.", Toast.LENGTH_LONG).show();
            }
        });
    }



    private File createTemporaryFileFromUri(Uri uri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                return null;
            }

            // Crear archivo temporal
            File tempFile = new File(context.getCacheDir(), "tempAvatarFile.jpg");
            FileOutputStream outputStream = new FileOutputStream(tempFile);

            // Copiar el contenido del InputStream al archivo temporal
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}

