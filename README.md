# Codefellowship

main java file:

AppUser: model
Post: model
PostControlloer:"/posts","/posts/{id}","/posts/add"
AppUserController: "/login" "/signup" "/users/{id}" "/myprofile","/explore","/feed"
AppUserRepository
PostRepository
cfShipController: "/" home page
UserDetailsServiceImpl
WebSecurityConfig


"/" land you to the home page
then click sign up link you to sign up page, fill out form submit and redirect to the myprofile page, 
click logout can go back to login page, display user information and lists of the posts belong to that user
and also link to the home page.
Click "create a post"  will link to createPost html file which allows users to create their owns posts.



New Features(Updated)

Now every logged in user can follow other users in the neighborhood, go to Explore link you will see all your avaiable users that you have not followed them yet
on the page you will be view each user's profile page, and on that page you will be able to follow that user, after you clicked the follow me button the button will turn to unfollow, and you will see that person's all posts in following users page.
and you still can unfollow that user in the future if you change your mind, just go to "Following users" and find the person and click the person's profile page and click "unfollow" button








test:
test the routes;

