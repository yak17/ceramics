package ru.sfedu.ceramicshop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.ceramicshop.api.DataProvider;
import ru.sfedu.ceramicshop.api.DataProviderCsv;
import ru.sfedu.ceramicshop.api.DataProviderJdbc;
import ru.sfedu.ceramicshop.api.DataProviderXML;
import ru.sfedu.ceramicshop.models.enums.Command;
import ru.sfedu.ceramicshop.utils.ConfigurationUtil;
import ru.sfedu.ceramicshop.utils.DataGenerator;

import java.io.IOException;

public class Main {

    public static final Logger log = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        try {
            ConfigurationUtil.getConfigurationEntry(Constants.CONFIG_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataProvider dataProvider;
        switch (args[0].toLowerCase()) {
            case Constants.Main_Csv -> {
                DataProviderCsv dpc = new DataProviderCsv();
                dataProvider = dpc;
                dpc.deleteAllRecord();
                DataGenerator.addRecord(dpc);
            }
            case Constants.Main_Xml -> {
                DataProviderXML dpx = new DataProviderXML();
                dataProvider = dpx;
                dpx.deleteAllRecord();
                DataGenerator.addRecord(dpx);
            }
            case Constants.Main_Jdbc -> {
                DataProviderJdbc dpj = new DataProviderJdbc();
                dataProvider = dpj;
                dpj.deleteAllRecord();
                dpj.createTables();
                DataGenerator.addRecord(dpj);
            }
            default -> {return;}
        }
        System.out.println(getAnswer(dataProvider, args));
    }

    public static String getAnswer(DataProvider dataProvider, String[] args) {
        try {
            switch (Command.valueOf(args[1].toUpperCase())) {
                case MAKEORDER:
                    return String.valueOf(dataProvider.makeOrder(Long.parseLong(args[2]),Boolean.parseBoolean(args[3])).getData());
                case COUNTORDERPRICE:
                    return String.valueOf(dataProvider.countOrderPrice(Long.parseLong(args[2])).getData());
                case SHOWCATALOGBYTYPE:
                    return String.valueOf(dataProvider.showCatalogByType(args[2].toLowerCase()).getData());
                case FILLSHOPPINGCART:
                    dataProvider.fillShopCart(args[2],Long.parseLong(args[3])).getData();
                    return String.valueOf(dataProvider.getCartById(Long.parseLong(args[3])).get());
                default:
                    log.error(Command.NONE);
            }
        }catch (Exception e){
            log.error(e);
        }
        return Constants.end;
    }
}
