package question_01;

public class Order {
    public int orderId;
    public  int productId;
    public int quantity;
    public double unitPrice;

    public Order(int orderId, int productId, int quantity, double unitPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
