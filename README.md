# AAHAR-CUSTOMER
Application for customer
AAHAR is the application made for mess owners(Customer) to resolve all the problems faced by mess owner due to manual system by digitizing the mess management. We have kept our UI so simple that even illiterate people can use our app. This application provides features like attendance, payment, review & rating, menu polling, expense record, suggestion & complaints and helps to provide a better way for management.
The functionalities provided to customer are as follows:
1.Sign up/ Registration: The customer can create an account by filling registration form which contains a Username, Email, Phone no, and Password. This information of user is collected and verified by OTP to authenticate the user so that no unauthorised person can enter into the app.
2.Login: In Login Activity, we have implemented login with email and password as well as providing a Google Sign in option.
MainActivity: After authentication, the user can enter into MainActivity. So we have implemented a navigation drawer and fragments for easy rendering in our app. The main activity mainly consists of 6 fragments; they are: home fragment, attendance fragment, student fragment, notification fragment, add mess fragment and add menu fragment.

1.Home fragment: The Home fragment will have information about customers’ mess like the total no of its users, its ratings, and reviews. Intially, home fragment will contains 0 reviews and 0 rating by default. After creating mess it will display mess name, no. of review and rating based on the rating given by the end-user to the mess.
![image](https://user-images.githubusercontent.com/102285284/175095652-b0cbfd16-11f7-4e09-99de-937e59cca8d4.png)

2.Attendance fragment: On the attendance fragment, customers can create the attendance session for user to mark their attendance. Session is initiated from application attendance cardview with the date, day and time by clicking the START SESSION button and can terminate the session with the END SESSION button. End-users have to mark their attendance during the sessions only. After session termination, users can’t mark their attendance. The count of the present users will get to the customer after the session is closed.

![image](https://user-images.githubusercontent.com/102285284/175096592-433f10a0-d14b-4c10-9798-2e86f7fcd2d7.png)

3.Students fragment: Student fragment will show the list of all the users who joined the mess in recyclerview with their details. Details will have the profile picture of the user, the name, Phone no, Email, attendance, and payment. After clicking attendance and payment, customer can view the attendance and payment datails of the user respectively
![image](https://user-images.githubusercontent.com/102285284/175096676-c2b4312f-d339-4c06-8762-2b2bf5f978a9.png)

4.Notification fragment: All the notifications about the user joined a mess, adds reviews, rates, and pays mess bill displayed.

5.Add mess fragment:The customer can add its mess to the app which will be visible to all the end-users in explore section. To create the mess, the Customer will fill out the Mess-Info form available in Add Mess section and add by button Create. The form will have the customer name, its mess name, the location, email-id, UPI-id for payment, monthly price, and the mess’s specialty. By filling in all the information, customers will create a mess. Customers can edit information about mess at any time by going to add mess section.
![image](https://user-images.githubusercontent.com/102285284/175095759-7911c878-98e0-4a0a-badf-1ed5e7d4e24f.png)

6.Add menu fragment: customers can add today’s menu which will display the end-user.
![image](https://user-images.githubusercontent.com/102285284/175095719-c67ae33a-3988-49b9-b501-fca3f23ea23a.png)
