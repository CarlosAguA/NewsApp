# NewsApp
 
This is a News Feed App. 

The app comprises the 8th project of the course Android Basics Nanodegree by Google/Udacity. 

The main screen shows a list of articles from "The Guardian" website doing a filtering by category and relevance, oldest or most recent. When the item in the list is clicked, the main page of the article is opened in the web browser. 


 Main Screen News Feed     |      Order By Setting     |     Category Setting      |      Article Webpage      |
:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------:|
![](https://cloud.githubusercontent.com/assets/23319417/23674684/58171698-033c-11e7-8277-052b851e45b4.png)  |  ![](https://cloud.githubusercontent.com/assets/23319417/23674683/5806769e-033c-11e7-8e42-6d694bd5d015.png) |  ![](https://cloud.githubusercontent.com/assets/23319417/23674682/5800974c-033c-11e7-8a3a-b6e7c30852a6.png) | ![](https://cloud.githubusercontent.com/assets/23319417/23674685/58177aac-033c-11e7-97fe-2e27510f1205.png) |

In this project different topics are covered like : 

1. Consuming an API from the Guardian website. 
2. Network and server connection.
3. HTTP Request and JSON Parsing.
4. Async Loader and callback methods.
5. Preference Fragments.
6. OnItemClickListener and OnSharedPreferenceChangedListener
7. Adapter and ListView. 

An Article java class was created in order to parse title, category, date and url in order to implement the functionality of the code. This states are stored in each instance of an article object which are added to an ArrayList<Article>. 

The Loader implemented was useful in order to respond to screen rotation or other phases of the Activity Lifecycle that could restart the method onCreate(), so that when the loader already exists, the infomation will keep on the screen. 

