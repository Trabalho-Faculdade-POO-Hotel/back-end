import java.util.ArrayList;
import java.util.List;

public class QuartoDAO implements Quarto {
    private List<Quarto> quartos = new ArrayList<>();

    @Override
    public void adicionar(Quarto quarto) {
        quartos.add(quarto);
    }

    @Override
    public Quarto buscarPorNumero(int numero) {
        for (Quarto quarto : quartos) {
            if (quarto.getNumero() == numero) {
                return quarto;
            }
        }
        return null; // ou lançar uma exceção
    }

    @Override
    public List<Quarto> listarTodos() {
        return new ArrayList<>(quartos);
    }

    @Override
    public void atualizar(Quarto quarto) {
        Quarto q = buscarPorNumero(quarto.getNumero());
        if (q != null) {
            q.setTipoQuarto(quarto.getTipoQuarto());
            q.setLotacaoQuarto(quarto.getLotacaoQuarto());
            q.setPreco(quarto.getPreco());
            q.setStatusManutencao(quarto.isStatusManutencao());
        }
    }

    @Override
    public void remover(int numero) {
        Quarto quarto = buscarPorNumero(numero);
        if (quarto != null) {
            quartos.remove(quarto);
        }
    }
}