package domain;

public class Product {

    private int id;
    private String title;
    private int count;
    private ProductType type;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public ProductType getType() {
        return type;
    }
    public void setType(ProductType type) {
        this.type = type;
    }
}
