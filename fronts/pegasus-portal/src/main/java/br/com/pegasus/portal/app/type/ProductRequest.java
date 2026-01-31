package br.com.pegasus.portal.app.type;

public class ProductRequest {
  private Long id;
  private String name;
  private Float price;
  private Integer quantity;

  public Integer getQuantity() {
    return quantity;
  }


  public ProductRequest(String name, Float price, Integer quantity) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
