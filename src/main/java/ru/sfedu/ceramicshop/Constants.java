package ru.sfedu.ceramicshop;


public class Constants {
    public static final String DEFAULT_ACTOR = "system";

    public static final String MONGO_CONNECT = "mongodb://localhost:27017";
    public static final String MONGO_DATABASE = "ceramicShop";
    public static final String MONGO_COLLECTION = "history";

    public static final String Item_CSV = "csv_item";
    public static final String Stoneware_CSV = "csv_stoneware";
    public static final String Door_CSV = "csv_door";
    public static final String Plumb_CSV = "csv_plumb";
    public static final String Catalog_CSV = "csv_catalog";
    public static final String Cart_CSV = "csv_cart";
    public static final String User_CSV = "csv_user";
    public static final String Order_CSV = "csv_order";

    public static final String FIELDS_DELIMITER = "@";
    public static final String OBJECT_DELIMITER = "\\^";


    public static final String STONEWARE_CONST = "stoneware";
    public static final String DOOR_CONST = "door";
    public static final String PLUMB_CONST = "plumb";


    public static final String Item_XML = "xml_item";
    public static final String Stoneware_XML = "xml_stoneware";
    public static final String Door_XML = "xml_door";
    public static final String Plumb_XML = "xml_plumb";
    public static final String Catalog_XML = "xml_catalog";
    public static final String Cart_XML = "xml_cart";
    public static final String User_XML = "xml_user";
    public static final String Order_XML = "xml_order";

    public static final String ID_NOT_EXISTS = "Object[%d] is not exists";

    public static final String DB_CONNECT = "db_url";
    public static final String DB_USER = "db_user";
    public static final String DB_PASS = "db_pass";

    public static final String STONEWARE = "stoneware";
    public static final String DOOR = "door";
    public static final String PLUMB = "plumb";

    public static final String SELECT_ALL = "SELECT * FROM %s WHERE Id=%d";
    public static final String CONFIG_PATH = "config.path";

    public static final String ITEM_ID = "id";
    public static final String ITEM_PRICE = "price";
    public static final String ITEM_MANIFACTURER = "manufacturer";
    public static final String ITEM_NUMBER = "number";
    public static final String ITEM_CODE = "vendorCode";

    public static final String STONEWARE_COLLECTION = "collection";
    public static final String STONEWARE_SIZE = "size";
    public static final String STONEWARE_THICKNESS = "thickness";
    public static final String STONEWARE_COLOR = "color";

    public static final String DOOR_TYPE = "type";

    public static final String PLUMB_DIMENSIONS = "dimensions";
    public static final String PLUMB_MOUNT_TYPE = "mountingType";
    public static final String PLUMB_TYPE = "type";

    public static final String CATALOG_ID = "id";
    public static final String CART_ID = "id";

    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONE = "phone";

    public static final String ORDER_ID = "id";
    public static final String ORDER_CART = "cart";
    public static final String ORDER_IS_SERVICE = "needService";
    public static final String ORDER_PRICE = "price";

    public static final String INSERT_ITEM = "INSERT INTO ITEM (price,manufacturer,number,vendorCode) VALUES (%d,'%s',%d,'%s')";
    public static final String ADD_ITEM = "Add item";

    public static final String UPDATE_ITEM = "UPDATE ITEM SET price = %d,manufacturer = '%s',number = %d,vendorCode = '%s' WHERE id=%d;";
    public static final String UPD_ITEM = "Update item";

    public static final String DELETE_ITEM = "DELETE FROM ITEM WHERE Id=%d;";
    public static final String DEL_ITEM = "Delete item";

    public static final String INSERT_STONEWARE = "INSERT INTO STONEWARE (price,manufacturer,number,vendorCode,collection,size,thickness,color) VALUES (%d,'%s',%d,'%s','%s',%d,%d,'%s')";
    public static final String ADD_STONEWARE = "Add stoneware";

    public static final String UPDATE_STONEWARE = "UPDATE STONEWARE SET price = %d,manufacturer = '%s',number = %d,vendorCode = '%s', collection = '%s',size =  %d,thickness =  %d, color = '%s' WHERE id=%d;";
    public static final String UPD_STONEWARE = "Upd stoneware";

    public static final String DELETE_STONEWARE = "DELETE FROM STONEWARE WHERE Id=%d;";
    public static final String DEL_STONEWARE = "Delete stoneware";

