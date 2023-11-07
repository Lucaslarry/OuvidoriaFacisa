package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Ingredientes;

import entities.Pizzaria;
import entities.PizzariaExceptions;
import entities.Relatorio;

public class Program {
    public static void main(String[] args) throws Exception {
        boolean sair = false;
        int idIng = 1;
        Pizzaria pizzaria = new Pizzaria();
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        while (sair == false) {
            try {
                UI.menu();
                int opcaoPrincipal = sc.nextInt();
                sc.nextLine();

                if (opcaoPrincipal != 4 && opcaoPrincipal != 6 && pizzaria.getListaIngredientesExistentes().isEmpty()) {
                    throw new PizzariaExceptions("Não é possivel fazer isso antes de criar um ingrediente.");
                }

                switch (opcaoPrincipal) {
                    default -> System.out.println("Opção Inválida");
                    case 1 -> {
                        boolean adicionado = false;
                        List<String> tempLista = new ArrayList<>();
                        while (adicionado == false) {
                            UI.menuPizza();
                            int escolhaPizza = sc.nextInt();
                            sc.nextLine();

                            if (escolhaPizza == 1) {
                                pizzaria.getListaIngredientesExistentes().forEach(System.out::println);
                                System.out.print("Escolha seu ingrediente: ");
                                String novoIng = sc.nextLine();
                                tempLista.add(novoIng);
                            }
                            if (escolhaPizza == 2) {
                                if (tempLista.isEmpty()) {
                                    System.out.println("Nenhum ingrediente foi adicionado");
                                } else {

                                    System.out.println("Ultimo ingrediente removido com sucesso!");
                                    tempLista.remove((tempLista.size() - 1));

                                }
                            }
                            if (escolhaPizza == 3) {
                                System.out.print("Nomeie este sabor: ");
                                String sabor = sc.nextLine();
                                pizzaria.criarPizza(UI.formatarTexto(sabor), tempLista);
                                adicionado = true;
                            }
                            if (escolhaPizza == 4) {
                                System.out.println("Voltando...");
                                adicionado = true;
                            }

                        }
                    }
                    case 2 -> {
                        System.out.println("Cardápio: ");
                        pizzaria.getListaPizzaExistentes().stream().map(p -> p.getNomeDoSabor())
                                .forEach(System.out::println);
                        System.out.print("Digite o nome sabor de pizza: ");
                        String escolhaSabor = sc.nextLine();
                        System.out.print("Digite o numero da mesa: ");
                        int numeroMesa = sc.nextInt();
                        sc.nextLine();
                        pizzaria.criarPedido(UI.formatarTexto(escolhaSabor), numeroMesa);

                    }
                    case 3 -> {
                        pizzaria.servirPedido();
                    }
                    case 4 -> {
                        System.out.print("Digite o ingrediente a ser adicionado: ");
                        String novoIngrediente = sc.nextLine();
                        pizzaria.adicionarIngrediente(new Ingredientes(UI.formatarTexto(novoIngrediente), idIng));
                        idIng++;
                    }
                    case 5 -> {
                        System.out.println(
                                "Ao total foram servidas " + Relatorio.getQuantidadeDePizzaSevida() + " pizzas");

                        System.out.println(
                                "As pizzas tem em média " + Relatorio.getQuantidadeMediaDeIngredientes()
                                        + " ingredientes por pizza");

                        System.out.println("O ingrediente mais pedido foi "
                                + Relatorio.getIngredienteMaisPedido().getIngrediente()
                                + " com um total de " + Relatorio.getQuantidadeDoMaisPedido() + " pedidos");

                        String semPedidos = Relatorio.ingredientesNaoPedidos(pizzaria.getListaIngredientesExistentes());
                        if (semPedidos == null) {
                            System.out.println("Não há ingredientes que não foram pedidos");
                        } else {
                            System.out.println("Lista de ingredientes que não ainda não foram pedidos: " + semPedidos);
                        }

                    }
                    case 6 -> {
                        System.out.println("Nossa pizzaria está fechando, obrigado!");
                        sair = true;
                    }

                }
            } catch (PizzariaExceptions e) {
                System.out.println(e.getMessage());
            } catch (NullPointerException | ArithmeticException e) {
                System.out.println("Nenhum pedido ainda foi feito");
            } catch (ExceptionInInitializerError e) {
                System.out.println("Ainda não foi adicionado nenhum ingrediente ou pizza");
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Valor inválido");
                sc.nextLine();
            }

        }
        sc.close();
    }
}
