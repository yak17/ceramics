package ru.sfedu.ceramicshop.models;


import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Objects;


/**
 * The type Stoneware.
 */
@Root(name = "Stoneware")
public class Stoneware extends Item {
    @Element
    @CsvBindByName
    private String collection;
    @Element
    @CsvBindByName
    private Integer size;
    @Element
    @CsvBindByName
    private Integer thickness;
    @Element
    @CsvBindByName
    private String color;


    /**
     * Instantiates a new Stoneware.
     */
    public Stoneware() {
    }

    /**
     * Instantiates a new Stoneware.
     *
     * @param collection the collection
     * @param size       the size
     * @param thickness  the thickness
     * @param color      the color
     */
    public Stoneware(String collection, Integer size, Integer thickness, String color) {
        this.collection = collection;
        this.size = size;
        this.thickness = thickness;
        this.color = color;
    }

    /**
     * Instantiates a new Stoneware.
     *
     * @param id           the id
     * @param price        the price
     * @param manufacturer the manufacturer
     * @param number       the number
     * @param vendorCode   the vendor code
     * @param collection   the collection
     * @param size         the size
     * @param thickness    the thickness
     * @param color        the color
     */
    public Stoneware(long id, Integer price, String manufacturer, Integer number, String vendorCode, String collection, Integer size, Integer thickness, String color) {
        super(id, price, manufacturer, number, vendorCode);
        this.collection = collection;
        this.size = size;
        this.thickness = thickness;
        this.color = color;
    }

    /**
     * Gets collection.
     *
     * @return the collection
     */
    public String getCollection() {
        return collection;
    }

    /**
     * Sets collection.
     *
     * @param collection the collection
     */
    public void setCollection(String collection) {
        this.collection = collection;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * Gets thickness.
     *
     * @return the thickness
     */
    public Integer getThickness() {
        return thickness;
    }

    /**
     * Sets thickness.
     *
     * @param thickness the thickness
     */
    public void setThickness(Integer thickness) {
        this.thickness = thickness;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stoneware)) return false;
        if (!super.equals(o)) return false;
        Stoneware stoneware = (Stoneware) o;
        return Objects.equals(getCollection(), stoneware.getCollection()) && Objects.equals(getSize(), stoneware.getSize()) && Objects.equals(getThickness(), stoneware.getThickness()) && Objects.equals(getColor(), stoneware.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCollection(), getSize(), getThickness(), getColor());
    }

    @Override
    public String toString() {
        return "Stoneware{" +
                "collection='" + collection + '\'' +
                ", size=" + size +
                ", thickness=" + thickness +
                ", color='" + color + '\'' +
                '}';
    }
}
