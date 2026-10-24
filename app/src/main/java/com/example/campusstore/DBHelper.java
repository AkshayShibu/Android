package com.example.campusstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CampusStore.db";
    private static final int DATABASE_VERSION = 4;


    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Create database tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Products table
        db.execSQL(
                "CREATE TABLE products (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT, " +
                        "description TEXT, " +
                        "price REAL, " +
                        "image_res INTEGER, " +
                        "category TEXT, " +
                        "stock INTEGER)"
        );


        // Cart table
        db.execSQL(
                "CREATE TABLE cart (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "product_id INTEGER, " +
                        "quantity INTEGER)"
        );


        // Orders table
        db.execSQL(
                "CREATE TABLE orders (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "order_date TEXT, " +
                        "total_amount REAL, " +
                        "customer_name TEXT)"
        );


        // Order items table
        db.execSQL(
                "CREATE TABLE order_items (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "order_id INTEGER, " +
                        "product_id INTEGER, " +
                        "quantity INTEGER, " +
                        "price REAL)"
        );


        // Users table
        db.execSQL(
                "CREATE TABLE users (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT UNIQUE, " +
                        "password TEXT)"
        );


        // Add sample products
        addSampleProducts(db);
    }


    // Upgrade database
    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        // Version 2 - Users table
        if (oldVersion < 2) {

            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name TEXT UNIQUE, " +
                            "password TEXT)"
            );
        }


        // Version 4 - Customer name in orders
        if (oldVersion < 4) {

            Cursor cursor = db.rawQuery(
                    "PRAGMA table_info(orders)",
                    null
            );

            boolean hasCustomerName = false;

            while (cursor.moveToNext()) {

                String columnName =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("name")
                        );

                if (columnName.equals("customer_name")) {
                    hasCustomerName = true;
                    break;
                }
            }

            cursor.close();


            if (!hasCustomerName) {

                db.execSQL(
                        "ALTER TABLE orders " +
                                "ADD COLUMN customer_name TEXT"
                );
            }
        }
    }


    // ---------------------------------------------------------
    // SAMPLE PRODUCTS
    // ---------------------------------------------------------

    private void addSampleProducts(SQLiteDatabase db) {

        ContentValues values = new ContentValues();

        // -------------------------------------------------
        // NOTEBOOK
        // -------------------------------------------------

        values.put("name", "Notebook");
        values.put(
                "description",
                "College Notebook"
        );
        values.put("price", 50);
        values.put(
                "image_res",
                R.drawable.note1
        );
        values.put(
                "category",
                "Stationery"
        );
        values.put("stock", 10);

        db.insert(
                "products",
                null,
                values
        );


        // -------------------------------------------------
        // SET OF NOTEBOOKS
        // -------------------------------------------------

        values.clear();

        values.put(
                "name",
                "Set of Notebooks"
        );

        values.put(
                "description",
                "Set of College Notebooks"
        );

        values.put("price", 150);

        values.put(
                "image_res",
                R.drawable.notebook1
        );

        values.put(
                "category",
                "Stationery"
        );

        values.put("stock", 10);

        db.insert(
                "products",
                null,
                values
        );


        // -------------------------------------------------
        // PEN
        // -------------------------------------------------

        values.clear();

        values.put(
                "name",
                "Pen"
        );

        values.put(
                "description",
                "Blue Ball Pen"
        );

        values.put("price", 20);

        values.put(
                "image_res",
                R.drawable.pen
        );

        values.put(
                "category",
                "Stationery"
        );

        values.put("stock", 20);

        db.insert(
                "products",
                null,
                values
        );


        // -------------------------------------------------
        // A4 PAPERS
        // -------------------------------------------------

        values.clear();

        values.put(
                "name",
                "A4 Papers"
        );

        values.put(
                "description",
                "A4 Size Printing Papers"
        );

        values.put("price", 100);

        values.put(
                "image_res",
                R.drawable.a4
        );

        values.put(
                "category",
                "Stationery"
        );

        values.put("stock", 10);

        db.insert(
                "products",
                null,
                values
        );


        // -------------------------------------------------
        // FILES
        // -------------------------------------------------

        values.clear();

        values.put(
                "name",
                "Files"
        );

        values.put(
                "description",
                "Strip Files for Documents"
        );

        values.put("price", 30);

        values.put(
                "image_res",
                R.drawable.files
        );

        values.put(
                "category",
                "Stationery"
        );

        values.put("stock", 20);

        db.insert(
                "products",
                null,
                values
        );


        // -------------------------------------------------
        // PYTHON TEXTBOOK
        // -------------------------------------------------

        values.clear();

        values.put(
                "name",
                "Python Textbook"
        );

        values.put(
                "description",
                "Python Programming Textbook"
        );

        values.put("price", 300);

        values.put(
                "image_res",
                R.drawable.pythontext
        );

        values.put(
                "category",
                "Books"
        );

        values.put("stock", 10);

        db.insert(
                "products",
                null,
                values
        );


        // -------------------------------------------------
        // COLLEGE SHIRT
        // -------------------------------------------------

        values.clear();

        values.put(
                "name",
                "College Shirt"
        );

        values.put(
                "description",
                "College Shirt"
        );

        values.put("price", 500);

        values.put(
                "image_res",
                R.drawable.shirt
        );

        values.put(
                "category",
                "Clothing"
        );

        values.put("stock", 10);

        db.insert(
                "products",
                null,
                values
        );
    }


    // ---------------------------------------------------------
    // ADD PRODUCT
    // ---------------------------------------------------------

    public void addProduct(
            String name,
            String description,
            double price,
            String category,
            int stock) {

        SQLiteDatabase db =
                getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put("name", name);
        values.put("description", description);
        values.put("price", price);

        // Choose image according to category
        int imageRes = 0;

        if (category.equalsIgnoreCase("Stationery")) {

            imageRes = R.drawable.pen;

        } else if (category.equalsIgnoreCase("Books")) {

            imageRes = R.drawable.pythontext;

        } else if (category.equalsIgnoreCase("Clothing")) {

            imageRes = R.drawable.shirt;

        } else {

            // No matching category
            imageRes = R.drawable.note1;
        }

        values.put("image_res", imageRes);

        values.put("category", category);
        values.put("stock", stock);

        db.insert(
                "products",
                null,
                values
        );
    }


    // ---------------------------------------------------------
    // REMOVE PRODUCT
    // ---------------------------------------------------------

    public void removeProduct(int productId) {

        SQLiteDatabase db =
                getWritableDatabase();

        db.delete(
                "products",
                "id = ?",
                new String[]{
                        String.valueOf(productId)
                }
        );
    }


    // ---------------------------------------------------------
    // GET ALL PRODUCTS
    // ---------------------------------------------------------

    public ArrayList<Product> getAllProducts() {

        ArrayList<Product> list =
                new ArrayList<>();

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT * FROM products",
                        null
                );

        while (cursor.moveToNext()) {

            Product product =
                    new Product(
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow("id")
                            ),
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow("name")
                            ),
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow("description")
                            ),
                            cursor.getDouble(
                                    cursor.getColumnIndexOrThrow("price")
                            ),
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow("image_res")
                            ),
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow("category")
                            ),
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow("stock")
                            )
                    );

            list.add(product);
        }

        cursor.close();

        return list;
    }


    // ---------------------------------------------------------
    // GET PRODUCTS BY CATEGORY
    // ---------------------------------------------------------

    public ArrayList<Product> getProductsByCategory(
            String category) {

        ArrayList<Product> list =
                new ArrayList<>();

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT * FROM products WHERE category = ?",
                        new String[]{category}
                );

        while (cursor.moveToNext()) {

            Product product =
                    new Product(
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow("id")
                            ),
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow("name")
                            ),
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow("description")
                            ),
                            cursor.getDouble(
                                    cursor.getColumnIndexOrThrow("price")
                            ),
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow("image_res")
                            ),
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow("category")
                            ),
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow("stock")
                            )
                    );

            list.add(product);
        }

        cursor.close();

        return list;
    }


    // ---------------------------------------------------------
    // SEARCH PRODUCTS
    // ---------------------------------------------------------

    public ArrayList<Product> searchProducts(
            String searchText) {

        ArrayList<Product> list =
                new ArrayList<>();

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT * FROM products " +
                                "WHERE name LIKE ? " +
                                "OR category LIKE ?",
                        new String[]{
                                "%" + searchText + "%",
                                "%" + searchText + "%"
                        }
                );

        while (cursor.moveToNext()) {

            Product product =
                    new Product(
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow("id")
                            ),
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow("name")
                            ),
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow("description")
                            ),
                            cursor.getDouble(
                                    cursor.getColumnIndexOrThrow("price")
                            ),
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow("image_res")
                            ),
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow("category")
                            ),
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow("stock")
                            )
                    );

            list.add(product);
        }

        cursor.close();

        return list;
    }


    // ---------------------------------------------------------
    // GET SINGLE PRODUCT
    // ---------------------------------------------------------

    public Product getProduct(int productId) {

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT * FROM products WHERE id = ?",
                        new String[]{
                                String.valueOf(productId)
                        }
                );

        Product product = null;

        if (cursor.moveToFirst()) {

            product =
                    new Product(
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow("id")
                            ),
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow("name")
                            ),
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow("description")
                            ),
                            cursor.getDouble(
                                    cursor.getColumnIndexOrThrow("price")
                            ),
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow("image_res")
                            ),
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow("category")
                            ),
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow("stock")
                            )
                    );
        }

        cursor.close();

        return product;
    }


    // ---------------------------------------------------------
    // UPDATE STOCK
    // ---------------------------------------------------------

    public void updateStock(
            int productId,
            int newStock) {

        SQLiteDatabase db =
                getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put("stock", newStock);

        db.update(
                "products",
                values,
                "id = ?",
                new String[]{
                        String.valueOf(productId)
                }
        );
    }


    // ---------------------------------------------------------
    // ADD TO CART
    // ---------------------------------------------------------

    public void addToCart(
            int productId,
            int quantity) {

        SQLiteDatabase db =
                getWritableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT quantity FROM cart " +
                                "WHERE product_id = ?",
                        new String[]{
                                String.valueOf(productId)
                        }
                );

        if (cursor.moveToFirst()) {

            int oldQuantity =
                    cursor.getInt(0);

            ContentValues values =
                    new ContentValues();

            values.put(
                    "quantity",
                    oldQuantity + quantity
            );

            db.update(
                    "cart",
                    values,
                    "product_id = ?",
                    new String[]{
                            String.valueOf(productId)
                    }
            );

        } else {

            ContentValues values =
                    new ContentValues();

            values.put(
                    "product_id",
                    productId
            );

            values.put(
                    "quantity",
                    quantity
            );

            db.insert(
                    "cart",
                    null,
                    values
            );
        }

        cursor.close();
    }


    // ---------------------------------------------------------
    // CHECK USER LOGIN
    // ---------------------------------------------------------

    public boolean checkUser(
            String name,
            String password) {

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT * FROM users " +
                                "WHERE name = ? AND password = ?",
                        new String[]{
                                name,
                                password
                        }
                );

        boolean exists =
                cursor.moveToFirst();

        cursor.close();

        return exists;
    }


    // ---------------------------------------------------------
    // REGISTER USER
    // ---------------------------------------------------------

    public boolean registerUser(
            String name,
            String password) {

        SQLiteDatabase db =
                getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put("name", name);
        values.put("password", password);

        long result =
                db.insert(
                        "users",
                        null,
                        values
                );

        return result != -1;
    }


    // ---------------------------------------------------------
    // ADD SALE / ORDER
    // ---------------------------------------------------------

    public void addSale(
            int productId,
            int quantity,
            String customerName) {

        SQLiteDatabase db =
                getWritableDatabase();

        Product product =
                getProduct(productId);

        if (product == null) {
            return;
        }


        // Create order
        ContentValues orderValues =
                new ContentValues();

        orderValues.put(
                "order_date",
                new SimpleDateFormat(
                        "dd-MM-yyyy HH:mm",
                        Locale.getDefault()
                ).format(new Date())
        );

        orderValues.put(
                "total_amount",
                product.getPrice() * quantity
        );

        orderValues.put(
                "customer_name",
                customerName
        );


        long orderId =
                db.insert(
                        "orders",
                        null,
                        orderValues
                );

        if (orderId == -1) {
            return;
        }


        // Create order item
        ContentValues itemValues =
                new ContentValues();

        itemValues.put(
                "order_id",
                orderId
        );

        itemValues.put(
                "product_id",
                productId
        );

        itemValues.put(
                "quantity",
                quantity
        );

        itemValues.put(
                "price",
                product.getPrice()
        );


        db.insert(
                "order_items",
                null,
                itemValues
        );
    }


    // ---------------------------------------------------------
    // GET ALL ORDERS
    // ---------------------------------------------------------

    public ArrayList<Order> getAllOrders() {

        ArrayList<Order> list =
                new ArrayList<>();

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT * FROM orders " +
                                "ORDER BY id DESC",
                        null
                );

        while (cursor.moveToNext()) {

            int id =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow("id")
                    );

            String date =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("order_date")
                    );

            double total =
                    cursor.getDouble(
                            cursor.getColumnIndexOrThrow("total_amount")
                    );

            String customerName =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("customer_name")
                    );

            Order order =
                    new Order(
                            id,
                            date,
                            total,
                            customerName
                    );

            list.add(order);
        }

        cursor.close();

        return list;
    }


    // ---------------------------------------------------------
    // GET ORDER ITEMS
    // ---------------------------------------------------------

    public ArrayList<OrderItem> getOrderItems(
            int orderId) {

        ArrayList<OrderItem> list =
                new ArrayList<>();

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor =
                db.rawQuery(
                        "SELECT order_items.*, " +
                                "products.name " +
                                "FROM order_items " +
                                "INNER JOIN products " +
                                "ON order_items.product_id = products.id " +
                                "WHERE order_items.order_id = ?",
                        new String[]{
                                String.valueOf(orderId)
                        }
                );

        while (cursor.moveToNext()) {

            int id =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow("id")
                    );

            int productId =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow("product_id")
                    );

            String productName =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow("name")
                    );

            int quantity =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow("quantity")
                    );

            double price =
                    cursor.getDouble(
                            cursor.getColumnIndexOrThrow("price")
                    );


            OrderItem item =
                    new OrderItem(
                            id,
                            productId,
                            productName,
                            quantity,
                            price
                    );

            list.add(item);
        }

        cursor.close();

        return list;
    }
}