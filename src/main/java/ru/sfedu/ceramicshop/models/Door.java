package ru.sfedu.ceramicshop.models;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Objects;

/**
 * The type Door.
 */
@Root(name = "Stoneware")
public class Door extends Item{
    @Element
    @CsvBindByName
    private String type;

    /**
     * Instantiates a new Door.
     *
     * @param type the type
     */
    public Door(String type) {
        this.type = type;
    }

    /**
     * Instantiates a new Door.
     *
     * @param id           the id
     * @param price        the price
     * @param manufacturer the manufacturer
     * @param number       the number
     * @param vendorCode   the vendor code
     * @param type         the type
     */
    public Door(long id, Integer price, String manufacturer, Integer number, String vendorCode, String type) {
        super(id, price, manufacturer, number, vendorCode);
        this.type = type;
    }

    /**
     * Instantiates a new Door.
     */
    public Door() {
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
        if (!(o instanceof Door)) return false;
        if (!super.equals(o)) return false;
        Door door = (Door) o;
        return Objects.equals(getType(), door.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType());
    }

    @Override
    public String toString() {
        return "Door{" +
                "type='" + type + '\'' +
                '}';
    }
}

