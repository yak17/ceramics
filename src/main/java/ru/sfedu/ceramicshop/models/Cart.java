package ru.sfedu.ceramicshop.models;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.ceramicshop.utils.CustomItemConverterList;
import ru.sfedu.ceramicshop.utils.CustomUserConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


/**
 * The type Cart.
 */
@Root(name = "cart")
public class Cart implements Serializable {
    @Attribute
    @CsvBindByName
    private long id;
    @ElementList
    @CsvCustomBindByName(converter = CustomItemConverterList.class)
    private List<Item> itemList;
    @Element
    @CsvCustomBindByName(converter = CustomUserConverter.class)
    private User user;


    /**
     * Instantiates a new Cart.
     */
    public Cart() {

    }

    /**
     * Instantiates a new Cart.
     *
     * @param id       the id
     * @param itemList the item list
     * @param user     the user
     */
    public Cart(long id, List<Item> itemList, User user) {
        this.id = id;
        this.itemList = itemList;
        this.user = user;
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

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return getId() == cart.getId() && Objects.equals(getItemList(), cart.getItemList()) && Objects.equals(getUser(), cart.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getItemList(), getUser());
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", itemList=" + itemList +
                ", user=" + user +
                '}';
    }
}
