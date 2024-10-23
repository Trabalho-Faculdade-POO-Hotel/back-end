import java.util.ArrayList;
import java.util.List;

public class QuartoDAOImpl implements QuartoDAO {
    private List<Quarto> quartos = new ArrayList<>();


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
    public void atualizarPreco(int numero, double novoPreco) {
        Quarto quarto = buscarPorNumero(numero);
        if (quarto != null) {
            quarto.setPreco(novoPreco);
        }
    }

    @Override
    public Quarto alterarTipoQuarto(int numero, Quarto.TipoQuarto novoTipo) {
        Quarto quarto = buscarPorNumero(numero);
        if (quarto != null) {
            quarto.setTipoQuarto(novoTipo);
            return quarto;
        }
        return null; // ou lançar uma exceção
    }

    @Override
    public void alterarStatusManutencao(int numero, boolean novoStatus) {
        Quarto quarto = buscarPorNumero(numero);
        if (quarto != null) {
            quarto.setStatusManutencao(novoStatus);
        }
    }
}