


package com.ulpsoft.inmobiliarialaboratorio3.ui.perfil;


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

import com.ulpsoft.inmobiliarialaboratorio3.model.Propietario;
import com.ulpsoft.inmobiliarialaboratorio3.request.ApiClient;

import java.io.File;
import java.io.FileNotFoundException;
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
import retrofit2.http.Field;
import retrofit2.http.Header;


public class PerfilViewModel extends AndroidViewModel {

    private Propietario propietario;
    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Uri> mAvatar;
    private String avatar;
    private MutableLiveData<String> mMsjUsuario;

    private Context context;



    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }


    public MutableLiveData<Propietario> getmPropietario(){
        if (mPropietario == null) {
            mPropietario = new MutableLiveData<Propietario>();
        }
        return mPropietario;
    }

    public LiveData<Uri> getMAvatar(){
        if(mAvatar == null){
            mAvatar = new MutableLiveData<>();
        }
        return mAvatar;
    }


    public MutableLiveData<String> getmMsjUsuario(){
        if (mMsjUsuario == null) {
            mMsjUsuario = new MutableLiveData<String>();
        }
        return mMsjUsuario;
    }


    /*    recuperar datos con el id del token */
    public void recuperarDatosPropietarioToken(){

        Call<Propietario> propietarioCall = ApiClient.getApiInmobiliaria().GetPerfilPropietario("Bearer "+ApiClient.Leer(context));

        propietarioCall.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()){
                    propietario = response.body();
                    Log.d("SALiDA", response.body().toString());
                    avatar = propietario.getAvatar();
                    mPropietario.postValue(propietario);
                }else{
                    Log.d("SALiDA", response.message());
                    Toast.makeText(context, "onResponse: Error de respuesta API getPerfilPropietario()", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Toast.makeText(context, "onFaailure: Error de respuesta API getPerfilPropietario()", Toast.LENGTH_SHORT).show();
            }
        });

    }



    /*modificar datos del propietario (dni,nombre,apellido,telefono,email) clave y avatar tienen sus metodos invididuales*/
    public void modificarPerfil(String dni, String apellido, String nombre, String telefono, String email){
        //Validar que existan cambios
        if(mPropietario.getValue().getDni().trim().equals(dni.trim()) &&
                mPropietario.getValue().getApellido().trim().equals(apellido.trim()) &&
                mPropietario.getValue().getNombre().trim().equals(nombre.trim()) &&
                mPropietario.getValue().getTelefono().trim().equals(telefono.trim()) &&
                mPropietario.getValue().getEmail().trim().equals(email.trim())) {

            mMsjUsuario.setValue("No se encontraron modificaciones.");
            return;
        }

        //Validar campos obligatorios
        if(dni.isEmpty() || apellido.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || email.isEmpty()){
            mMsjUsuario.setValue("Revisar datos vacios, unico dato que no es obligatorio: telefono .");
            return;
        }

        //Validar campos si son distintos a vacio

        // Validación de DNI (debería ser numerico y tener entre 5 y 10 caracteres, por ejemplo)
        if (!dni.matches("\\d{5,10}")) {
            mMsjUsuario.setValue("DNI: Debe contener solo números y tener entre 5 y 10 dígitos.");
            return;
        }

        // Validación de Apellido (solo letras, mínimo 2 caracteres)
        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,12}")) {
            mMsjUsuario.setValue("Apellido: Debe contener solo letras y tener entre 2 y 12 caracteres.");
            return;
        }

        // Validación de Nombre (solo letras, mínimo 2 caracteres)
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,20}")) {
                    mMsjUsuario.setValue("Nombre:  Debe contener solo letras y tener entre 2 y 20 caracteres.");
                    return;
                }

        // Validación de Teléfono (opcional, solo si no está vacío)
        if (!telefono.matches("[0-9+\\-\\s]{7,15}")) {
            mMsjUsuario.setValue("Teléfono: Debe contener solo números, guiones o espacios, y tener entre 7 y 15 dígitos.");
            return;
        }

        // Validación de Email (patrón simple para correo electrónico válido)
        if (!email.matches("^[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]{2,3}$")) {
            mMsjUsuario.setValue("Email: Introduzca un correo electrónico válido.");
            return;
        }



        Call<String> call = ApiClient.getApiInmobiliaria().modificarPerfil("Bearer "+ApiClient.Leer(context), dni, nombre, apellido, telefono , email);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    mMsjUsuario.setValue("");
                    mMsjUsuario.setValue("Datos modificados con exito.");
                } else {
                    mMsjUsuario.setValue("Error al intentar modificar datos del perfil.");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                mMsjUsuario.setValue("Error de conexion al modificar propietario.");

            }
        });

    };


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
            mMsjUsuario.setValue("No se ha seleccionado ninguna imagen para actualizar.");
            return;
        }

        Uri avatarUri = mAvatar.getValue();

        // Crear un archivo temporal a partir del Uri
        File avatarFile = createTemporaryFileFromUri(avatarUri);
        if (avatarFile == null) {
            mMsjUsuario.setValue("Error al crear un archivo temporal de la imagen.");
            Log.e("Error:", "Archivo temporal no creado");
            return;
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), avatarFile);
        MultipartBody.Part avatarPart = MultipartBody.Part.createFormData("avatarFile", avatarFile.getName(), requestFile);

        Call<ResponseBody> call = ApiClient.getApiInmobiliaria().modificarAvatar("Bearer " + ApiClient.Leer(context), avatarPart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    mMsjUsuario.setValue("Avatar actualizado con éxito.");
                } else {
                    try {
                        String errorMessage = response.errorBody().string(); // Captura el cuerpo de la respuesta de error
                        Log.d("Error response:", errorMessage);
                        mMsjUsuario.setValue("Error al actualizar el avatar: " + response.code());
                    } catch (IOException e) {
                        e.printStackTrace();
                        mMsjUsuario.setValue("Error al procesar el error de respuesta.");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mMsjUsuario.setValue("Error de conexión al intentar actualizar el avatar.");
            }
        });
    }

    // Método para crear un archivo temporal a partir de un Uri
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



    public void setAvatar(String avatar){
        this.avatar = avatar;
    }


}

