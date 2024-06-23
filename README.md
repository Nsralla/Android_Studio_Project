Application Name: Advance Pizza Total Points (120) (45% from the course mark) 
Project Description: 
A pizza restaurant is asking us to build an Android application which allows the restaurant’s users 
to order their pizza online or using a local database, the application should be user friendly and 
simple. The application should include the following functionalities: 
1. Introduction layout (10 points) 
This layout has a “Get Started” button which will connect to a Server using REST to load the pizza 
types in array-List. Use the following URL to get the pizza types: 
https://18fbea62d74a40eab49f72e12163fe6c.api.mockbin.io/ 
1.1. If connection successful - go-to login and registration section. 
1.2. If connection unsuccessful - Show error message and stay on the same layout. 
2. Login and Registration Layout (30 point) 
This layout should have a “Login” button and a “Sign Up” button. Each will redirect to a special 
layout. 
2.1. The Login button will redirect us to a login page where we can enter our email and password that 
are registered in the database to login into a special menu. The login layout must have a check box 
called “remember me” which will save the email in shared preferences so next time we login we 
don’t need to type the email. 
2.2. The Sign-Up button will redirect us to another layout where we will enter our email (must be an 
email) as a primary key, phone number (must be exactly 10 phone digits starting by “05”), first 
name (not less than 3 characters), last name (not less than 3 characters), gender (spinner), 
password (must not be less than 8 characters and must include at least 1 character and 1 
number) and confirm password (the password should be encrypted using a secure Hash 
Function). If all the previous conditions are entered correctly then and only then we can register 
the user and move the user to the login page. Otherwise, the user should be kept in the same 
layout and be able to see all the validation’s errors in a user friendly way. 
3. Home layout (sign in as normal Customer) (40 point) 
This layout should be a Navigation Drawer Activity. The navigation bar for the Navigation Drawer 
Activity should have the following functionalities: 
3.1. Home: which will contain the history of the restaurant. Also, this page will be the main page for the 
Navigation Drawer. 
3.2. Pizza menu: This menu should include all pizza types, each one should be defined by its name so as if 
the customer clicked on a name then the details of the pizza appears (this must be done using 
fragments). Also, a filter to search on a special type should be implemented to search for a pizza 
based on (Price, Size or Category [chicken, beef, veggies, others]). Although, beside each type there 
must be an “add to favorites” button which will add the pizza type to user’s favorites and an ‘’order’’ 
button which will pop up an order menu that has details of (size, price and quantity) and a ‘’submit’’ 
button to confirm the order. 
3.3. Your orders: This will only include all the orders that have been ordered before. Each with the date 
and time of the order. Clicking on any order should display all the order details. 
3.4. Your favorites: This will include all the favorites that were added by the customer. It will have the 
functionality for ordering as what we have in the Pizza menu.  Also, it will include the undo-favorite 
functionality.  
3.5. Special Offers: This menu will contain the special offers made by the restaurant. These special offers 
must have the functionality for ordering as what we have in the pizza menu. 
3.6. Profile: In this layout each customer should view all his personal information and be able to change 
his first name, last name, password and phone number. The changes should adhere all the conditions 
we use in the sign-up page. 
3.7. Call us or Find us: This layout should have three buttons. The first will call the restaurant on this 
phone number ”0599000000”. The second will open Google maps to find the restaurant using the 
following coordinates (31.961013, 35.190483). Finally, the third one will open Gmail to send an email to 
the restaurant main email AdvancePizza@Pizza.com. 
3.8. Logout: which will log out the customer from this profile and redirect him to the login page.
4. Home layout (sign in as Admin) (25 points) 
This layout should be a Navigation Drawer Activity. The navigation bar for the Navigation Drawer 
Activity should have the following functionalities: 
4.1. Admin Profile: In this layout each admin should view all his personal information and be able to 
change his first name, last name, password and phone number. This should adhere all the 
conditions we use in the sign-up page. Also, this page will be the main page for the Navigation 
Drawer. 
4.2. Add Admin: The admin can add a new admin to the restaurant app with the same fields and 
validations as what we have in the sign up page. 
4.3. View All Orders: view all orders including all the order details and the name of the customer of each 
order that is made. 
4.4. Add Special Offers: The admin should have the ability to add new special offers through this menu 
option. The admin should have the ability to specify different parameters for the special offers: 
pizza types and their sizes, the offer period and total price. 
4.5. Logout: This menu option will log out the Admin from this profile and redirect him to the login page. 
Note: the app will contain a static admin (added into the DB manually). 
5. Extra (15 points: This part is a required part) 
5.1. Adding a profile picture for the customer and allowing the customer to change his profile picture. 
5.2. Adding the ability for the admin to calculate the number of orders for each pizza type and 
calculate the total income for each type and the total income for all types together. 
This Project must be designed using Android packages and should use:  
1. Android Layouts (dynamically and statically). 
2. Intents & Notifications (toast messages).  
3. SQLite Database. 
4. Animation (Frame animation or tween Animation).  
5. Fragments. 
6. Shared Preferences. 
7. REST.
8. <img width="250" alt="image" src="https://github.com/Nsralla/Android_Studio_Project/assets/122102030/f9e5db46-d2c1-44ee-9600-1f09fee1cd52">
<img width="242" alt="image" src="https://github.com/Nsralla/Android_Studio_Project/assets/122102030/f4529ef4-34e2-428a-ae02-44634e492b82">
<img width="227" alt="image" src="https://github.com/Nsralla/Android_Studio_Project/assets/122102030/9a337c28-85ce-4c86-87d9-1a49ab98a705">
<img width="234" alt="image" src="https://github.com/Nsralla/Android_Studio_Project/assets/122102030/f9f56053-d3c8-40fb-bdfe-c48595f2911b">
