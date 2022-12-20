package ru.sfedu.ceramicshop.models;


import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Item.
 */
@Root(name = "Item")
public class Item implements Serializable {
    @Attribute
    @CsvBindByName
    private long id;
    @Element
    @CsvBindByName
    private Integer price;
    @Element
    @CsvBindByName
    private String manufacturer;
    @Element
    @CsvBindByName
    private Integer number;
    @Element
    @CsvBindByName
    private String vendorCode;

    /**
     * Instantiates a new Item.
     */
    public Item() {
    }

    /**
     * Instantiates a new Item.
     *
     * @param id           the id
     * @param price        the price
     * @param manufacturer the manufacturer
     * @param number       the number
     * @param vendorCode   the vendor code
     */
    public Item(long id, Integer price, String manufacturer, Integer number, String vendorCode) {
        this.id = id;
        this.price = price;
        this.manufacturer = manufacturer;
        this.number = number;
        this.vendorCode = vendorCode;
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
     * Gets price.
     *
     * @return the price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Gets manufacturer.
     *
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets manufacturer.
     *
     * @param manufacturer the manufacturer
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Gets number.
     *
     * @return the number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * Sets number.
     *
     * @param number the number
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * Gets vendor code.
     *
     * @return the vendor code
     */
    public String getVendorCode() {
        return vendorCode;
    }

    /**
     * Sets vendor code.
     *
     * @param vendorCode the vendor code
     */
    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getId() == item.getId() && Objects.equals(getPrice(), item.getPrice()) && Objects.equals(getManufacturer(), item.getManufacturer()) && Objects.equals(getNumber(), item.getNumber()) && Objects.equals(getVendorCode(), item.getVendorCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrice(), getManufacturer(), getNumber(), getVendorCode());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", price=" + price +
                ", manufacturer='" + manufacturer + '\'' +
                ", number=" + number +
                ", vendorCode='" + vendorCode + '\'' +
                '}';
    }
}
