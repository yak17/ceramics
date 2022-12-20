package ru.sfedu.ceramicshop.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.ceramicshop.Constants;
import ru.sfedu.ceramicshop.Result;
import ru.sfedu.ceramicshop.models.*;
import ru.sfedu.ceramicshop.models.enums.Outcomes;
import ru.sfedu.ceramicshop.utils.ConfigurationUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static ru.sfedu.ceramicshop.models.enums.Outcomes.Fail;
import static ru.sfedu.ceramicshop.models.enums.Outcomes.Success;
import static ru.sfedu.ceramicshop.utils.HistoryUtil.saveToLog;

/**
 * The type Data provider csv.
 */
public class DataProviderCsv implements DataProvider {

    private static final Logger log = LogManager.getLogger(DataProviderCsv.class);


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
            final String method = currentThread().getStackTrace()[1].getMethodName();
            return switch (type.toLowerCase()) {
                case Constants.STONEWARE -> new Result(Success, csvToBean(Stoneware.class, Constants.Stoneware_CSV, method));
                case Constants.DOOR -> new Result(Success, csvToBean(Door.class, Constants.Door_CSV, method));
                case Constants.PLUMB -> new Result(Success, csvToBean(Plumb.class, Constants.Plumb_CSV, method));
                default -> new Result<>(Fail);
            };
        } catch (Exception e) {
            log.error(e);
            return new Result<>(Fail);
        }
    }

    @Override
    public Result fillShopCart(String vendorCode, long userId) {
        try {
            final String method = currentThread().getStackTrace()[1].getMethodName();
            List<Stoneware> stonewares = csvToBean(Stoneware.class, Constants.Stoneware_CSV, method);
            List<Door> doors = csvToBean(Door.class, Constants.Door_CSV, method);
            List<Plumb> plumbs = csvToBean(Plumb.class, Constants.Plumb_CSV, method);
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
    public Result<Boolean> addStoneware(long userId, Stoneware stoneware) {
        try {
            final String method = currentThread().getStackTrace()[1].getMethodName();
            List<Cart> carts = csvToBean(Cart.class, Constants.Cart_CSV, method);
            Optional<Cart> optCart = carts.stream()
                    .filter(el -> el.getUser().getId() == userId)
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
    public Result<Boolean> addDoor(long userId, Door door) {
        try {
            final String method = currentThread().getStackTrace()[1].getMethodName();
            List<Cart> carts = csvToBean(Cart.class, Constants.Cart_CSV, method);
            Optional<Cart> optionalDoor = carts.stream()
                    .filter(el -> el.getUser().getId() == userId)
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
    public Result<Boolean> addPlumbing(long userId, Plumb plumb) {
        try {
            final String method = currentThread().getStackTrace()[1].getMethodName();
            List<Cart> carts = csvToBean(Cart.class, Constants.Cart_CSV, method);
            Optional<Cart> optionalPlumbing = carts.stream()
                    .filter(el -> el.getUser().getId() == userId)
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
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Item> itemList = csvToBean(Item.class, Constants.Item_CSV, method);
        if (itemList.stream().anyMatch(o -> o.getId() == item.getId())) {
            return new Result<>(Fail, item);
        }
        itemList.add(item);
        if (beanToCsv(itemList, Constants.Item_CSV, method) == Fail) {
            return new Result<>(Fail, item);
        }
        return new Result<>(Success, item);
    }

    @Override
    public Optional<Item> getItemById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Item> objects = csvToBean(Item.class, Constants.Item_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Result<Item> updateItem(Item item) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Item> objects = csvToBean(Item.class, Constants.Item_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == item.getId())) {
            return new Result<>(Fail, item, format(Constants.ID_NOT_EXISTS, item.getId()));
        }
        objects.removeIf(o -> o.getId() == item.getId());
        objects.add(item);
        if (beanToCsv(objects, Constants.Item_CSV, method) == Fail) {
            return new Result<>(Fail, item);
        }
        return new Result<>(Success, item);
    }

    @Override
    public Result<Boolean> deleteItem(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Item> objects = csvToBean(Item.class, Constants.Item_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, Constants.Item_CSV, method);
        return new Result<>(Success);
    }

    @Override
    public Result<Stoneware> createStoneware(Stoneware stoneware) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Stoneware> doorList = csvToBean(Stoneware.class, Constants.Stoneware_CSV, method);
        if (doorList.stream().anyMatch(o -> o.getId() == stoneware.getId())) {
            return new Result<>(Fail, stoneware);
        }
        doorList.add(stoneware);
        if (beanToCsv(doorList, Constants.Stoneware_CSV, method) == Fail) {
            return new Result<>(Fail, stoneware);
        }
        return new Result<>(Success, stoneware);
    }

    @Override
    public Optional<Stoneware> getStonewareById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Stoneware> objects = csvToBean(Stoneware.class, Constants.Stoneware_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Result<Stoneware> updateStoneware(Stoneware stoneware) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Stoneware> objects = csvToBean(Stoneware.class, Constants.Stoneware_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == stoneware.getId())) {
            return new Result<>(Fail, stoneware, format(Constants.ID_NOT_EXISTS, stoneware.getId()));
        }
        objects.removeIf(o -> o.getId() == stoneware.getId());
        objects.add(stoneware);
        if (beanToCsv(objects, Constants.Stoneware_CSV, method) == Fail) {
            return new Result<>(Fail, stoneware);
        }
        return new Result<>(Success, stoneware);
    }

    @Override
    public Result<Boolean> deleteStoneware(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Stoneware> objects = csvToBean(Stoneware.class, Constants.Stoneware_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, Constants.Stoneware_CSV, method);
        return new Result<>(Success);
    }

    @Override
    public Result<Door> createDoor(Door door) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Door> doorList = csvToBean(Door.class, Constants.Door_CSV, method);
        if (doorList.stream().anyMatch(o -> o.getId() == door.getId())) {
            return new Result<>(Fail, door);
        }
        doorList.add(door);
        if (beanToCsv(doorList, Constants.Door_CSV, method) == Fail) {
            return new Result<>(Fail, door);
        }
        return new Result<>(Success, door);
    }

    @Override
    public Optional<Door> getDoorById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Door> objects = csvToBean(Door.class, Constants.Door_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Result<Door> updateDoor(Door door) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Door> objects = csvToBean(Door.class, Constants.Door_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == door.getId())) {
            return new Result<>(Fail, door, format(Constants.ID_NOT_EXISTS, door.getId()));
        }
        objects.removeIf(o -> o.getId() == door.getId());
        objects.add(door);
        if (beanToCsv(objects, Constants.Door_CSV, method) == Fail) {
            return new Result<>(Fail, door);
        }
        return new Result<>(Success, door);
    }

    @Override
    public Result<Boolean> deleteDoor(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Door> objects = csvToBean(Door.class, Constants.Door_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, Constants.Door_CSV, method);
        return new Result<>(Success);
    }

    @Override
    public Result<Plumb> createPlumb(Plumb plumb) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Plumb> cartList = csvToBean(Plumb.class, Constants.Plumb_CSV, method);
        if (cartList.stream().anyMatch(o -> o.getId() == plumb.getId())) {
            return new Result<>(Fail, plumb);
        }
        cartList.add(plumb);
        if (beanToCsv(cartList, Constants.Plumb_CSV, method) == Fail) {
            return new Result<>(Fail, plumb);
        }
        return new Result<>(Success, plumb);
    }

    @Override
    public Optional<Plumb> getPlumbById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Plumb> objects = csvToBean(Plumb.class, Constants.Plumb_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Result<Plumb> updatePlumb(Plumb plumb) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Plumb> objects = csvToBean(Plumb.class, Constants.Plumb_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == plumb.getId())) {
            return new Result<>(Fail, plumb, format(Constants.ID_NOT_EXISTS, plumb.getId()));
        }
        objects.removeIf(o -> o.getId() == plumb.getId());
        objects.add(plumb);
        if (beanToCsv(objects, Constants.Plumb_CSV, method) == Fail) {
            return new Result<>(Fail, plumb);
        }
        return new Result<>(Success, plumb);
    }

    @Override
    public Result<Boolean> deletePlumb(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Plumb> objects = csvToBean(Plumb.class, Constants.Plumb_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, Constants.Plumb_CSV, method);
        return new Result<>(Success);
    }

    @Override
    public Result<Catalog> createCatalog(Catalog catalog) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Catalog> cartList = csvToBean(Catalog.class, Constants.Catalog_CSV, method);
        if (cartList.stream().anyMatch(o -> o.getId() == catalog.getId())) {
            return new Result<>(Fail, catalog);
        }
        cartList.add(catalog);
        if (beanToCsv(cartList, Constants.Catalog_CSV, method) == Fail) {
            return new Result<>(Fail, catalog);
        }
        return new Result<>(Success, catalog);
    }

    @Override
    public Optional<Catalog> getCatalogById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Catalog> objects = csvToBean(Catalog.class, Constants.Catalog_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Result<Catalog> updateCatalog(Catalog catalog) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Catalog> objects = csvToBean(Catalog.class, Constants.Catalog_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == catalog.getId())) {
            return new Result<>(Fail, catalog, format(Constants.ID_NOT_EXISTS, catalog.getId()));
        }
        objects.removeIf(o -> o.getId() == catalog.getId());
        objects.add(catalog);
        if (beanToCsv(objects, Constants.Catalog_CSV, method) == Fail) {
            return new Result<>(Fail, catalog);
        }
        return new Result<>(Success, catalog);
    }

    @Override
    public Result<Boolean> deleteCatalog(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Catalog> objects = csvToBean(Catalog.class, Constants.Catalog_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, Constants.Catalog_CSV, method);
        return new Result<>(Success);
    }

    @Override
    public Result<Cart> createCart(Cart cart) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Cart> cartList = csvToBean(Cart.class, Constants.Cart_CSV, method);
        if (cartList.stream().anyMatch(o -> o.getId() == cart.getId())) {
            return new Result<>(Fail, cart);
        }
        cartList.add(cart);
        if (beanToCsv(cartList, Constants.Cart_CSV, method) == Fail) {
            return new Result<>(Fail, cart);
        }
        return new Result<>(Success, cart);
    }

    @Override
    public Optional<Cart> getCartById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Cart> objects = csvToBean(Cart.class, Constants.Cart_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Result<Cart> updateCart(Cart cart) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Cart> objects = csvToBean(Cart.class, Constants.Cart_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == cart.getId())) {
            return new Result<>(Fail, cart, format(Constants.ID_NOT_EXISTS, cart.getId()));
        }
        objects.removeIf(o -> o.getId() == cart.getId());
        objects.add(cart);
        if (beanToCsv(objects, Constants.Cart_CSV, method) == Fail) {
            return new Result<>(Fail, cart);
        }
        return new Result<>(Success, cart);
    }

    @Override
    public Result<Boolean> deleteCart(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Cart> objects = csvToBean(Cart.class, Constants.Cart_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, Constants.Cart_CSV, method);
        return new Result<>(Success);
    }

    @Override
    public Result<User> createUser(User user) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<User> userList = csvToBean(User.class, Constants.User_CSV, method);
        if (userList.stream().anyMatch(o -> o.getId() == user.getId())) {
            return new Result<>(Fail, user);
        }
        userList.add(user);
        if (beanToCsv(userList, Constants.User_CSV, method) == Fail) {
            return new Result<>(Fail, user);
        }
        return new Result<>(Success, user);
    }

    @Override
    public Optional<User> getUserById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<User> objects = csvToBean(User.class, Constants.User_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Result<User> updateUser(User user) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<User> objects = csvToBean(User.class, Constants.User_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == user.getId())) {
            return new Result<>(Fail, user, format(Constants.ID_NOT_EXISTS, user.getId()));
        }
        objects.removeIf(o -> o.getId() == user.getId());
        objects.add(user);
        if (beanToCsv(objects, Constants.User_CSV, method) == Fail) {
            return new Result<>(Fail, user);
        }
        return new Result<>(Success, user);
    }

    @Override
    public Result<Boolean> deleteUser(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<User> objects = csvToBean(User.class, Constants.User_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, Constants.User_CSV, method);
        return new Result<>(Success);
    }

    @Override
    public Result<Order> createOrder(Order order) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Order> orderList = csvToBean(Order.class, Constants.Order_CSV, method);
        if (orderList.stream().anyMatch(o -> o.getId() == order.getId())) {
            return new Result<>(Fail, order);
        }
        orderList.add(order);
        if (beanToCsv(orderList, Constants.Order_CSV, method) == Fail) {
            return new Result<>(Fail, order);
        }
        return new Result<>(Success, order);
    }

    @Override
    public Optional<Order> getOrderById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Order> objects = csvToBean(Order.class, Constants.Order_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Result<Order> updateOrder(Order order) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Order> objects = csvToBean(Order.class, Constants.Order_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == order.getId())) {
            return new Result<>(Fail, order, format(Constants.ID_NOT_EXISTS, order.getId()));
        }
        objects.removeIf(o -> o.getId() == order.getId());
        objects.add(order);
        if (beanToCsv(objects, Constants.Order_CSV, method) == Fail) {
            return new Result<>(Fail, order);
        }
        return new Result<>(Success, order);
    }

    @Override
    public Result<Boolean> deleteOrder(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Order> objects = csvToBean(Order.class, Constants.Order_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, Constants.Order_CSV, method);
        return new Result<>(Success);
    }

    private static HistoryContent createHistoryContent(String method, Object object, Outcomes outcomes) {
        return new HistoryContent(DataProviderCsv.class.getSimpleName(), new Date(), Constants.DEFAULT_ACTOR, method, object, outcomes);
    }

    private <T> Outcomes beanToCsv(List<T> ts, String key, String method) {
        Outcomes result;
        try {
            FileWriter fileWriter = new FileWriter(ConfigurationUtil.getConfigurationEntry(key), false);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            beanToCsv.write(ts);
            csvWriter.close();
            fileWriter.close();
            result = Success;
        } catch (Exception exception) {
            log.error(exception);
            result = Fail;
        }
        saveToLog(createHistoryContent(method, ts, result));
        return result;
    }

    /**
     * Csv to bean list.
     *
     * @param <T>    the type parameter
     * @param cls    the cls
     * @param key    the key
     * @param method the method
     * @return the list
     */
    public static <T> List<T> csvToBean(Class<T> cls, String key, String method) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(ConfigurationUtil.getConfigurationEntry(key)));
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader).withType(cls).build();
            List<T> querySet = csvToBean.parse();
            csvReader.close();
            saveToLog(createHistoryContent(method, querySet, Success));
            return querySet;
        } catch (Exception exception) {
            log.error(exception);
        }
        saveToLog(createHistoryContent(method, null, Fail));
        return new ArrayList<>();
    }

    /**
     * Get class name string.
     *
     * @param <T> the type parameter
     * @param cl  the cl
     * @return the string
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
     * Delete record.
     *
     * @param <T> the type parameter
     * @param cl  the cl
     */
    public <T> void deleteRecord(Class<T> cl) {
        String path = getPath(cl);
        File file = new File(path);
        file.delete();
        new Result<>(Success);
    }

    /**
     * Delete all record.
     */
    public void deleteAllRecord() {
        deleteRecord(Item.class);
        deleteRecord(Stoneware.class);
        deleteRecord(Door.class);
        deleteRecord(Plumb.class);
        deleteRecord(Catalog.class);
        deleteRecord(Cart.class);
        deleteRecord(User.class);
        deleteRecord(Order.class);
    }

    /**
     * @param cl
     * @return
     */
    private String getPath(Class<?> cl) {
        try {
            String PATH = ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH);
            return PATH + cl.getSimpleName().toLowerCase() + ConfigurationUtil.
                    getConfigurationEntry(Constants.FILE_EXTENSION_CSV);
        } catch (IOException e) {
            log.error(e);
            return null;
        }
    }
}