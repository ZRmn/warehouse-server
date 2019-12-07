package models;

public class Item
{
    private Box box;
    private Integer count;

    public Item()
    {
    }

    public Item(Box box, Integer count)
    {
        this.box = box;
        this.count = count;
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
