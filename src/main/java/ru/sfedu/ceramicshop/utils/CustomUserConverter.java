package ru.sfedu.ceramicshop.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.ceramicshop.Constants;
import ru.sfedu.ceramicshop.models.User;

public class CustomUserConverter extends AbstractBeanField {

    private final String fieldDelimiter = Constants.FIELDS_DELIMITER;
    private static final Logger log = LogManager.getLogger(CustomUserConverter.class);


    @Override
    public Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        User user = new User();
        try {
            String[] data = s.split(fieldDelimiter);
            user.setId(Long.parseLong(data[0]));
            user.setName(data[1]);
            user.setEmail(data[2]);
            user.setPhone(data[3]);
        } catch (NullPointerException e){
            log.error(e);
        } finally {
            return user;
        }
    }

    @Override
    public String convertToWrite(Object user){
        User customUser = (User) user;
        if (user==null){
            return null;
        } else {
            return String.format("%d"
                            + fieldDelimiter
                            + "%s"
                            + fieldDelimiter
                            + "%s"
                            + fieldDelimiter
                            + "%s",
                    customUser.getId(),
                    customUser.getName(),
                    customUser.getEmail(),
                    customUser.getPhone());
        }
    }
}
