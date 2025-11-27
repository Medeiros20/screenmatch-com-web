package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=f5547d42";

    public void exibirMenu(){
        System.out.println("Digite o nome da série para a buscar: ");
        var nomeSerie =leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ","+") + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totaltemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ","+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

//        for (int i = 0; i < dados.totaltemporadas(); i++) {
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 10 Episodios: ");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacaoEpisodio().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro  filtro (N/A) " + e))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacaoEpisodio).reversed())
                .peek(e -> System.out.println("Ordenação " + e))
                .limit(10)
                .peek(e -> System.out.println("Limite " + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Mapeamento " + e))
                .forEach(System.out::println);

//        List<Episodio> episodios = temporadas.stream()
//                .flatMap(t -> t.episodios()
//                        .stream()
//                        .map(d -> new Episodio(t.numeroTemporada(), d)))
//                .collect(Collectors.toList());
//
//        episodios.forEach(System.out::println);
//
//        System.out.println("A partir de que ano voce deseja ver os episodios? ");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataDeLancamento() != null && e.getDataDeLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Tempoprada: " + e.getTemporada() +
//                                " Episodio: " + e.getTitulo() +
//                                " Data de Lançamento: " + e.getDataDeLancamento().format(formatador)
//                ));
    }
}
