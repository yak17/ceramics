package ru.sfedu.ceramicshop.utils;

import ru.sfedu.ceramicshop.api.DataProvider;
import ru.sfedu.ceramicshop.models.*;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {
    public static void addRecord(DataProvider dataProvider) {

        List<Stoneware> stonewareList = new ArrayList<>();
        List<Door> doorList = new ArrayList<>();
        List<Plumb> plumbList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            User user = new User();
            user.setId(i);
            user.setName(DataForTests.name[i-1]);
            user.setPhone(DataForTests.phone[i-1]);
            user.setEmail(DataForTests.email[i-1]);
            dataProvider.createUser(user);
        }

        for (int i = 1; i <= 5; i++) {
            Stoneware stoneware = new Stoneware();
            stoneware.setId(i);
            stoneware.setPrice(DataForTests.price[i-1]);
            stoneware.setManufacturer(DataForTests.manufacturer[i-1]);
            stoneware.setNumber(DataForTests.number[i-1]);
            stoneware.setVendorCode(DataForTests.vendorCode[i-1]);
            stoneware.setCollection(DataForTests.collection[i-1]);
            stoneware.setSize(DataForTests.size[i-1]);
            stoneware.setThickness(DataForTests.thickness[i-1]);
            stoneware.setColor(DataForTests.color[i-1]);
            stonewareList.add(stoneware);
            dataProvider.createStoneware(stoneware);
        }
        for (int i = 1; i <= 5; i++) {
            Door door = new Door();
            door.setId(i);
            door.setPrice(DataForTests.price[i-1]);
            door.setManufacturer(DataForTests.manufacturer[i-1]);
            door.setNumber(DataForTests.number[i-1]);
            door.setVendorCode(DataForTests.vendorCode[i-1]);
            door.setType(DataForTests.type[i-1]);
            doorList.add(door);
            dataProvider.createDoor(door);
        }
        for (int i = 1; i <= 5; i++) {
            Plumb plumb = new Plumb();
            plumb.setId(i);
            plumb.setPrice(DataForTests.price[i-1]);
            plumb.setManufacturer(DataForTests.manufacturer[i-1]);
            plumb.setNumber(DataForTests.number[i-1]);
            plumb.setVendorCode(DataForTests.vendorCode[i-1]);
            plumb.setDimensions(DataForTests.dimensions[i-1]);
            plumb.setMountingType(DataForTests.mountingType[i-1]);
            plumb.setType(DataForTests.plumbType[i-1]);
            plumbList.add(plumb);
            dataProvider.createPlumb(plumb);
        }

        for (int i = 1; i < 4; i++) {
            Cart cart = new Cart();
            cart.setId(i);
            List<Item> itemList = new ArrayList<>();
            if(i%2 ==0){
                itemList.add(stonewareList.get(i));
            }
            if(i%3 == 0){
                itemList.add(doorList.get(i));
            }else{
                itemList.add(plumbList.get(i));
            }
            cart.setItemList(itemList);
            cart.setUser(getUser(dataProvider));
            dataProvider.createCart(cart);
        }

        for (int i = 0; i < 3; i++) {
            Catalog catalog = new Catalog();
            catalog.setId(i);
            List<Item> itemList = new ArrayList<>();
            itemList.addAll(stonewareList);
            itemList.addAll(doorList);
            itemList.addAll(plumbList);
            catalog.setItemList(itemList);
            dataProvider.createCatalog(catalog);
        }

        for (int i = 1; i < 2; i++) {
            Order order = new Order();
            order.setId(i);
            order.setCart(getCart(dataProvider));
            order.setNeedService(true);
            int servicePrice = dataProvider.isNeedService(order.getCart()).getData();
            int price = dataProvider.countOrderPrice(order.getCart().getId()).getData();
            order.setPrice( servicePrice + price);
            dataProvider.createOrder(order);
        }
    }


    public static User getUser(DataProvider dataProvider) {
        int max = 2;
        int min = 1;
        return dataProvider.getUserById(((int) ((Math.random() * ((max - min) + 1)) + min))).get();
    }

    public static Cart getCart(DataProvider dataProvider) {
        int max = 2;
        int min = 1;
        return dataProvider.getCartById(((int) ((Math.random() * ((max - min) + 1)) + min))).get();
    }
}
