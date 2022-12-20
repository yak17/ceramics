package ru.sfedu.ceramicshop.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.ceramicshop.Constants;
import ru.sfedu.ceramicshop.models.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomItemConverterList extends AbstractBeanField {

    private final String fieldDelimiter = Constants.FIELDS_DELIMITER;
    private final String objectDelimiter = Constants.OBJECT_DELIMITER;

    private static final Logger log = LogManager.getLogger(CustomItemConverterList.class);

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        List<Item> itemList=new ArrayList<>();
        List<String> stringList= Arrays.asList(s.split(objectDelimiter));
        try {
            stringList.stream().forEach(x-> {
                Item item = new Item();
                String[] data = s.replace(",",".").split(fieldDelimiter);
                item.setId(Long.parseLong(data[0]));
                item.setPrice(Integer.parseInt(data[1]));
                item.setManufacturer(data[2]);
                item.setNumber(Integer.parseInt(data[3]));
                item.setVendorCode(data[4]);
                itemList.add(item);
            });
        } catch (NumberFormatException e){
            log.error(e);
        } finally {
            return itemList;
        }
    }

    @Override
    public String convertToWrite(Object value){
        List<Item> itemList=(List<Item>) value;
        if (itemList==null){
            return objectDelimiter;
        } else {
            List<String> stringList= itemList.stream()
                    .map(x-> String.format("%d"
                                    +fieldDelimiter
                                    + "%d"
                                    +fieldDelimiter
                                    + "%s"
                                    +fieldDelimiter
                                    + "%d"
                                    +fieldDelimiter
                                    + "%s",
                            x.getId(),
                            x.getPrice(),
                            x.getManufacturer(),
                            x.getNumber(),
                            x.getVendorCode()))
                    .collect(Collectors.toList()
                    );
            return String.join(objectDelimiter,stringList);
        }
    }
}
