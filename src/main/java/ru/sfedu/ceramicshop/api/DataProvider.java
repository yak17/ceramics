package ru.sfedu.ceramicshop.api;


import ru.sfedu.ceramicshop.Result;
import ru.sfedu.ceramicshop.models.*;

import java.util.List;
import java.util.Optional;

/**
 * The interface Data provider.
 */
public interface DataProvider {

    /**
     * Make order result.
     *
     * @param cartId      the cart id
     * @param needService the need service
     * @return the result
     */
    Result<Order> makeOrder(long cartId, boolean needService);

    /**
     * Is need service result.
     *
     * @param cart the cart
     * @return the result
     */
    Result<Integer> isNeedService(Cart cart);

    /**
     * Count order price result.
     *
     * @param cartId the cart id
     * @return the result
     */
    Result<Integer> countOrderPrice(long cartId);

    /**
     * Show catalog by type result.
     *
     * @param type the type
     * @return the result
     */
    Result<List<Item>> showCatalogByType(String type);

    /**
     * Fill shop cart result.
     *
     * @param vendorCode the vendor code
     * @param userId     the user id
     * @return the result
     */
    Result<Boolean> fillShopCart(String vendorCode, long userId);

    /**
     * Add stoneware result.
     *
     * @param userId    the user id
     * @param stoneware the stoneware
     * @return the result
     */
    Result<Boolean> addStoneware(long userId, Stoneware stoneware);

    /**
     * Add door result.
     *
     * @param id   the id
     * @param door the door
     * @return the result
     */
    Result<Boolean> addDoor(long id, Door door);

    /**
     * Add plumbing result.
     *
     * @param id    the id
     * @param plumb the plumb
     * @return the result
     */
    Result<Boolean> addPlumbing(long id, Plumb plumb);


    /**
     * Create item result.
     *
     * @param item the item
     * @return the result
     */
    Result<Item> createItem(Item item);

    /**
     * Gets item by id.
     *
     * @param id the id
     * @return the item by id
     */
    Optional<Item> getItemById(long id);

    /**
     * Update item result.
     *
     * @param item the item
     * @return the result
     */
    Result<Item> updateItem(Item item);

    /**
     * Delete item result.
     *
     * @param id the id
     * @return the result
     */
    Result<Boolean> deleteItem(long id);

    /**
     * Create stoneware result.
     *
     * @param stoneware the stoneware
     * @return the result
     */
    Result<Stoneware> createStoneware(Stoneware stoneware);

    /**
     * Gets stoneware by id.
     *
     * @param id the id
     * @return the stoneware by id
     */
    Optional<Stoneware> getStonewareById(long id);

    /**
     * Update stoneware result.
     *
     * @param stoneware the stoneware
     * @return the result
     */
    Result<Stoneware> updateStoneware(Stoneware stoneware);

    /**
     * Delete stoneware result.
     *
     * @param id the id
     * @return the result
     */
    Result<Boolean> deleteStoneware(long id);

    /**
     * Create door result.
     *
     * @param door the door
     * @return the result
     */
    Result<Door> createDoor(Door door);

    /**
     * Gets door by id.
     *
     * @param id the id
     * @return the door by id
     */
    Optional<Door> getDoorById(long id);

    /**
     * Update door result.
     *
     * @param door the door
     * @return the result
     */
    Result<Door> updateDoor(Door door);

    /**
     * Delete door result.
     *
     * @param id the id
     * @return the result
     */
    Result<Boolean> deleteDoor(long id);

    /**
     * Create plumb result.
     *
     * @param plumb the plumb
     * @return the result
     */
    Result<Plumb> createPlumb(Plumb plumb);

    /**
     * Gets plumb by id.
     *
     * @param id the id
     * @return the plumb by id
     */
    Optional<Plumb> getPlumbById(long id);

    /**
     * Update plumb result.
     *
     * @param plumb the plumb
     * @return the result
     */
    Result<Plumb> updatePlumb(Plumb plumb);

    /**
     * Delete plumb result.
     *
     * @param id the id
     * @return the result
     */
    Result<Boolean> deletePlumb(long id);

    /**
     * Create catalog result.
     *
     * @param catalog the catalog
     * @return the result
     */
    Result<Catalog> createCatalog(Catalog catalog);

    /**
     * Gets catalog by id.
     *
     * @param id the id
     * @return the catalog by id
     */
    Optional<Catalog> getCatalogById(long id);

    /**
     * Update catalog result.
     *
     * @param catalog the catalog
     * @return the result
     */
    Result<Catalog> updateCatalog(Catalog catalog);

    /**
     * Delete catalog result.
     *
     * @param id the id
     * @return the result
     */
    Result<Boolean> deleteCatalog(long id);

    /**
     * Create cart result.
     *
     * @param cart the cart
     * @return the result
     */
    Result<Cart> createCart(Cart cart);

    /**
     * Gets cart by id.
     *
     * @param id the id
     * @return the cart by id
     */
    Optional<Cart> getCartById(long id);

    /**
     * Update cart result.
     *
     * @param cart the cart
     * @return the result
     */
    Result<Cart> updateCart(Cart cart);

    /**
     * Delete cart result.
     *
     * @param id the id
     * @return the result
     */
    Result<Boolean> deleteCart(long id);

    /**
     * Create user result.
     *
     * @param user the user
     * @return the result
     */
    Result<User> createUser(User user);

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    Optional<User> getUserById(long id);

    /**
     * Update user result.
     *
     * @param user the user
     * @return the result
     */
    Result<User> updateUser(User user);

    /**
     * Delete user result.
     *
     * @param id the id
     * @return the result
     */
    Result<Boolean> deleteUser(long id);

    /**
     * Create order result.
     *
     * @param order the order
     * @return the result
     */
    Result<Order> createOrder(Order order);

    /**
     * Gets order by id.
     *
     * @param id the id
     * @return the order by id
     */
    Optional<Order> getOrderById(long id);

    /**
     * Update order result.
     *
     * @param order the order
     * @return the result
     */
    Result<Order> updateOrder(Order order);

    /**
     * Delete order result.
     *
     * @param id the id
     * @return the result
     */
    Result<Boolean> deleteOrder(long id);

}