    public static final String INSERT_DOOR = "INSERT INTO DOOR (price,manufacturer,number,vendorCode,type) VALUES (%d,'%s',%d,'%s','%s')";
    public static final String ADD_DOOR = "Add door";

    public static final String UPDATE_DOOR = "UPDATE DOOR SET price = %d,manufacturer = '%s',number = %d,vendorCode = '%s',type = '%s' WHERE id=%d;";
    public static final String UPD_DOOR = "Update door";

    public static final String DELETE_DOOR = "DELETE FROM DOOR WHERE Id=%d;";
    public static final String DEL_DOOR = "Delete door";

    public static final String INSERT_PLUMB = "INSERT INTO PLUMB (price,manufacturer,number,vendorCode,dimensions,mountingType,type) VALUES (%d,'%s',%d,'%s',%d, '%s','%s')";
    public static final String ADD_PLUMB = "Add plumb";

    public static final String UPDATE_PLUMB = "UPDATE PLUMB SET price = %d,manufacturer = '%s',number = %d,vendorCode = '%s', dimensions = %d,mountingType='%s', type = '%s' WHERE id=%d;";
    public static final String UPD_PLUMB = "Update plumb";

    public static final String DELETE_PLUMB = "DELETE FROM PLUMB WHERE Id=%d;";
    public static final String DEL_PLUMB = "Delete plumb";

    public static final String INSERT_CATALOG = "INSERT INTO CATALOG () VALUES ();";
    public static final String ADD_CATALOG = "add catalog";

    public static final String INSERT_CATALOG_ITEM = "INSERT INTO \"CATALOG_ITEM\" (CATALOG_ID, ITEM_ID) VALUES (%d,%d)";
    public static final String ADD_CATALOG_ITEM = "add data item to catalog";

    public static final String GETCATALOGITEM = "SELECT * FROM CATALOG_ITEM WHERE CATALOG_ID=%d";
    public static final String CATALOGITEMID = "ITEM_ID";

    public static final String DELETE_CATALOG = "DELETE FROM CATALOG WHERE Id=%d;";
    public static final String DEL_CATALOG = "Delete catalog";

    public static final String DELETE_CATALOG_ITEM = "DELETE FROM CATALOG_ITEM WHERE CATALOG_ID=%d;";
    public static final String DEL_CATALOG_ITEM = "Delete cascade catalog";

    public static final String INSERT_CART = "INSERT INTO CART (user) VALUES (%d)";
    public static final String ADD_CART = "Add cart";

    public static final String INSERT_CART_ITEM = "INSERT INTO \"CART_ITEM\" (CART_ID, ITEM_ID) VALUES (%d,%d)";
    public static final String ADD_CART_ITEM = "add data item to cart";

    public static final String GETCARTITEM = "SELECT * FROM CART_ITEM WHERE CART_ID=%d";
    public static final String CART_ITEM_ID = "ITEM_ID";
    public static final String CART_USER_ID = "user";

    public static final String UPDATE_CART = "UPDATE CART SET user = %d WHERE id = %d";
    public static final String UPD_CART = "update cart";

    public static final String DELETE_CART = "DELETE FROM CART WHERE Id=%d;";
    public static final String DEL_CART = "delete cart";

    public static final String DELETE_CART_ITEM = "DELETE FROM CART_ITEM WHERE CART_ID=%d";
    public static final String DEL_CART_ITEM = "cascade delete cart item";

    public static final String INSERT_USER = "INSERT INTO USER (name,email,phone) VALUES ('%s','%s','%s');";
    public static final String ADD_USER = "add user";

    public static final String UPDATE_USER = "UPDATE USER SET name = '%s',email = '%s',phone = '%s' WHERE id=%d;";
    public static final String UPD_USER = "Update user";

    public static final String DELETE_USER = "DELETE FROM USER WHERE Id=%d;";
    public static final String DEL_USER = "Delete user";

    public static final String INSERT_ORDER = "INSERT INTO ORDERS (cart,needService,price) VALUES (%d,%b,%d);";
    public static final String ADD_ORDER = "add order";

    public static final String UPDATE_ORDER = "UPDATE ORDERS SET cart = %d,needService = %b,price = %d WHERE id=%d;";
    public static final String UPD_ORDER = "upd order";

    public static final String DELETE_ORDER = "DELETE FROM ORDERS WHERE id=%d;";
    public static final String DEL_ORDER = "delete order";

