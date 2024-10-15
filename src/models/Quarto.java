
package javaapplication2;


public class Quarto {
    private int numero;
    private TipoQuarto tipo_quarto;
    private int lotacao_quarto;
    private double preço;
    private boolean status_manutencao;
      
    public Quarto(int numero, TipoQuarto tipo_quarto, int lotacao_quarto, double preco) {
        this.numero = numero;
        this.tipo_quarto = tipo_quarto;
        this.lotacao_quarto = lotacao_quarto;
        this.preço = preco;
        this.status_manutencao = false; 
    }
    
    public enum TipoQuarto{
        SUITE,
        QUARTO_INDIVIDUAL,
        QUARTO_FAMILIAR,
        QUARTO_EXECUTIVO,           
    }

    public int getNumero() {
        return this.numero;
    }

    public TipoQuarto getTipoQuarto() {
        return this.tipo_quarto;
    }

    public int getLotacaoQuarto() {
        return this.lotacao_quarto;
    }

    public double getPreco() {
        return this.preço;
    }

    public boolean getStatusManutencao() {
        return this.status_manutencao;
    }
    
    
    public void atualizarPreco(double novoPreco) {
        this.preço = novoPreco;
    }

    public void alterarTipoQuarto(TipoQuarto novoTipo) {
        this.tipo_quarto = novoTipo;
        
    }

    public void alterarStatusManutencao(boolean novoStatus) {
        this.status_manutencao = novoStatus;
    }

}