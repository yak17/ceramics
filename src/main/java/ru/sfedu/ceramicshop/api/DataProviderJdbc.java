package ru.sfedu.ceramicshop.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.ceramicshop.Constants;
import ru.sfedu.ceramicshop.Result;
import ru.sfedu.ceramicshop.models.*;
import ru.sfedu.ceramicshop.models.enums.Outcomes;
import ru.sfedu.ceramicshop.utils.ConfigurationUtil;

import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.*;

import static java.lang.Thread.currentThread;
import static ru.sfedu.ceramicshop.models.enums.Outcomes.Fail;
import static ru.sfedu.ceramicshop.models.enums.Outcomes.Success;
import static ru.sfedu.ceramicshop.utils.HistoryUtil.saveToLog;

/**
 * The type Data provider jdbc.
 */
public class DataProviderJdbc implements DataProvider {

    /**
     * The Connection.
     */
    Connection connection;
    /**
     * The constant log.
     */
    public static final Logger log = LogManager.getLogger(DataProviderJdbc.class);

    @Override
    public Result<Order> makeOrder(long cartId, boolean needService) {
        try {
            Order order = new Order();
            order.setNeedService(needService);
            Cart cart = getCartById(cartId).orElse(new Cart());
            order.setCart(cart);
            if (needService) {
                order.setPrice(isNeedService(cart).getData() + countOrderPrice(cartId).getData());
            } else {
                order.setPrice(countOrderPrice(cartId).getData());
            }
            return new Result<>(Success, order);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Integer> isNeedService(Cart cart) {
        try {
            int additionPrice = 0;
            Optional<Item> optStoneItem = cart.getItemList().stream()
                    .filter(el -> getClassName(el.getClass()).equals(Constants.Stoneware_CSV))
                    .findFirst();
            Optional<Item> optDoorItem = cart.getItemList().stream()
                    .filter(el -> getClassName(el.getClass()).equals(Constants.Door_CSV))
                    .findFirst();
            Optional<Item> optPlumbItem = cart.getItemList().stream()
                    .filter(el -> getClassName(el.getClass()).equals(Constants.Plumb_CSV))
                    .findFirst();
            if (optStoneItem.isPresent()) {
                additionPrice += 6000;
            }
            if (optDoorItem.isPresent()) {
                additionPrice += 4000;
            }
            if (optPlumbItem.isPresent()) {
                additionPrice += 8000;
            }
            return new Result<>(Success, additionPrice);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }


    @Override
    public Result<Integer> countOrderPrice(long cartId) {
        try {
            int mainPrice;
            mainPrice = getCartById(cartId).get().getItemList().stream()
                    .mapToInt(el -> el.getPrice() * el.getNumber())
                    .sum();
            return new Result<>(Success, mainPrice);
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<List<Item>> showCatalogByType(String type) {
        try {
            return switch (type.toLowerCase()) {
                case Constants.STONEWARE -> new Result(Success, getListOfStoneware());
                case Constants.DOOR -> new Result(Success, getListOfDoor());
                case Constants.PLUMB -> new Result(Success, getListOfPlumb());
                default -> new Result<>(Fail);
            };
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Boolean> fillShopCart(String vendorCode, long userId) {
        try {
            final String method = currentThread().getStackTrace()[1].getMethodName();
            List<Stoneware> stonewares = getListOfStoneware();
            List<Door> doors = getListOfDoor();
            List<Plumb> plumbs = getListOfPlumb();
            Optional<Stoneware> optStoneItem = stonewares.stream()
                    .filter(el -> el.getVendorCode().equals(vendorCode))
                    .findFirst();
            Optional<Door> optDoorItem = doors.stream()
                    .filter(el -> el.getVendorCode().equals(vendorCode))
                    .findFirst();
            Optional<Plumb> optPlumbItem = plumbs.stream()
                    .filter(el -> el.getVendorCode().equals(vendorCode))
                    .findFirst();
            Result result = new Result(Fail);
            if (optStoneItem.isPresent()) {
                result = addStoneware(userId, optStoneItem.get());
                return new Result(result.getStatus(), result.getData());
            }
            if (optDoorItem.isPresent()) {
                result = addDoor(userId, optDoorItem.get());
                return new Result(result.getStatus(), result.getData());
            }
            if (optPlumbItem.isPresent()) {
                result = addPlumbing(userId, optPlumbItem.get());
                return new Result(result.getStatus(), result.getData());
            }
            return result;
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result<Boolean> addStoneware(long id, Stoneware stoneware) {
        try {
            List<Cart> carts = getCartList();
            Optional<Cart> optCart = carts.stream()
                    .filter(el -> el.getUser().getId() == id)
                    .findFirst();
            optCart.get().getItemList().add(stoneware);
            if (updateCart(optCart.get()).getStatus().equals(Success)) {
                return new Result<>(Success, true);
            } else {
                return new Result<>(Fail, false);
            }
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail, false);
        }
    }

    @Override
    public Result<Boolean> addDoor(long id, Door door) {
        try {
            List<Cart> carts = getCartList();
            Optional<Cart> optionalDoor = carts.stream()
                    .filter(el -> el.getUser().getId() == id)
                    .findFirst();
            optionalDoor.get().getItemList().add(door);
            if (updateCart(optionalDoor.get()).getStatus().equals(Success)) {
                return new Result<>(Success, true);
            } else {
                return new Result<>(Fail, false);
            }
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail, false);
        }
    }

    @Override
    public Result<Boolean> addPlumbing(long id, Plumb plumb) {
        try {
            List<Cart> carts = getCartList();
            Optional<Cart> optionalPlumbing = carts.stream()
                    .filter(el -> el.getUser().getId() == id)
                    .findFirst();
            optionalPlumbing.get().getItemList().add(plumb);
            if (updateCart(optionalPlumbing.get()).getStatus().equals(Success)) {
                return new Result<>(Success, true);
            } else {
                return new Result<>(Fail, false);
            }
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail, false);
        }
    }

    @Override
    public Result<Item> createItem(Item item) {
        Outcomes outcomes;
        try {
            outcomes = execute(item, String.format(
                    Constants.INSERT_ITEM,
                    item.getPrice(),
                    item.getManufacturer(),
                    item.getNumber(),
                    item.getVendorCode()
            ), Constants.ADD_ITEM).getStatus();
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes, item);
    }

    @Override
    public Optional<Item> getItemById(long id) {
        try {
            ResultSet set = setResById(Item.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                Item item = new Item();
                item.setId(set.getLong(Constants.ITEM_ID));
                item.setPrice(set.getInt(Constants.ITEM_PRICE));
                item.setManufacturer(set.getString(Constants.ITEM_MANIFACTURER));
                item.setNumber(set.getInt(Constants.ITEM_NUMBER));
                item.setVendorCode(set.getString(Constants.ITEM_CODE));
                return Optional.of(item);
            } else {
                return Optional.of(new Item());
            }
        } catch (SQLException e) {
            log.error(e);
            return Optional.of(new Item());
        }
    }

    @Override
    public Result<Item> updateItem(Item item) {
        Outcomes outcomes;
        try {
            outcomes = execute(item, String.format(Constants.UPDATE_ITEM,
                    item.getPrice(),
                    item.getManufacturer(),
                    item.getNumber(),
                    item.getVendorCode(), item.getId()), Constants.UPD_ITEM).getStatus();
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes);
    }

    @Override
    public Result<Boolean> deleteItem(long id) {
        try {
            boolean a = execute(null, String.format(Constants.DELETE_ITEM, id), Constants.DEL_ITEM).getData() == Outcomes.Success;
            if (a) return new Result<>(Outcomes.Success);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Stoneware> createStoneware(Stoneware stoneware) {
        Outcomes outcomes;
        try {
            outcomes = execute(stoneware, String.format(
                    Constants.INSERT_STONEWARE,
                    stoneware.getPrice(),
                    stoneware.getManufacturer(),
                    stoneware.getNumber(),
                    stoneware.getVendorCode(),
                    stoneware.getCollection(),
                    stoneware.getSize(),
                    stoneware.getThickness(),
                    stoneware.getColor()
            ), Constants.ADD_STONEWARE).getStatus();
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes, stoneware);
    }

    @Override
    public Optional<Stoneware> getStonewareById(long id) {
        try {
            ResultSet set = setResById(Stoneware.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                Stoneware stoneware = new Stoneware();
                stoneware.setId(set.getLong(Constants.ITEM_ID));
                stoneware.setPrice(set.getInt(Constants.ITEM_PRICE));
                stoneware.setManufacturer(set.getString(Constants.ITEM_MANIFACTURER));
                stoneware.setNumber(set.getInt(Constants.ITEM_NUMBER));
                stoneware.setVendorCode(set.getString(Constants.ITEM_CODE));
                stoneware.setCollection(set.getString(Constants.STONEWARE_COLLECTION));
                stoneware.setSize(set.getInt(Constants.STONEWARE_SIZE));
                stoneware.setThickness(set.getInt(Constants.STONEWARE_THICKNESS));
                stoneware.setColor(set.getString(Constants.STONEWARE_COLOR));
                return Optional.of(stoneware);
            } else {
                return Optional.of(new Stoneware());
            }
        } catch (SQLException e) {
            log.error(e);
            return Optional.of(new Stoneware());
        }
    }

    @Override
    public Result<Stoneware> updateStoneware(Stoneware stoneware) {
        Outcomes outcomes;
        try {
            outcomes = execute(stoneware, String.format(Constants.UPDATE_STONEWARE,
                    stoneware.getPrice(),
                    stoneware.getManufacturer(),
                    stoneware.getNumber(),
                    stoneware.getVendorCode(),
                    stoneware.getCollection(),
                    stoneware.getSize(),
                    stoneware.getThickness(),
                    stoneware.getColor(), stoneware.getId()), Constants.UPD_STONEWARE).getStatus();
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes);
    }

    @Override
    public Result<Boolean> deleteStoneware(long id) {
        try {
            boolean a = execute(null, String.format(Constants.DELETE_STONEWARE, id), Constants.DEL_STONEWARE).getData() == Outcomes.Success;
            if (a) return new Result<>(Outcomes.Success);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Door> createDoor(Door door) {
        Outcomes outcomes;
        try {
            outcomes = execute(door, String.format(
                    Constants.INSERT_DOOR,
                    door.getPrice(),
                    door.getManufacturer(),
                    door.getNumber(),
                    door.getVendorCode(),

                    door.getType()
            ), Constants.ADD_DOOR).getStatus();
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes, door);
    }

    @Override
    public Optional<Door> getDoorById(long id) {
        try {
            ResultSet set = setResById(Door.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                Door door = new Door();
                door.setId(set.getLong(Constants.ITEM_ID));
                door.setPrice(set.getInt(Constants.ITEM_PRICE));
                door.setManufacturer(set.getString(Constants.ITEM_MANIFACTURER));
                door.setNumber(set.getInt(Constants.ITEM_NUMBER));
                door.setVendorCode(set.getString(Constants.ITEM_CODE));
                door.setType(set.getString(Constants.DOOR_TYPE));
                return Optional.of(door);
            } else {
                return Optional.of(new Door());
            }
        } catch (SQLException e) {
            log.error(e);
            return Optional.of(new Door());
        }
    }

    @Override
    public Result<Door> updateDoor(Door door) {
        Outcomes outcomes;
        try {
            outcomes = execute(door, String.format(Constants.UPDATE_DOOR,
                    door.getPrice(),
                    door.getManufacturer(),
                    door.getNumber(),
                    door.getVendorCode(),

                    door.getType(), door.getId()), Constants.UPD_DOOR).getStatus();
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes);
    }

    @Override
    public Result<Boolean> deleteDoor(long id) {
        try {
            boolean a = execute(null, String.format(Constants.DELETE_DOOR, id), Constants.DEL_DOOR).getData() == Outcomes.Success;
            if (a) return new Result<>(Outcomes.Success);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Plumb> createPlumb(Plumb plumb) {
        Outcomes outcomes;
        try {
            outcomes = execute(plumb, String.format(
                    Constants.INSERT_PLUMB,
                    plumb.getPrice(),
                    plumb.getManufacturer(),
                    plumb.getNumber(),
                    plumb.getVendorCode(),

                    plumb.getDimensions(),
                    plumb.getMountingType(),
                    plumb.getType()
            ), Constants.ADD_PLUMB).getStatus();
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes, plumb);
    }

    @Override
    public Optional<Plumb> getPlumbById(long id) {
        try {
            ResultSet set = setResById(Plumb.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                Plumb plumb = new Plumb();
                plumb.setId(set.getLong(Constants.ITEM_ID));
                plumb.setPrice(set.getInt(Constants.ITEM_PRICE));
                plumb.setManufacturer(set.getString(Constants.ITEM_MANIFACTURER));
                plumb.setNumber(set.getInt(Constants.ITEM_NUMBER));
                plumb.setVendorCode(set.getString(Constants.ITEM_CODE));

                plumb.setDimensions(set.getInt(Constants.PLUMB_DIMENSIONS));
                plumb.setMountingType(set.getString(Constants.PLUMB_MOUNT_TYPE));
                plumb.setType(set.getString(Constants.PLUMB_TYPE));
                return Optional.of(plumb);
            } else {
                return Optional.of(new Plumb());
            }
        } catch (SQLException e) {
            log.error(e);
            return Optional.of(new Plumb());
        }
    }

    @Override
    public Result<Plumb> updatePlumb(Plumb plumb) {
        Outcomes outcomes;
        try {
            outcomes = execute(plumb, String.format(Constants.UPDATE_PLUMB,
                    plumb.getPrice(),
                    plumb.getManufacturer(),
                    plumb.getNumber(),
                    plumb.getVendorCode(),
                    plumb.getDimensions(),
                    plumb.getMountingType(),
                    plumb.getType(), plumb.getId()), Constants.UPD_PLUMB).getStatus();
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes);
    }

    @Override
    public Result<Boolean> deletePlumb(long id) {
        try {
            boolean a = execute(null, String.format(Constants.DELETE_PLUMB, id), Constants.DEL_PLUMB).getData() == Outcomes.Success;
            if (a) return new Result<>(Outcomes.Success);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Catalog> createCatalog(Catalog catalog) {
        Outcomes outcomes;
        try {
            Outcomes outcomes1 = execute(catalog, Constants.INSERT_CATALOG, Constants.ADD_CATALOG).getStatus();
            DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(Locale.getDefault());
            unusualSymbols.setDecimalSeparator('.');
            catalog.getItemList().stream().forEach(el -> {
                try {
                    this.execute(null,
                            String.format(
                                    Constants.INSERT_CATALOG_ITEM,
                                    catalog.getId(), el.getId()
                            ), Constants.ADD_CATALOG_ITEM
                    );
                    log.debug("will add to db " + el);
                } catch (Exception e) {
                    log.error(e);
                    log.debug("insert fail " + el);
                }
            });
            outcomes = outcomes1;
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes, catalog);
    }

    @Override
    public Optional<Catalog> getCatalogById(long id) {
        try {
            ResultSet set = setResById(Catalog.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                Catalog catalog = new Catalog();
                catalog.setId(set.getLong(Constants.CATALOG_ID));
                ResultSet setItemList = getFromCatalogItems(id);
                List<Item> itemList = new ArrayList<>();
                if (setItemList != null && setItemList.next()) {
                    itemList.add(getItemById(setItemList.getInt(Constants.CATALOGITEMID)).get());
                }
                catalog.setItemList(itemList);
                return Optional.of(catalog);
            } else {
                return Optional.of(new Catalog());
            }
        } catch (SQLException e) {
            log.error(e);
            return Optional.of(new Catalog());
        }
    }


    @Override
    public Result<Catalog> updateCatalog(Catalog catalog) {
        return null;
    }

    @Override
    public Result<Boolean> deleteCatalog(long id) {
        try {
            boolean a = execute(null, String.format(Constants.DELETE_CATALOG, id), Constants.DEL_CATALOG).getData() == Outcomes.Success;
            execute(null, String.format(Constants.DELETE_CATALOG_ITEM, id), Constants.DEL_CATALOG_ITEM);
            if (a) return new Result<>(Outcomes.Success);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Cart> createCart(Cart cart) {
        Outcomes outcomes;
        try {
            Outcomes outcomes1 = execute(cart, String.format(
                    Constants.INSERT_CART,
                    cart.getUser().getId()
            ), Constants.ADD_CART).getStatus();
            DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(Locale.getDefault());
            unusualSymbols.setDecimalSeparator('.');
            cart.getItemList().stream().forEach(el -> {
                try {
                    this.execute(null,
                            String.format(
                                    Constants.INSERT_CART_ITEM,
                                    cart.getId(), el.getId()
                            ), Constants.ADD_CART_ITEM
                    );
                    log.debug("will add to db " + el);
                } catch (Exception e) {
                    log.error(e);
                    log.debug("insert fail " + el);
                }
            });
            outcomes = outcomes1;
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes, cart);
    }

    @Override
    public Optional<Cart> getCartById(long id) {
        try {
            ResultSet set = setResById(Cart.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                Cart cart = new Cart();
                cart.setId(set.getLong(Constants.CART_ID));
                ResultSet setItemList = getFromCartItems(id);
                List<Item> itemList = new ArrayList<>();
                if (setItemList != null && setItemList.next()) {
                    itemList.add(getStonewareById(setItemList.getInt(Constants.CART_ITEM_ID)).get());
                }
                cart.setItemList(itemList);
                cart.setUser(getUserById(set.getLong(Constants.CART_USER_ID)).get());
                return Optional.of(cart);
            } else {
                return Optional.of(new Cart());
            }
        } catch (SQLException e) {
            log.error(e);
            return Optional.of(new Cart());
        }
    }

    @Override
    public Result<Cart> updateCart(Cart cart) {
        Outcomes outcomes;
        try {
            outcomes = execute(cart, String.format(Constants.UPDATE_CART,
                    cart.getUser().getId(), cart.getId()), Constants.UPD_CART).getStatus();
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes);
    }

    @Override
    public Result<Boolean> deleteCart(long id) {
        try {
            boolean a = execute(null, String.format(Constants.DELETE_CART, id), Constants.DEL_CART).getData() == Outcomes.Success;
            execute(null, String.format(Constants.DELETE_CART_ITEM, id), Constants.DEL_CART_ITEM);
            if (a) return new Result<>(Outcomes.Success);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<User> createUser(User user) {
        Outcomes outcomes;
        try {
            outcomes = execute(user, String.format(
                    Constants.INSERT_USER,
                    user.getName(),
                    user.getEmail(),
                    user.getPhone()
            ), Constants.ADD_USER).getStatus();
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes, user);
    }

    @Override
    public Optional<User> getUserById(long id) {
        try {
            ResultSet set = setResById(User.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                User user = new User();
                user.setId(set.getLong(Constants.USER_ID));
                user.setName(set.getString(Constants.USER_NAME));
                user.setEmail(set.getString(Constants.USER_EMAIL));
                user.setPhone(set.getString(Constants.USER_PHONE));
                return Optional.of(user);
            } else {
                return Optional.of(new User());
            }
        } catch (SQLException e) {
            log.error(e);
            return Optional.of(new User());
        }
    }

    @Override
    public Result<User> updateUser(User user) {
        Outcomes outcomes;
        try {
            outcomes = execute(user, String.format(Constants.UPDATE_USER,
                    user.getName(),
                    user.getEmail(),
                    user.getPhone(), user.getId()), Constants.UPD_USER).getStatus();
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes);
    }

    @Override
    public Result<Boolean> deleteUser(long id) {
        try {
            boolean a = execute(null, String.format(Constants.DELETE_USER, id), Constants.DEL_USER).getData() == Outcomes.Success;
            if (a) return new Result<>(Outcomes.Success);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }

    @Override
    public Result<Order> createOrder(Order order) {
        Outcomes outcomes;
        try {
            outcomes = execute(order, String.format(
                    Constants.INSERT_ORDER,
                    order.getCart().getId(),
                    order.getNeedService(),
                    order.getPrice()
            ), Constants.ADD_ORDER).getStatus();
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes, order);
    }

    @Override
    public Optional<Order> getOrderById(long id) {
        try {
            ResultSet set = setResById(Order.class, id);
            log.debug(set);
            if (set != null && set.next()) {
                Order order = new Order();
                order.setId(set.getLong(Constants.ORDER_ID));
                order.setCart(getCartById(set.getLong(Constants.ORDER_CART)).get());
                order.setNeedService(set.getBoolean(Constants.ORDER_IS_SERVICE));
                order.setPrice(set.getInt(Constants.ORDER_PRICE));
                return Optional.of(order);
            } else {
                return Optional.of(new Order());
            }
        } catch (SQLException e) {
            log.error(e);
            return Optional.of(new Order());
        }
    }

    @Override
    public Result<Order> updateOrder(Order order) {
        Outcomes outcomes;
        try {
            outcomes = execute(order, String.format(Constants.UPDATE_ORDER,
                    order.getCart().getId(),
                    order.getNeedService(),
                    order.getPrice(), order.getId()), Constants.UPD_ORDER).getStatus();
        } catch (Exception e) {
            log.error(e);
            outcomes = Outcomes.Fail;
        }
        return new Result<>(outcomes);
    }

    @Override
    public Result<Boolean> deleteOrder(long id) {
        try {
            boolean a = execute(null, String.format(Constants.DELETE_ORDER, id), Constants.DEL_ORDER).getData() == Outcomes.Success;
            if (a) return new Result<>(Outcomes.Success);
        } catch (Exception e) {
            log.error(e);
        }
        return new Result<>(Outcomes.Fail);
    }


    private <T> Result execute(T lst, String sql, String method) {
        Outcomes outcomes;
        try {
            if (lst == null) {
                lst = (T) "";
            }
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.executeUpdate();
            getConnection().close();
            outcomes = Outcomes.Success;
        } catch (SQLException e) {
            outcomes = Outcomes.Fail;
            log.error(e);
        }
        saveToLog(createHistoryContent(method, lst, outcomes));
        return new Result(outcomes);
    }

    private Connection getConnection() {
        try {
            connection = DriverManager.getConnection(
                    ConfigurationUtil.getConfigurationEntry(Constants.DB_CONNECT),
                    ConfigurationUtil.getConfigurationEntry(Constants.DB_USER),
                    ConfigurationUtil.getConfigurationEntry(Constants.DB_PASS)
            );
            return connection;
        } catch (SQLException | IOException e) {
            log.error(e);
            return null;
        }
    }

    private static HistoryContent createHistoryContent(String method, Object object, Outcomes status) {
        return new HistoryContent(DataProviderJdbc.class.getSimpleName(), new Date(), Constants.DEFAULT_ACTOR, method, object, status);
    }

    private ResultSet setResById(Class cl, long id) {
        ResultSet set = getRecords(String.format(Constants.SELECT_ALL,
                cl.getSimpleName().toLowerCase(), id));
        return set;
    }

    private ResultSet getRecords(String sql) {
        log.info(sql);
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            getConnection().close();
            return statement.executeQuery();
        } catch (SQLException e) {
            log.info(e);
        }
        return null;
    }

    private ResultSet getFromCatalogItems(long id) {
        return getRecords(String.format(Constants.GETCATALOGITEM, id));
    }

    private ResultSet getFromCartItems(long id) {
        return getRecords(String.format(Constants.GETCARTITEM, id));
    }

    /**
     * Create tables.
     */
    public void createTables() {
        execute(null, String.format(Constants.CREATE_ITEM), Constants.ITEM_CREATE);
        execute(null, String.format(Constants.CREATE_STONEWARE), Constants.STONEWARE_CREATE);
        execute(null, String.format(Constants.CREATE_DOOR), Constants.DOOR_CREATE);
        execute(null, String.format(Constants.CREATE_PLUMB), Constants.PLUMB_CREATE);
        execute(null, String.format(Constants.CREATE_USER), Constants.USER_CREATE);
        execute(null, String.format(Constants.CREATE_CATALOG), Constants.CATALOG_CREATE);
        execute(null, String.format(Constants.CREATE_CART), Constants.CART_CREATE);
        execute(null, String.format(Constants.CREATE_ORDERS), Constants.ORDERS_CREATE);
        execute(null, String.format(Constants.CART_ITEM), Constants.CART_ITEM_CREATE);
        execute(null, String.format(Constants.CATALOG_ITEM), Constants.CATALOG_ITEM_CREATE);
    }

    /**
     * Delete all record.
     */
    public void deleteAllRecord() {
        deleteRecord(Item.class);
        deleteRecord(Stoneware.class);
        deleteRecord(Door.class);
        deleteRecord(Plumb.class);
        deleteRecord(User.class);
        deleteRecord(Catalog.class);
        deleteRecord(Cart.class);
        deleteRecord(Order.class);
    }

    /**
     * Delete record.
     *
     * @param <T> the type parameter
     * @param cl  the cl
     */
    public <T> void deleteRecord(Class<T> cl) {
        execute(null, String.format(Constants.DROP,
                cl.getSimpleName().toUpperCase()), Constants.DROP_NAME);
        new Result<>(Outcomes.Success);
    }

    /**
     * Gets class name.
     *
     * @param <T> the type parameter
     * @param cl  the cl
     * @return the class name
     */
    public <T extends Item> String getClassName(Class<T> cl) {
        return switch (cl.getSimpleName().toLowerCase()) {
            case Constants.STONEWARE_CONST -> Constants.Stoneware_CSV;
            case Constants.DOOR_CONST -> Constants.Door_CSV;
            case Constants.PLUMB_CONST -> Constants.Plumb_CSV;
            default -> "---WRITING---";
        };
    }

    /**
     * Gets list of stoneware.
     *
     * @return the list of stoneware
     */
    public List<Stoneware> getListOfStoneware() {
        List<Stoneware> stonewareList = new ArrayList<>();
        ResultSet set = getRecords(Constants.DB_SELECT_ALL_STONEWARE);
        try {
            while (set.next()) {
                Stoneware stoneware = new Stoneware();
                stoneware.setId(set.getLong(Constants.ITEM_ID));
                stoneware.setPrice(set.getInt(Constants.ITEM_PRICE));
                stoneware.setManufacturer(set.getString(Constants.ITEM_MANIFACTURER));
                stoneware.setNumber(set.getInt(Constants.ITEM_NUMBER));
                stoneware.setVendorCode(set.getString(Constants.ITEM_CODE));
                stoneware.setCollection(set.getString(Constants.STONEWARE_COLLECTION));
                stoneware.setSize(set.getInt(Constants.STONEWARE_SIZE));
                stoneware.setThickness(set.getInt(Constants.STONEWARE_THICKNESS));
                stoneware.setColor(set.getString(Constants.STONEWARE_COLOR));
                stonewareList.add(stoneware);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return stonewareList;
    }

    /**
     * Gets list of door.
     *
     * @return the list of door
     */
    public List<Door> getListOfDoor() {
        List<Door> doorList = new ArrayList<>();
        ResultSet set = getRecords(Constants.DB_SELECT_ALL_DOOR);
        try {
            while (set.next()) {
                Door door = new Door();
                door.setId(set.getLong(Constants.ITEM_ID));
                door.setPrice(set.getInt(Constants.ITEM_PRICE));
                door.setManufacturer(set.getString(Constants.ITEM_MANIFACTURER));
                door.setNumber(set.getInt(Constants.ITEM_NUMBER));
                door.setVendorCode(set.getString(Constants.ITEM_CODE));
                door.setType(set.getString(Constants.DOOR_TYPE));
                doorList.add(door);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return doorList;
    }

    /**
     * Gets list of plumb.
     *
     * @return the list of plumb
     */
    public List<Plumb> getListOfPlumb() {
        List<Plumb> plumbList = new ArrayList<>();
        ResultSet set = getRecords(Constants.DB_SELECT_ALL_PLUMB);
        try {
            while (set.next()) {
                Plumb plumb = new Plumb();
                plumb.setId(set.getLong(Constants.ITEM_ID));
                plumb.setPrice(set.getInt(Constants.ITEM_PRICE));
                plumb.setManufacturer(set.getString(Constants.ITEM_MANIFACTURER));
                plumb.setNumber(set.getInt(Constants.ITEM_NUMBER));
                plumb.setVendorCode(set.getString(Constants.ITEM_CODE));

                plumb.setDimensions(set.getInt(Constants.PLUMB_DIMENSIONS));
                plumb.setMountingType(set.getString(Constants.PLUMB_MOUNT_TYPE));
                plumb.setType(set.getString(Constants.PLUMB_TYPE));
                plumbList.add(plumb);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return plumbList;
    }

    /**
     * Gets cart list.
     *
     * @return the cart list
     */
    public List<Cart> getCartList() {
        List<Cart> cartList = new ArrayList<>();
        ResultSet set = getRecords(Constants.DB_SELECT_ALL_CART);
        try {
            while (set.next()) {
                Cart cart = new Cart();
                cart.setId(set.getLong(Constants.CART_ID));
                ResultSet setItemList = getFromCartItems(set.getLong(Constants.CART_ID));
                List<Item> itemList = new ArrayList<>();
                if (setItemList != null && setItemList.next()) {
                    itemList.add(getItemById(setItemList.getInt(Constants.CART_ITEM_ID)).get());
                }
                cart.setItemList(itemList);
                cart.setUser(getUserById(set.getLong(Constants.CART_USER_ID)).get());
                cartList.add(cart);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return cartList;
    }
}