    public static final String ITEM_CREATE = "create table ITEM";
    public static final String CREATE_ITEM ="create table ITEM \n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    PRICE            INTEGER,\n" +
            "    MANUFACTURER     VARCHAR(40),\n" +
            "    NUMBER           INTEGER,\n" +
            "    VENDORCODE       VARCHAR(40)\n"+
            ");";

    public static final String STONEWARE_CREATE = "create table STONEWARE";
    public static final String CREATE_STONEWARE ="create table STONEWARE\n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    PRICE            INTEGER,\n" +
            "    MANUFACTURER     VARCHAR(40),\n" +
            "    NUMBER           INTEGER,\n" +
            "    VENDORCODE       VARCHAR(40), \n"+
            "    COLLECTION       VARCHAR(40), \n"+
            "    SIZE             INTEGER,\n" +
            "    THICKNESS        INTEGER,\n" +
            "    COLOR            VARCHAR(40) \n"+
            ");";

    public static final String DOOR_CREATE = "create table DOOR";
    public static final String CREATE_DOOR ="create table DOOR \n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    PRICE            INTEGER,\n" +
            "    MANUFACTURER     VARCHAR(40),\n" +
            "    NUMBER           INTEGER,\n" +
            "    VENDORCODE       VARCHAR(40), \n"+
            "    TYPE             VARCHAR(40) \n"+
            ");";

    public static final String PLUMB_CREATE = "create table PLUMB";
    public static final String CREATE_PLUMB ="create table PLUMB \n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    PRICE            INTEGER,\n" +
            "    MANUFACTURER     VARCHAR(40),\n" +
            "    NUMBER           INTEGER,\n" +
            "    VENDORCODE       VARCHAR(40),\n"+
            "    DIMENSIONS       INTEGER,\n" +
            "    MOUNTINGTYPE     VARCHAR(40), \n"+
            "    TYPE             VARCHAR(40) \n"+
            ");";

    public static final String USER_CREATE = "create table USER";
    public static final String CREATE_USER ="create table USER \n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    NAME            VARCHAR(40),\n" +
            "    EMAIL           VARCHAR(40),\n" +
            "    PHONE           VARCHAR(40)\n" +
            ");";

    public static final String CATALOG_CREATE = "create table CATALOG";
    public static final String CREATE_CATALOG ="create table CATALOG \n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key\n" +
            ");";

    public static final String CART_CREATE = "create table CART";
    public static final String CREATE_CART ="create table CART \n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    USER            INTEGER\n" +
            ");";

    public static final String ORDERS_CREATE = "create table ORDERS";
    public static final String CREATE_ORDERS = "create table ORDERS \n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    CART            INTEGER, \n" +
            "    NEEDSERVICE     BOOLEAN,\n" +
            "    PRICE           INTEGER\n" +
            ");";

    public static final String CART_ITEM_CREATE = "create table CART_ITEM";
    public static final String CART_ITEM = "create table CART_ITEM \n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    CART_ID         INTEGER, \n" +
            "    ITEM_ID         INTEGER\n" +
            ");";

    public static final String CATALOG_ITEM_CREATE = "create table CATALOG_ITEM";
    public static final String CATALOG_ITEM = "create table CATALOG_ITEM \n" +
            "(\n" +
            "    ID BIGINT auto_increment primary key,\n" +
            "    CATALOG_ID         INTEGER, \n" +
            "    ITEM_ID         INTEGER\n" +
            ");";
    public static final String DROP ="DROP TABLE IF EXISTS \"%s\" CASCADE";
    public static final String DROP_NAME="Drop Table";

    public static final String DB_SELECT_ALL_STONEWARE = "SELECT * FROM STONEWARE";
    public static final String DB_SELECT_ALL_DOOR = "SELECT * FROM DOOR";
    public static final String DB_SELECT_ALL_PLUMB = "SELECT * FROM PLUMB";
    public static final String DB_SELECT_ALL_CART = "SELECT * FROM CART";


    public static final String CSV_PATH ="CSV_PATH";
    public static final String FILE_EXTENSION_CSV = "FILE_EXTENSION_CSV";

    public static final String FILE_EXTENSION_XML = "FILE_EXTENSION_XML";
    public static final String XML_PATH ="XML_PATH";

    public static final String Main_Csv="csv";
    public static final String Main_Xml="xml";
    public static final String Main_Jdbc="jdbc";
    public static final String end  = "end";
}
