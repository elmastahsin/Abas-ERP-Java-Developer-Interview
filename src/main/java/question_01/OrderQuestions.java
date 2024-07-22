package question_01;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderQuestions {

    public static void main(String[] args) {
        List<Order> orders = Arrays.asList(
                new Order(1000, 2000, 12, 100.51),
                new Order(1000, 2001, 31, 200.00),
                new Order(1000, 2002, 22, 150.86),
                new Order(1000, 2003, 41, 250.00),
                new Order(1000, 2004, 55, 244.00),
                new Order(1001, 2001, 88, 44.531),
                new Order(1001, 2002, 121, 88.11),
                new Order(1001, 2004, 74, 211.00),
                new Order(1001, 2002, 14, 88.11),
                new Order(1002, 2003, 2, 12.10),
                new Order(1002, 2004, 3, 22.30),
                new Order(1002, 2003, 8, 12.10),
                new Order(1002, 2002, 16, 94.00),
                new Order(1002, 2005, 9, 44.10),
                new Order(1002, 2006, 19, 90.00)
        );
        System.out.println("Stream implementation for the following:");
        System.out.println("Sum Each Order:");
        System.out.println("=============================================");
        orderTotal(orders);
        System.out.println("=============================================");
        System.out.println();


        System.out.println("Average Price Each Order:");
        System.out.println("=============================================");
        orderAveragePrice(orders);
        System.out.println("=============================================");
        System.out.println();


        System.out.println("Average Price Each Product:");
        System.out.println("=============================================");
        productAveragePrice(orders);
        System.out.println("=============================================");
        System.out.println();


        System.out.println("Product Count In Each Order:");
        System.out.println("=============================================");
        productCountInOrders(orders);
        System.out.println("=============================================");
        System.out.println();

        System.out.println("Non-stream implementation for the following:");
        System.out.println("Sum Each Order:");
        System.out.println("=============================================");
        orderTotalNonStream(orders);
        System.out.println("=============================================");

        System.out.println("Average Price Each Order:");
        System.out.println("=============================================");
        orderAveragePriceNonStream(orders);
        System.out.println("=============================================");
        System.out.println();

        System.out.println("Average Price Each Product:");
        System.out.println("=============================================");
        productAveragePriceNonStream(orders);
        System.out.println("=============================================");
        System.out.println();

        System.out.println("Product Count In Each Order:");
        System.out.println("=============================================");
        productCountInOrdersNonStream(orders);
        System.out.println("=============================================");
        System.out.println();

    }

    private static void productCountInOrdersNonStream(List<Order> orders) {
        Map<Integer, Map<Integer, Integer>> productCountInOrders = new HashMap<>();
        for (Order order : orders) {
            if (productCountInOrders.containsKey(order.productId)) {
                if (productCountInOrders.get(order.productId).containsKey(order.orderId)) {
                    productCountInOrders.get(order.productId).put(order.orderId, productCountInOrders.get(order.productId).get(order.orderId) + 1);
                } else {
                    productCountInOrders.get(order.productId).put(order.orderId, 1);
                }
            } else {
                Map<Integer, Integer> orderCount = new HashMap<>();
                orderCount.put(order.orderId, 1);
                productCountInOrders.put(order.productId, orderCount);
            }
        }
        for (Map.Entry<Integer, Map<Integer, Integer>> entry : productCountInOrders.entrySet()) {
            System.out.println("Product ID: " + entry.getKey());
            for (Map.Entry<Integer, Integer> orderCount : entry.getValue().entrySet()) {
                System.out.println("  Order ID: " + orderCount.getKey() + ", Count: " + orderCount.getValue());
            }
        }
    }

    private static void productAveragePriceNonStream(List<Order> orders) {
        Map<Integer,Double> productTotal = new HashMap<>();
        Map<Integer,Integer> productCount = new HashMap<>();
        for (Order order : orders) {
            if (productTotal.containsKey(order.productId)) {
                productTotal.put(order.productId, productTotal.get(order.productId) + order.unitPrice);
                productCount.put(order.productId, productCount.get(order.productId) + 1);
            } else {
                productTotal.put(order.productId, order.unitPrice);
                productCount.put(order.productId, 1);
            }
        }
        for (Map.Entry<Integer, Double> entry : productTotal.entrySet()) {
            System.out.println("Product ID: " + entry.getKey() + ", Average Price: " + entry.getValue()/productCount.get(entry.getKey()));
        }
    }

    private static void orderAveragePriceNonStream(List<Order> orders) {
        Map<Integer,Double> orderTotal = new HashMap<>();
        Map<Integer,Integer> orderCount = new HashMap<>();
        for (Order order : orders) {
            if (orderTotal.containsKey(order.orderId)) {
                orderTotal.put(order.orderId, orderTotal.get(order.orderId) + order.unitPrice);
                orderCount.put(order.orderId, orderCount.get(order.orderId) + 1);
            } else {
                orderTotal.put(order.orderId, order.unitPrice);
                orderCount.put(order.orderId, 1);
            }
        }
        for (Map.Entry<Integer, Double> entry : orderTotal.entrySet()) {
            System.out.println("Order ID: " + entry.getKey() + ", Average Price: " + entry.getValue()/orderCount.get(entry.getKey()));
        }
    }

    private static void orderTotalNonStream(List<Order> orders) {
        Map<Integer,Double> orderTotal = new HashMap<>();
        for (Order order : orders) {
            if (orderTotal.containsKey(order.orderId)) {
                orderTotal.put(order.orderId, orderTotal.get(order.orderId) + order.quantity * order.unitPrice);
            } else {
                orderTotal.put(order.orderId, order.quantity * order.unitPrice);
            }
        }
        for (Map.Entry<Integer, Double> entry : orderTotal.entrySet()) {
            System.out.println("Order ID: " + entry.getKey() + ", Total: " + entry.getValue());
        }
    }


    public static Map<Integer, Double> orderTotal(List<Order> orders) {
        Map<Integer, Double> orderTotal = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.orderId,
                        Collectors.summingDouble(order -> order.quantity * order.unitPrice)
                ));
        orderTotal.forEach((orderId, total) ->
                System.out.println("Order ID: " + orderId + ", Total: " + total)
        );
        return orderTotal;
    }

    public static Map<Integer, Double> orderAveragePrice(List<Order> orders) {
        Map<Integer, Double> orderAveragePrice = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.orderId,
                        Collectors.averagingDouble(order -> order.unitPrice)
                ));
        orderAveragePrice.forEach((orderId, avgPrice) ->
                System.out.println("Order ID: " + orderId + ", Average Price: " + avgPrice)
        );
        return orderAveragePrice;
    }

    public static Map<Integer, Double> productAveragePrice(List<Order> orders) {
        Map<Integer, Double> productAveragePrice = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.productId,
                        Collectors.averagingDouble(order -> order.unitPrice)
                ));
        productAveragePrice.forEach((productId, avgPrice) ->
                System.out.println("Product ID: " + productId + ", Average Price: " + avgPrice)
        );
        return productAveragePrice;
    }

    public static void productCountInOrders(List<Order> orders) {
        Map<Integer, Map<Integer, Long>> productCountInOrders = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.productId,
                        Collectors.groupingBy(
                                order -> order.orderId,
                                Collectors.counting()
                        )
                ));
        productCountInOrders.forEach((productId, orderCounts) -> {
            System.out.println("Product ID: " + productId);
            orderCounts.forEach((orderId, count) ->
                    System.out.println("  Order ID: " + orderId + ", Count: " + count)
            );
        });
    }
}
