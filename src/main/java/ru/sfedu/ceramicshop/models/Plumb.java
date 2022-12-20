package ru.sfedu.ceramicshop.models;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Objects;

/**
 * The type Plumb.
 */
@Root(name = "Plumb")
public class Plumb extends Item{
    @Element
    @CsvBindByName
    private Integer dimensions;
    @Element
    @CsvBindByName
    private String mountingType;
    @Element
    @CsvBindByName
    private String type;

    /**
     * Instantiates a new Plumb.
     */
    public Plumb() {
    }

    /**
     * Instantiates a new Plumb.
     *
     * @param dimensions   the dimensions
     * @param mountingType the mounting type
     * @param type         the type
     */
    public Plumb(Integer dimensions, String mountingType, String type) {
        this.dimensions = dimensions;
        this.mountingType = mountingType;
        this.type = type;
    }

    /**
     * Instantiates a new Plumb.
     *
     * @param id           the id
     * @param price        the price
     * @param manufacturer the manufacturer
     * @param number       the number
     * @param vendorCode   the vendor code
     * @param dimensions   the dimensions
     * @param mountingType the mounting type
     * @param type         the type
     */
    public Plumb(long id, Integer price, String manufacturer, Integer number, String vendorCode, Integer dimensions, String mountingType, String type) {
        super(id, price, manufacturer, number, vendorCode);
        this.dimensions = dimensions;
        this.mountingType = mountingType;
        this.type = type;
    }

    /**
     * Gets dimensions.
     *
     * @return the dimensions
     */
    public Integer getDimensions() {
        return dimensions;
    }

    /**
     * Sets dimensions.
     *
     * @param dimensions the dimensions
     */
    public void setDimensions(Integer dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * Gets mounting type.
     *
     * @return the mounting type
     */
    public String getMountingType() {
        return mountingType;
    }

    /**
     * Sets mounting type.
     *
     * @param mountingType the mounting type
     */
    public void setMountingType(String mountingType) {
        this.mountingType = mountingType;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plumb)) return false;
        if (!super.equals(o)) return false;
        Plumb plumb = (Plumb) o;
        return Objects.equals(getDimensions(), plumb.getDimensions()) && Objects.equals(getMountingType(), plumb.getMountingType()) && Objects.equals(getType(), plumb.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDimensions(), getMountingType(), getType());
    }

    @Override
    public String toString() {
        return "Plumb{" +
                "dimensions=" + dimensions +
                ", mountingType='" + mountingType + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
