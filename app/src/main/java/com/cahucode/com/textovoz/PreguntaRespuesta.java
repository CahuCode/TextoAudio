package com.cahucode.com.textovoz;

/**
 * Creado por Carlos_Code el 02/03/2022.
 * carlos.japon.code@gmail.com
 */

class PreguntaRespuesta {
    private String pregunta;
    private String respuesta;

    public PreguntaRespuesta(String pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "PreguntaRespuesta{" +
                "pregunta='" + pregunta + '\'' +
                ", respuesta='" + respuesta + '\'' +
                '}';
    }
}
