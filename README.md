# androidCRUDproject
Android project to complete the Android course at OE-AMK. 

## Purpose of the project:
The purpose is to create an application which represents a basic inventory system to help the chosen company's processes.

## User stories:
* Create an application for a small company - deals with selling boardgames - to help to keep its inventory up-to-date.
* It has to be a function to create a new product (board game) with its attributes. 
* According to the price and quantity it should be able to calculate the total revenue by the concrete game. It has to be calculated for all the games at a total amount.
* It has to be a function to update the chosen boardgame's traits.
* It has to be a function to delete the chosen boardgame.

## The classes and activities:
I created 3 activities for this app and classes for handling the database, to implement the boardgame's model.

### Activities:
* activity_main.xml: Contains the create button (to open the activity_create_product.xml activity), a listview where the boardgames are displayed and the "Expected Revenue" amount, calculated by all games' revenue.

* activity_create_product.xml: Here the user is able to write the new boardgame's attributes. Here there is a possibility to "Save" our work and create a new object or to "Cancel" our work to go back to Main Activity without creating any object.

* activity_edit_delete_product: Here the user is able to show them boardgames in details, delete and update the chosen game.

### Classes: 
* For the database I've created the DatabaseHelper class, which contains the structure of the database (db name, table name, column names), the recommended functions (goes from SQLiteOpenHelper), and the CRUD processes those I created for the project (create, delete, update, read all item, read one item).

* For the object I've created a model class - BoardGame.class. It is inherite from Serializable class (this is because I want to send the object from anactivity to another, and I've find this solution for the problem). The modell class contains the attributes of the boardgame, getters, setters and constructors.

#### Activity related classes:
* MainActivity: Here is the logic of the main_activity.xml button (Create which leads you to the create activity), the listview item (the getAllGame() and the getOneGame() are find here as well, because of the list view has the ability to show all items, or to click to one item and show the chosen item's detail in another activity). Also I code here the calculation of all games expected revenue.

* CreateProduct: Here is find the logic of create a new product after clicking the Save button. For this I used try/catch because, the new game should be created only if all attribute has a value. To create a new game I give the textboxes' text to the Boardgame class construstor. 

* EditDeleteProduct: Here the delete and update functions appear, and the detailed view of the chosen game (for this I gave the clicked listview item/chosen boardgame to this activity). Delete is executed if the user click to "Delete" button (the chosen game will be deleted, for this I used the primary key id). If the user modify a textbox and click update, then the database will be updated with the new data (if the textbox empty, the update function does not excecute).

### Used links for creation:
Mainly I used these source to figure out how to create the database class, model class and the CRUD functions:
* https://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
* https://www.youtube.com/watch?v=hDSVInZ2JCs

For sending a whole object from one activity to another I used as a help this video:
* https://www.youtube.com/watch?v=aO2qYYNwWpA
