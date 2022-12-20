package ru.sfedu.ceramicshop.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.ceramicshop.Constants;
import ru.sfedu.ceramicshop.models.Cart;
import ru.sfedu.ceramicshop.models.Item;
import ru.sfedu.ceramicshop.models.User;

import java.util.List;

public class CustomCartConverter extends AbstractBeanField {

    private final String fieldDelimiter = Constants.FIELDS_DELIMITER;
    private static final Logger log = LogManager.getLogger(CustomCartConverter.class);
    private final CustomItemConverterList customItemConverterList=new CustomItemConverterList();
    private final CustomUserConverter customUserConverter  = new CustomUserConverter();
    @Override
    public Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        Cart cart = new Cart();
        try {
            String[] data = s.split(fieldDelimiter);
            cart.setId(Long.parseLong(data[0]));
            cart.setItemList((List<Item>) customItemConverterList.convert(data[1]));
            cart.setUser((User) customUserConverter.convert(data[2]));
        } catch (NullPointerException e){
            log.error(e);
        } finally {
            return cart;
        }
    }

    @Override
    public String convertToWrite(Object cart){
        Cart customCart = (Cart) cart;
        if (cart==null){
            return null;
        } else {
            return String.format("%d"
                            + fieldDelimiter
                            + "%s"
                            + fieldDelimiter
                            + "%s",
                    customCart.getId(),
                    customItemConverterList.convertToWrite(customCart.getItemList()),
                    customUserConverter.convertToWrite(customCart.getUser()));
        }
    }
}
