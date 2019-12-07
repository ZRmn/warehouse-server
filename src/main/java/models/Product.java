package models;

import java.util.Objects;

public class Product
{
    private Integer id;
    private Long code;
    private String description;

    public Product()
    {

    }

    public Product(String description)
    {
        this.description = description;
        code = hash();
    }

    public Product(Integer id, Long code, String description)
    {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Long getCode()
    {
        return code;
    }

    public void setCode(Long code)
    {
        this.code = code;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(code, product.code) && Objects.equals(description, product.description);
    }

    private long hash()
    {
        int p = 31;
        long hash = 0;
        long pPow = 1;

        for (char ch : description.toCharArray())
        {
            hash += (ch - 'a' + 1) * pPow;
            pPow *= p;
        }

        return Math.abs(hash);
    }
}
