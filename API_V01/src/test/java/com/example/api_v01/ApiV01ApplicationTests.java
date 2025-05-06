package com.example.api_v01;

import com.example.api_v01.model.Product;
import com.example.api_v01.service.customer_order_service.CustomerOrderService;
import com.example.api_v01.service.order_set_service.OrderSetService;
import com.example.api_v01.service.product_service.ProductService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
class ApiV01ApplicationTests {

    @Autowired
    private  ProductService productService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private OrderSetService orderSetService;

    @Test
    void contextLoads() {
        System.out.println("Hello World");
    }

    @Test
    void addProduct() {

        /*
        ProductDTO product1 = ProductDTO.builder()
                .name_product("Tallarin Verde con Milanesa")
                .price(16.0)
                .additional_observation("Con queso parmesano")
                .category(Category.PASTAS)
                .ini_stock(40)
                .current_stock(25)
                .total_sold(15)
                .admin(null)
                .build();

        ProductDTO product2 = ProductDTO.builder()
                .name_product("Ceviche Mixto")
                .price(20.0)
                .additional_observation("Sin picante")
                .category(Category.MARINO)
                .ini_stock(30)
                .current_stock(10)
                .total_sold(20)
                .admin(null)
                .build();

        ProductDTO product3 = ProductDTO.builder()
                .name_product("Ají de Gallina")
                .price(14.0)
                .additional_observation("Con huevo y aceituna")
                .category(Category.CRIOLLOS)
                .ini_stock(50)
                .current_stock(40)
                .total_sold(10)
                .admin(null)
                .build();

        ProductDTO product4 = ProductDTO.builder()
                .name_product("Pollo a la Brasa")
                .price(18.5)
                .additional_observation("Incluye papas fritas y ensalada")
                .category(Category.COMIDA_RAPIDA)
                .ini_stock(60)
                .current_stock(30)
                .total_sold(30)
                .admin(null)
                .build();

        ProductDTO product5 = ProductDTO.builder()
                .name_product("Chaufa de Pollo")
                .price(13.5)
                .additional_observation("Con wantán incluido")
                .category(Category.COMIDA_RAPIDA)
                .ini_stock(35)
                .current_stock(20)
                .total_sold(15)
                .admin(null)
                .build();

        Product p1 = ProductMovement.createProductAndStock(product1);
        Product p2 = ProductMovement.createProductAndStock(product2);
        Product p3 = ProductMovement.createProductAndStock(product3);
        Product p4 = ProductMovement.createProductAndStock(product4);
        Product p5 = ProductMovement.createProductAndStock(product5);

        productService.saveProduct(p1);
        productService.saveProduct(p2);
        productService.saveProduct(p3);
        productService.saveProduct(p4);
        productService.saveProduct(p5);
        */
    }

    @Test
    void ListProduct(){
        List<Product>ListProducts = productService.getProducts();
        ListProducts.forEach(System.out::println);
    }

    @Test
    void TestOrderSet(){

        /*
        OrderSet orderSet = OrderSet.builder()
                .name_client("Juan Perez")
                .date_order(LocalDate.now())
                .time_order(LocalTime.now())
                .total_order(null)
                .arching(null)
                .dispatch(true)
                .build();

        orderSet = orderSetService.saveOrderSet(orderSet);

        Product product1 = productService.getProduct(UUID.fromString("53dfc69c-5fcc-4bf6-a06c-932b94dd3201"));
        Product product2 = productService.getProduct(UUID.fromString("93a54048-2f7e-441f-a7a4-3942766e991a"));
        Product product3 = productService.getProduct(UUID.fromString("a264cabf-30e1-4f14-8d58-6c35f725d4cb"));
        Product product4 = productService.getProduct(UUID.fromString("aada1533-86ad-4a04-aa3a-ae7529a6f88f"));
        Product product5 = productService.getProduct(UUID.fromString("b19b992c-499f-4182-a016-5b0b74204e4a"));

        //apartir de aqui deberian descontarse el stock

        Product p1 = InventoryMovement.discountStock(product1,10);
        Product p2 = InventoryMovement.discountStock(product2,4);
        Product p3 = InventoryMovement.discountStock(product3,7);
        Product p4 = InventoryMovement.discountStock(product4,7);
        Product p5 = InventoryMovement.discountStock(product5,9);

        p1 = productService.saveProduct(p1);
        p2 = productService.saveProduct(p2);
        p3 = productService.saveProduct(p3);
        p4 = productService.saveProduct(p4);
        p5 = productService.saveProduct(p5);

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
        System.out.println(p5);


        CustomerOrder order1 = OrderMovement.createCustomerOrder(10,p1,orderSet);
        CustomerOrder order2 = OrderMovement.createCustomerOrder(4,p2,orderSet);
        CustomerOrder order3 = OrderMovement.createCustomerOrder(7,p3,orderSet);
        CustomerOrder order4 = OrderMovement.createCustomerOrder(7,p4,orderSet);
        CustomerOrder order5 = OrderMovement.createCustomerOrder(9,p5,orderSet);

        customerOrderService.saveCustomerOrder(order1);
        customerOrderService.saveCustomerOrder(order2);
        customerOrderService.saveCustomerOrder(order3);
        customerOrderService.saveCustomerOrder(order4);
        customerOrderService.saveCustomerOrder(order5);

        List<CustomerOrder>OrderList=new ArrayList<>();
        OrderList.add(order1);
        OrderList.add(order2);
        OrderList.add(order3);
        OrderList.add(order4);
        OrderList.add(order5);

        orderSet.setTotal_order(OrderMovement.ContTotal(OrderList));

        orderSetService.saveOrderSet(orderSet);

        */

    }


}
