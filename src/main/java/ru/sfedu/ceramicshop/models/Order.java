package ru.sfedu.ceramicshop.models;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.ceramicshop.utils.CustomCartConverter;
import ru.sfedu.ceramicshop.utils.CustomUserConverter;

import java.io.Serializable;
import java.util.Objects;


/**
 * The type Order.
 */
@Root(name = "Order")
public class Order implements Serializable {
    @Attribute
    @CsvBindByName
    private long id;
    @Element
    @CsvCustomBindByName(converter = CustomCartConverter.class)
    private Cart cart;
    @Element
    @CsvBindByName
    private Boolean needService;
    @Element
    @CsvBindByName
    private Integer price;

    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    /**
     * Instantiates a new Order.
     *
     * @param id          the id
     * @param cart        the cart
     * @param needService the need service
     * @param price       the price
     */
    public Order(long id, Cart cart, Boolean needService, Integer price) {
        this.id = id;
        this.cart = cart;
        this.needService = needService;
        this.price = price;
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
     * Gets cart.
     *
     * @return the cart
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Sets cart.
     *
     * @param cart the cart
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * Gets need service.
     *
     * @return the need service
     */
    public Boolean getNeedService() {
        return needService;
    }

    /**
     * Sets need service.
     *
     * @param needService the need service
     */
    public void setNeedService(Boolean needService) {
        this.needService = needService;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getId() == order.getId() && Objects.equals(getCart(), order.getCart()) && Objects.equals(getNeedService(), order.getNeedService()) && Objects.equals(getPrice(), order.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCart(), getNeedService(), getPrice());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cart=" + cart +
                ", needService=" + needService +
                ", price=" + price +
                '}';
    }
}
