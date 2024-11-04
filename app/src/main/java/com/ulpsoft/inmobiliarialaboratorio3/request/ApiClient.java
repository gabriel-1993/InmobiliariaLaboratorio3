package com.ulpsoft.inmobiliarialaboratorio3.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ulpsoft.inmobiliarialaboratorio3.model.Contrato;
import com.ulpsoft.inmobiliarialaboratorio3.model.CrearInmuebleRespuesta;
import com.ulpsoft.inmobiliarialaboratorio3.model.Inmueble;
import com.ulpsoft.inmobiliarialaboratorio3.model.Propietario;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public class ApiClient {
    //Lapunta
    //public static final String URLBASE = "http://192.168.1.3:5290/api/";

    //Sanluis
    //public static final String URLBASE = "http://192.168.0.8:5290/api/";
    //public static final String URLBASE = "http://192.168.0.3:5290/api/";

    //ger
    public static final String URLBASE = "http://192.168.0.5:5290/api/";

    private static SharedPreferences sp;
    private static String token;

    public static InmobiliariaService getApiInmobiliaria(){

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(InmobiliariaService.class);
    };



    private static SharedPreferences getSharedPreference(Context context){
        if(sp == null){
            sp = context.getSharedPreferences("usuario",0);
        }
        return sp;
    }

    public static void Guardar(Context context, String token){
        SharedPreferences sp = getSharedPreference(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    };


    public static String Leer(Context context) {
        SharedPreferences sp = getSharedPreference(context);
        return sp.getString("token", null); // El segundo parámetro es el valor por defecto si no existe
    }





    public interface InmobiliariaService {


//PROPIETARIO---------------------------------------------------------------------------------

        @FormUrlEncoded
        @POST("propietarios/login")
        Call<String> login(@Field("Email") String mail, @Field("Clave") String clave);


        @GET("propietarios/perfil")
        Call<Propietario> GetPerfilPropietario(@Header("Authorization") String token);


        @FormUrlEncoded
        @PUT("propietarios/modificarPerfil")
        Call<String> modificarPerfil(
                @Header("Authorization") String token,
                @Field("Dni") String dni,
                @Field("Nombre") String nombre,
                @Field("Apellido") String apellido,
                @Field("Telefono") String telefono,
                @Field("Email") String email
        );


        @FormUrlEncoded
        @PUT("propietarios/cambiarContraseña")
        Call<String> cambiarContraseña(
                @Header("Authorization") String token,
                @Field("ContraseñaActual") String contraseñaActual,
                @Field("NuevaContraseña") String nuevaContraseña,
                @Field("RepetirContraseña") String repetirContraseña
        );

        @Multipart
        @PUT("propietarios/modificarAvatar")
        Call<ResponseBody> modificarAvatar(
                @Header("Authorization") String token,
                @Part MultipartBody.Part avatarFile
        );

        @FormUrlEncoded
        @POST("propietarios/restablecerClave")
        Call<String> restablecerClave(
                @Field("email") String email
        );



 //INMUEBLE -----------------------------------------------------------------------------------

        @GET("inmuebles/misInmuebles")
        Call<List<Inmueble>> getMisInmuebles(@Header("Authorization") String token);

        @FormUrlEncoded
        @PUT("inmuebles/publicarOnOff")
        Call<String> publicarOnOff(
                @Field("IdInmueble") int idInmueble,
                @Header("Authorization") String token
        );

        @FormUrlEncoded // Indica que el cuerpo de la solicitud será un formulario
        @POST("inmuebles/agregarInmueble")
        Call<CrearInmuebleRespuesta> agregarInmueble(
                @Header("Authorization") String token,
                @Field("direccion") String direccion,
                @Field("ambientes") String ambientes,
                @Field("tipo") String tipo,
                @Field("uso") String uso,
                @Field("precio") String precio
        );



        @Multipart
        @PUT("inmuebles/modificarAvatar")
        Call<ResponseBody> modificarAvatarInmueble(
                @Header("Authorization") String token,
                @Part("idInmueble") RequestBody idInmueble,
                @Part MultipartBody.Part avatarFile
        );


//INQUILINOS-----------------------------------------------------------------------------------------
//inquilinos con contrato en curso inmueble(contrato(inquilino)))
        @GET("inquilinos/contratosEnCursoInquilinos")
        Call<List<Inmueble>> getContratosEnCursoInquilinos(@Header("Authorization") String token);



//CONTRATOS-----------------------------------------------------------------------------------------
//contratos en curso con sus pagos inmueble
        @GET("contratos/contratoEnCursoInmuebleInquilinoPagos")
        Call<List<Inmueble>> contratoEnCursoInmuebleInquilinoPagos(
                @Header("Authorization") String token
        );




    }


}
