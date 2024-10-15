import java.util.Date;

public class Reserva {
    private Cliente cliente;
    private Quarto quarto;
    private Date dtInicial;
    private Date dtFinal;
    private Status status;

    public enum Status {
        ATIVO,
        PENDENTE,
        CANCELADO,
        NO_SHOW,
        CHECK_IN,
        CHECK_OU
    }
    public Reserva (Cliente cliente, Quarto quarto, Date dtInicial, Date dtFinal, Status status){
        this.cliente = cliente;
        this.quarto = quarto;
        this.dtInicial = dtInicial;
        this.dtFinal = dtFinal;
        this.status = status;
    }
    //Getter e Setters

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Quarto getQuarto() {
        return quarto;
    }
    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public Date getDtInicial() {
        return dtInicial;
    }
    public void setDtInicial(Date dtInicial) {
        this.dtInicial = dtInicial;
    }

    public Date getDtFinal() {
        return dtFinal;
    }
    public void setDtFinal(Date dtFinal) {
        this.dtFinal = dtFinal;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public void alterarStatus(Status novoStatus) {
        this.setStatus(novoStatus);
    }
    public void alterarQuarto(Quarto novoQuarto) {
        this.setQuarto(novoQuarto);
    }
}
