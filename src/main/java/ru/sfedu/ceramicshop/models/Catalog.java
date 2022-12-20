package ru.sfedu.ceramicshop.models;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.ceramicshop.utils.CustomItemConverterList;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


/**
 * The type Rating.
 */
@Root(name = "Catalog")
public class Catalog implements Serializable {
    @Attribute
    @CsvBindByName
    private long id;
    @ElementList
    @CsvCustomBindByName(converter = CustomItemConverterList.class)
    private List<Item> itemList;

    /**
     * Instantiates a new Catalog.
     */
    public Catalog() {
    }

    /**
     * Instantiates a new Catalog.
     *
     * @param id       the id
     * @param itemList the item list
     */
    public Catalog(long id, List<Item> itemList) {
        this.id = id;
        this.itemList = itemList;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets item list.
     *
     * @return the item list
     */
    public List<Item> getItemList() {
        return itemList;
    }

    /**
     * Sets item list.
     *
     * @param itemList the item list
     */
    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Catalog)) return false;
        Catalog catalog = (Catalog) o;
        return getId() == catalog.getId() && Objects.equals(getItemList(), catalog.getItemList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getItemList());
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", itemList=" + itemList +
                '}';
    }
}
