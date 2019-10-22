package br.ufrpe.wanderlustapp.usuario.dominio;

import androidx.annotation.Nullable;

public enum TipoUsuario {
    ATIVO("ativo"), INATIVO("inativo"), ADM("adm");
    private String TIPO_USUARIO;

    TipoUsuario(String tipoUsuario) {
        this.TIPO_USUARIO = tipoUsuario;
    }

    public String getTipoUsuario(){
        return TIPO_USUARIO;
    }

    public void setTipoUsuario(String tipoUsuario){
        this.TIPO_USUARIO = tipoUsuario;
        stringToEnum(tipoUsuario);
    }


    @Nullable
    public static final TipoUsuario stringToEnum(String tipo){
        if(tipo.equals(TipoUsuario.ATIVO.toString())){
            return TipoUsuario.ATIVO;
        }else if(tipo.equals(TipoUsuario.INATIVO.toString())){
            return TipoUsuario.INATIVO;
        }
        else if(tipo.equals(TipoUsuario.ADM.toString())){
            return TipoUsuario.ADM;
        }
        return null;
    }

}
