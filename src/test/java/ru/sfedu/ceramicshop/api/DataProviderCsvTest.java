package ru.sfedu.ceramicshop.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.ceramicshop.models.Stoneware;
import ru.sfedu.ceramicshop.models.enums.Outcomes;
import ru.sfedu.ceramicshop.utils.DataGenerator;

class DataProviderCsvTest {
    public static DataProvider instance = new DataProviderCsv();

    @BeforeAll
    static void setData() {
        DataGenerator.addRecord(instance);
    }

    @Test
    void makeOrderSuccess() {
        Assertions.assertEquals(Outcomes.Success,instance.makeOrder(1,true).getStatus());
    }

    @Test
    void makeOrderFail() {
        Assertions.assertNotEquals(1000,instance.makeOrder(1,true).getData().getPrice());
    }

    @Test
    void countOrderPriceSuccess() {
        Assertions.assertEquals(Outcomes.Success,instance.countOrderPrice(2L).getStatus());
    }

    @Test
    void countOrderPriceFail() {
        Assertions.assertNotEquals(0,instance.countOrderPrice(2L).getData());
    }

    @Test
    void showCatalogByTypeSuccess() {
        Assertions.assertEquals(Stoneware.class,instance.showCatalogByType("stoneware").getData().get(0).getClass());
    }

    @Test
    void showCatalogByTypeFail() {
        Assertions.assertNotEquals(Stoneware.class,instance.showCatalogByType("door").getData().get(0).getVendorCode());
    }

    @Test
    void fillShopCartSuccess() {
        Assertions.assertEquals(Outcomes.Success,instance.fillShopCart("zraOirFB5b34In5T",1).getStatus());
    }

    @Test
    void fillShopCartFail() {
        Assertions.assertEquals(Outcomes.Fail,instance.fillShopCart("zraOirFB5b34In5T",11).getStatus());
    }

}