package se2project.model;


import javax.persistence.*;
import java.text.DecimalFormat;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long productId;
    private String productName;
    private double price;
    private String imageName;
    private String description;
    private String color;
    @ManyToOne
    private SubCategory subCategory;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
public String convert(double price){
    price = this.price;
    DecimalFormat formatter = new DecimalFormat("###,###,###");

        return formatter.format(price);
}
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Transient
    public String getProductImagePath(){
        if (imageName == null ||productId == null) return null;

        return "/images/" + productId + "/" + imageName;
    }
}
