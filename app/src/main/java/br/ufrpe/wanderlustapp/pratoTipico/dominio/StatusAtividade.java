package br.ufrpe.wanderlustapp.pratoTipico.dominio;

public enum StatusAtividade {
    ATIVO("ativo"), INATIVO("inativo");

    private String valor;

    StatusAtividade(String valor){
        this.valor = valor;
    }

    public String getStatus(){
        return this.valor;
    }

    public StatusAtividade stringToEnum(String status){
        if(status.equals(StatusAtividade.ATIVO.toString())){
            return StatusAtividade.ATIVO;
        }else if(status.equals(StatusAtividade.INATIVO.toString())){
            return StatusAtividade.INATIVO;
        }else{
            return null;
        }
    }
}
