package com.example.var.encuestas.Config;

/**
 * Created by rexv666480 on 07/02/2018.
 */

public class AgenteWS {

    private  ServiciosWeb serviciosWeb;
    private  AppConfig appConfig;

    public AgenteWS() {

        this.serviciosWeb = new AppConfig().getRetrofit().create(ServiciosWeb.class);
    }

    public ServiciosWeb getServiciosWeb() {
        return serviciosWeb;
    }

    public void setServiciosWeb(ServiciosWeb serviciosWeb) {
        this.serviciosWeb = serviciosWeb;
    }


}
