package models;

public class Pallet
{
    private Integer id;
    private Box box;
    private Integer count;

    public Pallet()
    {
    }

    public Pallet(Box box, Integer count)
    {
        this.box = box;
        this.count = count;
    }

    public Pallet(Integer id, Box box, Integer count)
    {
        this.id = id;
        this.box = box;
        this.count = count;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Box getBox()
    {
        return box;
    }

    public void setBox(Box box)
    {
        this.box = box;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }
}
