package com.example.var.encuestas.Config;

import com.example.var.encuestas.Model.EncuestaResponse;
import com.example.var.encuestas.Model.RegionResponse;
import com.example.var.encuestas.Model.SucursalResponse;
import com.example.var.encuestas.Params.EnviarRepuestasParams;
import com.example.var.encuestas.Params.EstatusResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by rexv666480 on 28/09/2016.
 */
public interface ServiciosWeb {


    @POST("ObtenerEncuestas")
    Call<EncuestaResponse> obtenerEncuesta(@Body JsonObject x);

    @POST("ObtenerSucursales")
    Call<SucursalResponse> obtenerSucursales();

    @POST("ObtenerRegiones")
    Call<RegionResponse> obtenerRegiones();

   @POST("InsertarEncuesta")
   Call<EstatusResponse> enviarEncuestas(@Body EnviarRepuestasParams encuesta);


}

