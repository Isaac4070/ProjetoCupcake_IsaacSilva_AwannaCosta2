package Modelo;

import java.io.Serializable;
import java.util.Objects;

public class Cupcake implements Serializable {
    private String sabor;
    private double preco;
    private TipoCupcake tipo;

    public Cupcake(String sabor, double preco, TipoCupcake tipo){
        this.sabor = sabor;
        this.preco = preco;
        this.tipo = tipo;
    }
    public boolean ehDoTipo(TipoCupcake tipo){
        return this.tipo == tipo;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cupcake cupcake = (Cupcake) o;
        return Double.compare(cupcake.preco, preco) == 0 && Objects.equals(sabor, cupcake.sabor) && Objects.equals(tipo, cupcake.tipo);
    }

    public int hashCode(){
        return Objects.hash(sabor, preco, tipo);
    }

    public String toString(){
        return "Cupecake(" + "sabor=" + sabor + ", preco=" + preco + ", tipo" + tipo + ')';
    }

    public String getSabor(){
        return sabor;
    }

    public void setSabor(String sabor){
        this.sabor = sabor;
    }

    public double getPreco(){
        return preco;
    }

    public void setPreco(double preco){
        this.preco = preco;
    }

    public TipoCupcake getTipo(){
        return tipo;
    }

    public void setTipo(TipoCupcake tipo){
        this.tipo = tipo;
    }
}
