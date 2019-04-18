package bean;

public enum TypeOfGoods {
    LIQUIDS(60), COUNTABLE(15);

    private int price;

    TypeOfGoods(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
