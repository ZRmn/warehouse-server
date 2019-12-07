package models;

public class Box
{
    private Integer id;
    private Integer count;
    private Product product;

    public Box()
    {
        
    }

    public Box(Integer count, Product product)
    {
        this.count = count;
        this.product = product;
    }

    public Box(Integer id, Integer count, Product product)
    {
        this.id = id;
        this.count = count;
        this.product = product;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }
}
