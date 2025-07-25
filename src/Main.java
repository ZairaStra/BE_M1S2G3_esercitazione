import entities.Customer;
import entities.Order;
import entities.Product;

import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //creo utenti,prodotti e ordini finti

        Customer firstCustomer = new Customer(32423, "Matilde", 3);
        Customer secondCustomer = new Customer(32453, "Umberto", 1);
        Customer thirdCustomer = new Customer(32579, "Ugo", 2);
        Customer fourthCustomer = new Customer(32784, "Fiammetta", 1);
        Customer fifthCustomer = new Customer(32874, "Agostina", 2);

        List<Product> products1 = List.of(
                new Product(14165, "Sandman", "Books", 145.0),
                new Product(14167, "Seggiolino auto", "Baby", 99.7),
                new Product(14146, "Papillon per bambino", "Boys", 22.0),
                new Product(141794, "Mina Lima's Harry Potter", "Books", 118.0),
                new Product(1416357, "Pallone da calcio Inter", "Toys", 24.9),
                new Product(14165782, "Piccoli brividi", "Books", 17.5),
                new Product(414165, "Skifidol", "Boys", 3.5),
                new Product(1416714165, "Biberon", "Baby", 16.4)
        );

        Order firstOrder = new Order(32645269, "delivered", LocalDate.of(2021, 2, 2), LocalDate.of(2021, 2, 7), List.of(products1.get(1), products1.getLast()), thirdCustomer);
        Order secondOrder = new Order(32645748, "ready for shipping", LocalDate.of(2021, 3, 25), LocalDate.of(2021, 3, 27), List.of(products1.get(0), products1.get(5), products1.getFirst()), firstCustomer);
        Order thirdOrder = new Order(3264718, "shipped", LocalDate.of(2021, 3, 28), LocalDate.of(2021, 4, 1), List.of(products1.get(4), products1.get(2), products1.getFirst(), products1.get(7)), fifthCustomer);
        Order fourthOrder = new Order(32642413, "shipped", LocalDate.of(2021, 3, 29), LocalDate.of(2021, 4, 4), List.of(products1.get(5), products1.get(6), products1.getFirst(), products1.get(2)), secondCustomer);

        //insereisco gli ordini in una lista da usare per verificare la categoria
        List<Order> orders = List.of(firstOrder, secondOrder, thirdOrder, fourthOrder);

        //es1 - lista da stream
        List<Product> expBooks = products1.stream()
                //scrivo entrambi i filter in un'unica riga collegandoli con && pcosì mi risparmio un filtro
                .filter(product -> product.getCategory().equals("Books") && product.getPrice() > 100)
                .toList();
        System.out.println("I libri più costosi sono: ");
        expBooks.forEach(product -> System.out.println(product.getName() + ": €" + product.getPrice()));

        //es2 - lista ordini baby
        List<Order> babyOrders = orders.stream()
                .filter(order -> order.getProducts().stream()
                        .anyMatch(product -> product.getCategory().equals("Baby")))
                .toList();

        System.out.println("Gli ordini con prodotti per l'infanzia sono: ");
        babyOrders.forEach(order -> System.out.println(order.getId()));

        //es3 - lista prodotti boys scontati EXNOVO - la devo ricreare modificando solo i dati che voglio cambire
        List<Product> saleBoysOrders = products1.stream()
                .filter(product -> product.getCategory().equals("Boys"))
                .map(product -> new Product(product.getId(), product.getName(), product.getCategory(), product.getPrice() * 0.9))
                .toList();

        System.out.println("I prodotti per bambino scontati sono: ");
        saleBoysOrders.forEach(product ->
                System.out.println(product.getName() + ": " + product.getPrice() + " €")
        );

        //es4 - lista prodotti ordinati da tier2
        List<Product> productsOrderedCustomer2 = orders.stream()
                .filter(order -> order.getCustomer().getTier() == 2 &&
                        !order.getOrderDate().isBefore(LocalDate.of(2021, 2, 1)) &&
                        !order.getOrderDate().isAfter(LocalDate.of(2021, 4, 1)))
                //debrief:serve il flatMap perchè funzioni - rivediti cos'è
                //.map(order -> order.getProducts().stream())
                .flatMap(order -> order.getProducts().stream())
                .toList();

        System.out.println("I prodotti filtrati per livello utente 2 e data di ordine compresa tra 1/2/2021 e 1/4/2021 sono: ");
        productsOrderedCustomer2.forEach(product ->
                System.out.println(product.getName())
        );
    }
}