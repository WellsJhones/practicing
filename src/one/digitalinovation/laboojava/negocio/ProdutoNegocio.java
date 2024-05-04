package one.digitalinovation.laboojava.negocio;

import one.digitalinovation.laboojava.basedados.Banco;
import one.digitalinovation.laboojava.entidade.Produto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Classe para manipular a entidade {@link Produto}.
 * 
 * @author thiago leite
 */
public class ProdutoNegocio {

    /**
     * {@inheritDoc}.
     */
    private Banco bancoDados;

    /**
     * Construtor.
     * 
     * @param banco Banco de dados para ter armazenar e ter acesso os produtos
     */
    public ProdutoNegocio(Banco banco) {
        this.bancoDados = banco;
    }

    /**
     * Salva um novo produto(livro ou caderno) na loja.
     * 
     * @param novoProduto Livro ou caderno que pode ser vendido
     */
    public void salvar(Produto novoProduto) {

        String codigo = "PR%04d";
        codigo = String.format(codigo, bancoDados.getProdutos().length);
        novoProduto.setCodigo(codigo);

        boolean produtoRepetido = false;
        for (Produto produto : bancoDados.getProdutos()) {
            if (produto.getCodigo().equals(novoProduto.getCodigo())) {
                produtoRepetido = true;
                System.out.println("Produto já cadastrado.");
                break;
            }
        }

        if (!produtoRepetido) {
            this.bancoDados.adicionarProduto(novoProduto);
            System.out.println("Produto cadastrado com sucesso.");
        }
    }

    /**
     * Exclui um produto pelo código de cadastro.
     * 
     * @param codigo Código de cadastro do produto
     */

    public void excluir(String codigo) {
        List<Produto> produtos = new ArrayList<>(Arrays.asList(bancoDados.getProdutos()));
        boolean produtoExcluido = false;
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                produtos.remove(i);
                produtoExcluido = true;
                break;
            }
        }
        if (produtoExcluido) {
            bancoDados.setProdutos(produtos.toArray(new Produto[0]));
            System.out.println("Produto excluído com sucesso.");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    /**
     * Obtem um produto a partir de seu código de cadastro.
     * 
     * @param codigo Código de cadastro do produto
     * @return Optional indicando a existência ou não do Produto
     */
    public Optional<Produto> consultar(String codigo) {

        for (Produto produto : bancoDados.getProdutos()) {

            if (produto.getCodigo().equalsIgnoreCase(codigo)) {
                return Optional.of(produto);
            }
        }

        return Optional.empty();
    }

    /**
     * Lista todos os produtos cadastrados.
     */
    public void listarTodos() {

        if (bancoDados.getProdutos().length == 0) {
            System.out.println("Não existem produtos cadastrados");
        } else {

            for (Produto produto : bancoDados.getProdutos()) {
                System.out.println(produto.toString());
            }
        }
    }
}
